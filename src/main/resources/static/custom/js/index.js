/**
 * Created by 我的样子平平无奇 on 2018/6/9.
 */

$(function () {
    /*
     获取日期
     */
    var myDate = new Date()
    var month = myDate.getMonth() > 9 ? (myDate.getMonth() + 1) : ('0' + (myDate.getMonth() + 1));
    var date = myDate.getDate() > 10 ? myDate.getDate() : ('0' + myDate.getDate());
    $(".datediv h1").text(month);
    $(".datediv h4").text("/" + date);
    $(".box-content .post").html("© "+myDate.getFullYear()+" Copyright BY LinHuaSheng" +
        "<br>粤ICP备18046160号" );
    /*
     封面标题淡入淡出
     */
    $(".label_title").click(function () {
        $(this).addClass("blurfilter")
        .removeClass("animated fadeInUp")
        .addClass("animated fadeOut")
        if($(this).css("color")=="rgb(68, 68, 68)") {
            setTimeout(function () {
                $(".label_title").removeClass("blurfilter")
                    .css("color", "#52afa4")
                    .html("Discove<br>more perfect<br>sound<h6><a href='/musicIndex'>发现新声音<span class='glyphicon glyphicon-menu-right'></span></a></h6>")
                    .removeClass("animated fadeOut")
                    .addClass("animated fadeIn");
            }, 500)
        }else {
            setTimeout(function () {
                $(".label_title").removeClass("blurfilter")
                    .css("color", "#444444")
                    .html("Discove<br>your better<br>sound")
                    .removeClass("animated fadeOut")
                    .addClass("animated fadeIn");
            }, 500)
        }
    })

    /*
    导航按钮变换
     */
    $(window).scroll(function(e){
        var wScrollTop = $(window).scrollTop();
        // console.log(wScrollTop);
        if(wScrollTop > 125){
            $(".mid_bar .fade").addClass("animated fadeInUp");
            $(".iconBox").addClass("animated fadeInUp");
        }
        if (wScrollTop > 800){
            $(".page2>.fade").addClass("animated fadeInUp");
        }
        if (wScrollTop > 1150){
            $(".messagediv>.fade").eq(0).addClass("animated fadeInUp");
        }
        if (wScrollTop > 1350){
            $(".messagediv>.fade").eq(1).addClass("animated fadeInUp");
        }
        if (wScrollTop > 1600){
            $(".messagediv>.fade").eq(2).addClass("animated fadeInUp");
        }
        if(wScrollTop > 1380){
            $(".scrollbtn").html("top<i class='fa fa-angle-double-up'></i>")
        }else {
            $(".scrollbtn").html("next<i class='fa fa-angle-double-down'></i>")
        }
    })

    /*
     滚动按钮
     */
    $(".scrollbtn").click(function () {
        if ($(this).text() == "next") {
            $("html,body").animate({scrollTop: "1300px"}, 500);
        } else if ($(this).text() == "top") {
            $("html,body").animate({scrollTop: "0px"}, 500);
        }
    })
    var plateNo = {
        "古典": "1",
        "爵士": "2",
        "流行": "3",
        "民谣": "4",
        "电子": "5",
        "嘻哈": "6",
        "轻音乐": "7",
        "布鲁斯": "8",
        "摇滚": "9"
    }
    new Vue({
        el:"#messagediv",
        methods:{
            toblog:function (event) {
                window.location.href = "/blog/"+plateNo[event.target.innerText];
            }
        }

    })
})