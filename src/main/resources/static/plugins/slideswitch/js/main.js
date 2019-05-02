(function() {
    header = document.getElementById( 'header' )
    switchBtnn = header.querySelector( 'button.slider-switch' ),
        //切换按钮图片
        toggleBtnn = function() {
            if( slideshow.isFullscreen ) {
                classie.add( switchBtnn, 'view-maxi' );
            }
            else {
                classie.remove( switchBtnn, 'view-maxi' );
            }
        },
        toggleCtrls = function() {
            if( !slideshow.isContent ) {
                classie.add( header, 'hide' );
            }
        },
        toggleCompleteCtrls = function() {
            if( !slideshow.isContent ) {
                classie.remove( header, 'hide' );
            }
        },
        slideshow = new DragSlideshow( document.getElementById( 'slideshow' ), {
            // 在全屏和最小化幻灯片之间切换
            onToggle : toggleBtnn,
            // 切换主图像和内容视图
            onToggleContent : toggleCtrls,
            // 切换主图像和内容视图（动画结束后触发）
            onToggleContentComplete : toggleCompleteCtrls
        }),
        toggleSlideshow = function() {
            slideshow.toggle();
            toggleBtnn();
        };

    // 在全屏和小幻灯片之间切换
    switchBtnn.addEventListener( 'click', toggleSlideshow );

}());