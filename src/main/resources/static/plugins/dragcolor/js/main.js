/**
 * main.js
 * http://www.codrops.com
 *
 * Licensed under the MIT license.
 * http://www.opensource.org/licenses/mit-license.php
 * 
 * Copyright 2015, Codrops
 * http://www.codrops.com
 */
(function() {

	var docElem = window.document.documentElement,
		// transition end event name
		transEndEventNames = { 'WebkitTransition': 'webkitTransitionEnd', 'MozTransition': 'transitionend', 'OTransition': 'oTransitionEnd', 'msTransition': 'MSTransitionEnd', 'transition': 'transitionend' },
		transEndEventName = transEndEventNames[ Modernizr.prefixed( 'transition' ) ];

	function scrollX() { return window.pageXOffset || docElem.scrollLeft; }
	function scrollY() { return window.pageYOffset || docElem.scrollTop; }
	function getOffset(el) {
		var offset = el.getBoundingClientRect();
		return { top : offset.top + scrollY(), left : offset.left + scrollX() };
	}

	//拖动事件的处理函数
	function dragMoveListener(event) {
		var target = event.target,
			// 将拖动位置保存在data-x / data-y属性中
			x = (parseFloat(target.getAttribute('data-x')) || 0) + event.dx,
			y = (parseFloat(target.getAttribute('data-y')) || 0) + event.dy;

		// translate the element
		target.style.transform = target.style.webkitTransform = 'translate(' + x + 'px, ' + y + 'px)';
		target.style.zIndex = 10000;

		// update the posiion attributes
		target.setAttribute('data-x', x);
		target.setAttribute('data-y', y);
	}
	//还原元素
	function revertDraggable(el) {
		el.style.transform = el.style.webkitTransform = 'none';
		el.style.zIndex = 1;
		el.setAttribute('data-x', 0);
		el.setAttribute('data-y', 0);
	}

	function init() {

		// 具有“drag-element”类的目标元素
		interact('.drag-element').draggable({
			// 开启拖拽投掷
			inertia: true,
			// 在每个dragmove事件上调用此函数
			onmove: dragMoveListener,
			onend: function (event) {
				if(!classie.has(event.target, 'drag-element--dropped') && !classie.has(event.target, 'drag-element--dropped-text')) {
					//还原元素
					revertDraggable(event.target);
				}
			}
		});

		// enable draggables to be dropped into this
		interact('.paint-area').dropzone({
			// 只接受与此CSS选择器匹配的元素
			accept: '.drag-element',
			// 需要75％的元素重叠才能接受被抛元素
			overlap: 0.75,
			//水滴进入选区
			ondragenter: function (event) {
				classie.add(event.target, 'paint-area--highlight');
			},
			//水滴离开选区
			ondragleave: function (event) {
				classie.remove(event.target, 'paint-area--highlight');
			},
			//水滴落下
			ondrop: function (event) {
				var type = 'area';
				if(classie.has(event.target, 'paint-area--text')) {
					type = 'text';
				}
				//被滴落的水滴
				var draggableElement = event.relatedTarget;

				classie.add(draggableElement, type === 'text' ? 'drag-element--dropped-text' : 'drag-element--dropped');
				//滴落完成的回调函数
				var onEndTransCallbackFn = function(ev) {
					this.removeEventListener( transEndEventName, onEndTransCallbackFn );
					if( type === 'area' ) {
						//染色
						paintArea(event.dragEvent, event.target, draggableElement.getAttribute('data-color'));
					}
					setTimeout(function() {
						//还原元素
						revertDraggable(draggableElement);
						classie.remove(draggableElement, type === 'text' ? 'drag-element--dropped-text' : 'drag-element--dropped');
					}, type === 'text' ? 0 : 250);
				};
				if( type === 'text' ) {
					//染色
					paintArea(event.dragEvent, event.target, draggableElement.getAttribute('data-color'));
				}
				draggableElement.querySelector('.drop').addEventListener(transEndEventName, onEndTransCallbackFn);
			},
			ondropdeactivate: function (event) {
				// 移除水滴进入选区时的样式
				classie.remove(event.target, 'paint-area--highlight');
			}
		});

		// 重置颜色
		document.querySelector('button.reset-button').addEventListener('click', resetColors);
	}

	//染色
	function paintArea(ev, el, color) {
		var type = 'area';
		if(classie.has(el, 'paint-area--text')) {
			type = 'text';
		}

		if( type === 'area' ) {
			// create SVG element
			var dummy = document.createElementNS('http://www.w3.org/2000/svg', 'svg');
			dummy.setAttributeNS(null, 'version', '1.1');
			dummy.setAttributeNS(null, 'width', '100%');
			dummy.setAttributeNS(null, 'height', '100%');
			dummy.setAttributeNS(null, 'class', 'paint');

			var g = document.createElementNS('http://www.w3.org/2000/svg', 'g');
			g.setAttributeNS(null, 'transform', 'translate(' + Number(ev.pageX - getOffset(el).left) + ', ' + Number(ev.pageY - getOffset(el).top) + ')');

			var circle = document.createElementNS("http://www.w3.org/2000/svg", "circle");
			circle.setAttributeNS(null, 'cx', 0);
			circle.setAttributeNS(null, 'cy', 0);
			circle.setAttributeNS(null, 'r', Math.sqrt(Math.pow(el.offsetWidth,2) + Math.pow(el.offsetHeight,2)));
			circle.setAttributeNS(null, 'fill', color);

			dummy.appendChild(g);
			g.appendChild(circle);
			el.appendChild(dummy);
		}

		setTimeout(function() {
			classie.add(el, 'paint--active');

			if( type === 'text' ) {
				el.style.color = color;
				var onEndTransCallbackFn = function(ev) {
					if( ev.target != this ) return;
					this.removeEventListener( transEndEventName, onEndTransCallbackFn );
					classie.remove(el, 'paint--active');
				};

				el.addEventListener(transEndEventName, onEndTransCallbackFn);
			}
			else {
				var onEndTransCallbackFn = function(ev) {
					if( ev.target != this || ev.propertyName === 'fill-opacity' ) return;
					this.removeEventListener(transEndEventName, onEndTransCallbackFn);
					// set the color
					el.style.backgroundColor = color;
					// remove SVG element
					el.removeChild(dummy);

					setTimeout(function() { classie.remove(el, 'paint--active'); }, 25);
				};

				circle.addEventListener(transEndEventName, onEndTransCallbackFn);
			}
		}, 25);
	}
	//重置颜色
	function resetColors() {
		[].slice.call(document.querySelectorAll('.paint-area')).forEach(function(el) {
			el.style[classie.has(el, 'paint-area--text') ? 'color' : 'background-color'] = '';
		});
	}

	init();

})();