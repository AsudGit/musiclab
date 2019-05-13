$(function () {
    /*富文本编辑器初始化*/
    var E = window.wangEditor;
    var editor = new E("#toolbar","#editor_area");
    editor.customConfig.zIndex =0;
    //初始化菜单栏
    editor.customConfig.menus = [
        'head',  // 标题
        'bold',  // 粗体
        'fontSize',  // 字号
        'fontName',  // 字体
        'italic',  // 斜体
        'underline',  // 下划线
        'strikeThrough',  // 删除线
        'foreColor',  // 文字颜色
        'backColor',  // 背景颜色
        'link',  // 插入链接
        'list',  // 列表
        'justify',  // 对齐方式
        'quote',  // 引用
        'image',  // 插入图片
        'table',  // 表格
        'video',  // 插入视频
        'code',  // 插入代码
    ];
    // 自定义字体
    editor.customConfig.fontNames = [
        '宋体',
        '黑体',
        '微软雅黑',
        'Arial',
        'Tahoma',
        'Verdana'
    ]
    editor.create();
    $(".contentedit #editor_submit").click(function () {
        var html = editor.txt.html()
        var filterHtml = filterXSS(html)  // 此处进行 xss 攻击过滤
        if($("#editor_title").val()==''){
            alert("标题不能为空");
        }else if (editor.txt.text()==''){
            alert("内容不能为空");
        }else {
            var str = "title=" +
                $("#editor_title").val() +
                "&content=" + html +
                "&plate=" + $(":checked").val();
            axios.post("/blog/add",str).then(function (value) {
                if (value.data.msg == "true") {
                    window.location.href = "/blog/" + value.data.plate;
                }else {
                    alert("发送失败");
                }
            }).catch(function (reason) {
                console.log(reason);
            })
        }
        // alert(filterHtml)
    })

    // $(".editbtn").click(function () {
    //     if($(".plate").css("display")=="none"){
    //         $(".myeditor").css("display", "none");
    //         $(".plate").css("display", "block");
    //         $(".emoji_btn,.emoji_container").remove();
    //     }else {
    //         $(".plate").css("display", "none");
    //         $(".myeditor").css("display", "block");
    //         //初始化表情控件
    //         $("#editor_area>.w-e-text").emoji({
    //             showTab: false,
    //             animation: 'slide',
    //             icons: [
    //                 {
    //                     name: "贴吧表情",
    //                     path: "/image/tieba/",
    //                     maxNum: 50,
    //                     file: ".jpg",
    //                     placeholder: "#tieba_{alias}#"
    //                 },{
    //                     name: "QQ表情",
    //                     path: "/image/qq/",
    //                     maxNum: 91,
    //                     file: ".gif",
    //                     placeholder: "#qq_{alias}#"
    //                 }]
    //         });
    //         $(".myeditor #editor_submit").click(function () {
    //             var html = editor.txt.html()
    //             var filterHtml = filterXSS(html)  // 此处进行 xss 攻击过滤
    //             alert(filterHtml)
    //         })
    //     }
    // })

    var plateName = {
         "1":"古典",
         "2":"爵士",
         "3":"流行",
         "4":"民谣",
         "5":"电子",
         "6":"嘻哈",
         "7":"轻音乐",
         "8":"布鲁斯",
         "9":"摇滚"
    }
    var plateimg = {
        "1":"classical.jpg",
        "2":"jazz.jpg",
        "3":"pop.jpg",
        "4":"ballad.jpg",
        "5":"electronic.jpg",
        "6":"hiphop.jpg",
        "7":"lightmusic.jpg",
        "8":"blus.jpg",
        "9":"rock.jpg"
    }
    var plateNo;
    function getLocalTime(nS) {
        return new Date(parseInt(nS) * 1000).toLocaleString().replace(/:\d{1,2}$/,' ');
    }

    function getSimpleText(html){
        var re1 = new RegExp("<.+?>","g");//匹配html标签的正则表达式，"g"是搜索匹配多个符合的内容
        var msg = html.replace(re1,'');//执行替换成空字符
        if (msg.length>200){
            msg = msg.substr(0, 200);
        }
        return msg;
    }

    var blog_item = document.getElementById("blog_item").innerHTML;
    var hotblog = document.getElementById("hotblog").innerHTML;
    new Vue({
        el:".page_content",
        data:{
            plateName:"",
            userName:"",
            moodsign:"HelloWorld",
            headImg:"default",
            platename:"",
            title:"这是个标题",
            content:"这是内容"
        },
        mounted:function (event) {
            var href = window.location.href;
            plateNo = href.substr(href.lastIndexOf("/") + 1);
            this.platename=plateName[plateNo]
            $(".container-fluid").css("background-image", "url(/image/" + plateimg[plateNo] + ")");
            $(".plate").css("background-image", "url(/image/" + plateimg[plateNo] + ")");
            axios.get("/blog/indexInit/"+plateNo).then(function (value) {
                var hlength=value.data.hotBlogList.length
                var blogList = value.data.blogPage.list;
                var blength=value.data.blogPage.list.length
                if (blength==0){
                } else if (blength<5) {
                    for (let i = 0; i < blength; i++) {
                        $("#blog_item").after(blog_item);
                    }
                    $("#paint_blog>.blog_item").each(function (i) {
                        $(this).find("h3").text(blogList[i].title)
                        $(this).find(".titlelink").attr("href","/blog/info/"+blogList[i].bid);
                        $(this).children("p").text(getSimpleText(blogList[i].content) + "...");
                        $(this).find(".blog_time").html("<i class='fa fa-clock-o'></i>"+getLocalTime(blogList[i].blogged_time));
                        $(this).find(".blog_likes").html("<i class='fa fa-thumbs-o-up'></i>"+blogList[i].likes);
                        $(this).find(".blog_views").html("<i class='fa fa-eye'>"+blogList[i].views);
                        $(this).find(".blog_comments").html("<i class='fa fa-commenting'></i>"+blogList[i].comments);
                    })
                } else {
                    for (let i = 0; i < 5; i++) {
                        $("#blog_item").after(blog_item);
                    }
                    $("#paint_blog>.blog_item").each(function (i) {
                        $(this).find("h3").text(blogList[i].title);
                        $(this).find(".titlelink").attr("href","/blog/info/"+blogList[i].bid);
                        $(this).children("p").text(getSimpleText(blogList[i].content) + "...");
                        $(this).find(".blog_time").html("<i class='fa fa-clock-o'></i>"+getLocalTime(blogList[i].blogged_time));
                        $(this).find(".blog_likes").html("<i class='fa fa-thumbs-o-up'></i>"+blogList[i].likes);
                        $(this).find(".blog_views").html("<i class='fa fa-eye'>"+blogList[i].views);
                        $(this).find(".blog_comments").html("<i class='fa fa-commenting'></i>"+blogList[i].comments);
                    })
                }
                if (hlength==0){}
                else{
                    for (let i = 0; i < hlength; i++) {
                        $("#hotblog").after(hotblog);
                    }
                    $(".blog_nav_item>.row").each(function (i) {
                        $(this).find("p").text(value.data.hotBlogList[i].title)
                        $(this).find("small").text(value.data.hotBlogList[i].views)
                    })
                }
                console.log(value);
            }).catch(function (reason) {
                console.log(reason);
            })
        }
    })
    $("#all_search").click(function () {
        if($("#all_text").val()==""){
            alert("请输入博客标题查询");
        }else {
            var params="title="+$("#all_text").val();
            axios.post("/blog/get",params).then(function (value) {
                $("#paint_blog>.blog_item").remove();

                if (value.data.length==0){
                } else if (value.data.length<5) {
                    for (let i = 0; i < value.data.length; i++) {
                        $("#blog_item").after(blog_item);
                    }
                    $("#paint_blog>.blog_item").each(function (i) {
                        $(this).find("h3").text(value.data[i].title);
                        $(this).find(".titlelink").attr("href","/blog/info/"+value.data[i].bid);
                        $(this).children("p").text(getSimpleText(value.data[i].content) + "...");
                        $(this).find(".blog_time").html("<i class='fa fa-clock-o'></i>"+getLocalTime(value.data[i].blogged_time));
                        $(this).find(".blog_likes").html("<i class='fa fa-thumbs-o-up'></i>"+value.data[i].likes);
                        $(this).find(".blog_views").html("<i class='fa fa-eye'>"+value.data[i].views);
                        $(this).find(".blog_comments").html("<i class='fa fa-commenting'></i>"+value.data[i].comments);
                    })
                } else {
                    for (let i = 0; i < 5; i++) {
                        $("#blog_item").after(blog_item);
                    }
                    $("#paint_blog>.blog_item").each(function (i) {
                        $(this).find("h3").text(value.data[i].title);
                        $(this).find(".titlelink").attr("href","/blog/info/"+value.data[i].bid);
                        $(this).children("p").text(getSimpleText(value.data[i].content) + "...");
                        $(this).find(".blog_time").html("<i class='fa fa-clock-o'></i>"+getLocalTime(value.data[i].blogged_time));
                        $(this).find(".blog_likes").html("<i class='fa fa-thumbs-o-up'></i>"+value.data[i].likes);
                        $(this).find(".blog_views").html("<i class='fa fa-eye'>"+value.data[i].views);
                        $(this).find(".blog_comments").html("<i class='fa fa-commenting'></i>"+value.data[i].comments);
                    })
                }
                console.log(value)
            }).catch(function (reason) {
                console.log(reason);
            })
        }
    })

    $("#plate_search").click(function () {
        if($("#plate_text").val()==""){
            alert("请输入博客标题查询");
        }else {
            var params="title="+$("#plate_text").val()+"&plate="+plateNo
            axios.post("/blog/get",params).then(function (value) {
                $("#paint_blog>.blog_item").remove();

                if (value.data.length==0){
                } else if (value.data.length<5) {
                    for (let i = 0; i < value.data.length; i++) {
                        $("#blog_item").after(blog_item);
                    }
                    $("#paint_blog>.blog_item").each(function (i) {
                        $(this).find("h3").text(value.data[i].title);
                        $(this).find(".titlelink").attr("href","/blog/info/"+value.data[i].bid);
                        $(this).children("p").text(getSimpleText(value.data[i].content) + "...");
                        $(this).find(".blog_time").html("<i class='fa fa-clock-o'></i>"+getLocalTime(value.data[i].blogged_time));
                        $(this).find(".blog_likes").html("<i class='fa fa-thumbs-o-up'></i>"+value.data[i].likes);
                        $(this).find(".blog_views").html("<i class='fa fa-eye'>"+value.data[i].views);
                        $(this).find(".blog_comments").html("<i class='fa fa-commenting'></i>"+value.data[i].comments);
                    })
                } else {
                    for (let i = 0; i < 5; i++) {
                        $("#blog_item").after(blog_item);
                    }
                    $("#paint_blog>.blog_item").each(function (i) {
                        $(this).find("h3").text(value.data[i].title);
                        $(this).find(".titlelink").attr("href","/blog/info/"+value.data[i].bid);
                        $(this).children("p").text(getSimpleText(value.data[i].content) + "...");
                        $(this).find(".blog_time").html("<i class='fa fa-clock-o'></i>"+getLocalTime(value.data[i].blogged_time));
                        $(this).find(".blog_likes").html("<i class='fa fa-thumbs-o-up'></i>"+value.data[i].likes);
                        $(this).find(".blog_views").html("<i class='fa fa-eye'>"+value.data[i].views);
                        $(this).find(".blog_comments").html("<i class='fa fa-commenting'></i>"+value.data[i].comments);
                    })
                }
                console.log(value)
            }).catch(function (reason) {
                console.log(reason);
            })
        }
    })

    $("")
    /*油画工具淡入淡出*/
    $(".fa-paint-brush").click(function () {
        var $paint=$(".customizer");
        if (!$paint.hasClass("fadeInLeft")){
            $(".customizer").removeClass("animated fadeOutLeft")
                .addClass("animated fadeInLeft");
        }else {
            $paint.removeClass("animated fadeInLeft")
                .addClass("animated fadeOutLeft");
        }
    })
})