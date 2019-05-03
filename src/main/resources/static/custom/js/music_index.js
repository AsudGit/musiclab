$(function () {
    var $switch=$(".content-switch>i");
    $(".content-switch").click(function () {
        if ($switch.hasClass("fa fa-angle-double-down")){
            $switch.removeClass("fa fa-angle-double-down")
                .addClass("fa fa-angle-double-up");
            $(this).addClass("myhiden");
            setTimeout(function () {
                $("#myrecord").removeClass("animated fadeOutLeft")
                    .addClass("animated fadeInLeft");
                $(".content_bottom,.music_nav,.music_title").removeClass("animated fadeOutUp")
                    .addClass("animated fadeInUp");
            }, 400);
        }else {
            $switch.removeClass("fa fa-angle-double-up")
                .addClass("fa fa-angle-double-down");
            $(this).removeClass("myhiden");
            $("#myrecord").removeClass("animated fadeInLeft")
                .addClass("animated fadeOutLeft");
            $(".content_bottom,.music_nav,.music_title").removeClass("animated fadeInUp")
                .addClass("animated fadeOutUp");
        }
    })
    $(document).keydown(function(event){
        if(event.keyCode==40){
            $switch.removeClass("fa fa-angle-double-down")
                .addClass("fa fa-angle-double-up");
            $(".content-switch").addClass("myhiden");
            setTimeout(function () {
                $("#myrecord").removeClass("animated fadeOutLeft")
                    .addClass("animated fadeInLeft");
                $(".content_bottom,.music_nav,.music_title").removeClass("animated fadeOutUp")
                    .addClass("animated fadeInUp");
            }, 400);
        }else if (event.keyCode == 38) {
            $switch.removeClass("fa fa-angle-double-up")
                .addClass("fa fa-angle-double-down");
            $(".content-switch").removeClass("myhiden");
            $("#myrecord").removeClass("animated fadeInLeft")
                .addClass("animated fadeOutLeft");
            $(".content_bottom,.music_nav,.music_title").removeClass("animated fadeInUp")
                .addClass("animated fadeOutUp");
        }
    });

    $(".toggle_song").click(function () {
        $(".record_lines>p").text("这不是歌名");
        $("#myrecord_min>.record_center_cover").addClass("rotating");
    })
})
