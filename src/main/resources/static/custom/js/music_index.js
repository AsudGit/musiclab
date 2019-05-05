$(function () {
    //填色
    function coloring(colorclass,bcgcolor,darkcolor) {
        $(".bcgcolor").addClass(colorclass);
        $(".adorn_line").css("border-color",bcgcolor);
        $(".song_item>a").css("color", bcgcolor);
        $(".record_center_dotedge").css("background-color",darkcolor)
        $(".song_item").hover(
            function () {
                $(this).css({
                    "background-color": bcgcolor, "color": "white"
                })
                $(this).children("a").css("color", "white");
            },
            function () {
                $(this).css({
                    "background-color": "white", "color": "black"
                })
                $(this).children("a").css("color", bcgcolor);
            }
        )
        ap.theme(bcgcolor);
    }
    //页面上滑
    function pageslideup() {
        //改变图标指向
        $switch.removeClass("fa fa-angle-double-down")
            .addClass("fa fa-angle-double-up");
        //隐藏换页图标
        $(".content-switch").addClass("myhiden");
        //浮动显示
        setTimeout(function () {
            $("#myrecord").removeClass("animated fadeOutLeft")
                .addClass("animated fadeInLeft");
            $(".content_bottom,.music_nav,.music_title").removeClass("animated fadeOutUp")
                .addClass("animated fadeInUp");
        }, 400);
    }
    //页面下滑
    function pageslidedown() {
        $switch.removeClass("fa fa-angle-double-up")
            .addClass("fa fa-angle-double-down");
        $(".content-switch").removeClass("myhiden");
        $("#myrecord").removeClass("animated fadeInLeft")
            .addClass("animated fadeOutLeft");
        $(".content_bottom,.music_nav,.music_title").removeClass("animated fadeInUp")
            .addClass("animated fadeOutUp");
    }
    /*换页动画*/
    var $switch=$(".content-switch>i");
    var colorclass,bcgcolor,darkcolor;
    $(".content-switch").click(function () {
        if ($switch.hasClass("fa fa-angle-double-down")){
            //填色
            if($(".current>h2").text()=="网易云音乐听见 · 好时光！") {
                colorclass = "redbcg";
                bcgcolor = "#da0000";
                darkcolor = "#ab0000";
            }else if($(".current>h2").text()=="QQ音乐音乐你的生活"){
                colorclass = "greenbcg";
                bcgcolor = "#31c27c";
                darkcolor = "#22824c";
            } else if($(".current>h2").text()=="酷狗音乐音乐总有新玩法"){
                colorclass = "bluebcg";
                bcgcolor = "#1e84e4";
                darkcolor = "#1f5fb5";
            } else if($(".current>h2").text()=="酷我音乐好音质用酷我"){
                colorclass = "yellowbcg";
                bcgcolor = "#f0bc0f";
                darkcolor = "#bf8e0f";
            } else if($(".current>h2").text()=="MusicLab我们永远缺一个你的idea"){
                colorclass = "mlabbcg";
                bcgcolor = "#8a8ab9";
                darkcolor = "#636389";
            }
            coloring(colorclass, bcgcolor, darkcolor);
            pageslideup();
        }else {
            pageslidedown();
            setTimeout(function () {
                $(".bcgcolor").removeClass("redbcg greenbcg bluebcg yellowbcg mlabbcg");
            }, 700);
        }
    })
    $(document).keydown(function(event){
        if(event.keyCode==40){
            if($(".current>h2").text()=="网易云音乐听见 · 好时光！") {
                colorclass = "redbcg";
                bcgcolor = "#da0000";
                darkcolor = "#ab0000";
            }else if($(".current>h2").text()=="QQ音乐音乐你的生活"){
                colorclass = "greenbcg";
                bcgcolor = "#31c27c";
                darkcolor = "#22824c";
            } else if($(".current>h2").text()=="酷狗音乐音乐总有新玩法"){
                colorclass = "bluebcg";
                bcgcolor = "#1e84e4";
                darkcolor = "#1f5fb5";
            } else if($(".current>h2").text()=="酷我音乐好音质用酷我"){
                colorclass = "yellowbcg";
                bcgcolor = "#f0bc0f";
                darkcolor = "#bf8e0f";
            } else if($(".current>h2").text()=="MusicLab我们永远缺一个你的idea"){
                colorclass = "mlabbcg";
                bcgcolor = "#8a8ab9";
                darkcolor = "#636389";
            }
            coloring(colorclass, bcgcolor, darkcolor);
            pageslideup();
        }else if (event.keyCode == 38) {
            pageslidedown();
            setTimeout(function () {
                $(".bcgcolor").removeClass("redbcg greenbcg bluebcg yellowbcg mlabbcg");
            }, 700);
        }
    });
    /*换页动画end*/

    //切歌
    $(".toggle_song").click(function () {
        var $myrecord_min=$("#myrecord_min");
        $myrecord_min.removeClass("animated rotateInDownRight")
            .addClass("animated rotateOutDownRight");
        setTimeout(function () {
            $myrecord_min.removeClass("animated rotateOutDownRight")
                .addClass("animated rotateInDownRight");
            $(".record_lines>p").text("不是歌名");
        }, 150);
        setTimeout(function () {
            $("#myrecord_min>.record_center_cover").addClass("rotating");
        }, 1300);
    })

    //init APlayer
    var ap = new APlayer({
        container: document.getElementById('music_player'),
        theme: '#da0000',
        loop: 'all',
        order: 'random',
        preload: 'auto',
        mutex: true,
        listFolded:true,
        listMaxHeight:10,
        audio: [{
            name: 'name',
            artist: 'artist',
            url: 'https://api.hibai.cn/music/Music/Music?id=1349292048&type=url',
            cover: '/image/ballad.jpg'
        },{
            name: 'name2',
            artist: 'artist',
            url: 'https://api.hibai.cn/music/Music/Music?id=1349292048&type=url',
            cover: '/image/ballad.jpg'
        },{
            name: 'name3',
            artist: 'artist',
            url: 'https://api.hibai.cn/music/Music/Music?id=1349292048&type=url',
            cover: '/image/ballad.jpg'
        },{
            name: 'name3',
            artist: 'artist',
            url: 'https://api.hibai.cn/music/Music/Music?id=1349292048&type=url',
            cover: '/image/ballad.jpg'
        },{
            name: 'name3',
            artist: 'artist',
            url: 'https://api.hibai.cn/music/Music/Music?id=1349292048&type=url',
            cover: '/image/ballad.jpg'
        },{
            name: 'name3',
            artist: 'artist',
            url: 'https://api.hibai.cn/music/Music/Music?id=1349292048&type=url',
            cover: '/image/ballad.jpg'
        },{
            name: 'name3',
            artist: 'artist',
            url: 'https://api.hibai.cn/music/Music/Music?id=1349292048&type=url',
            cover: '/image/ballad.jpg'
        },{
            name: 'name3',
            artist: 'artist',
            url: 'https://api.hibai.cn/music/Music/Music?id=1349292048&type=url',
            cover: '/image/ballad.jpg'
        }]
    });

})
