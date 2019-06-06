$(function () {
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

    var plateNo;
    var editor;
    function getStrLength( str ){
        return str.replace(/[\u0391-\uFFE5]/g,"aa").length; //"g" 表示全局匹配
    }

    Vue.filter("dateformat",function (value) {
        return new Date(parseInt(value) * 1000).toLocaleString().replace(/:\d{1,2}$/,' ');
    })
    Vue.filter("getSimpleText",function (html) {
        var re1 = new RegExp("<.+?>","g");//匹配html标签的正则表达式，"g"是搜索匹配多个符合的内容
        var msg = html.replace(re1,'');//执行替换成空字符
        if (msg.length>200){
            msg = msg.substr(0, 200);
        }
        return msg;
    })
    var blogComponent={
        props:["blog"],
        template:"#blog_item",
    };
    var blogContentComponent={
        data:function(){
            return{
                plateKey:""
            }
        },
        props:["blogs"],
        template:"#blog_content",
        components: {
            'blog-item':blogComponent,
        },
        methods:{
            plateSearch:function () {
                var params = "title=" + this.plateKey + "&plate=" + plateNo;
                axios.post("/blog/get",params).then(function (value) {
                    app.blogs = value.data.list;
                    console.log(value)
                }).catch(function (reason) {
                    console.log(reason);
                })
            }
        }
    };
    var hotblogComponent={
        props:["hotblog"],
        template: "#hotblog"
    };
    var blogEditComponent={
        data:function(){
            return{
                title:"",
                plate:1,
            }
        },
        template:"#blog_edit",
        methods:{
            checkTitle:function () {
                var titleval = this.title.replace(/\s*/g,"");;
                if (getStrLength(titleval) > 40) {
                    var i;
                    for (i=20;i<=41;i++){
                        if(getStrLength(titleval.substr(0,i))>40){
                            break;
                        }
                    }
                    this.title=titleval.substr(0, i-1);
                }else {
                    this.title = titleval;
                }
            },
            submitBlog:function () {
                var html = editor.txt.html()
                var filterHtml = filterXSS(html)  // 此处进行 xss 攻击过滤
                if (this.title == '') {
                    alert("标题不能为空");
                } else if (editor.txt.text() == '') {
                    alert("内容不能为空");
                } else {
                    var str = "title=" +
                        this.title +
                        "&content=" + filterHtml +
                        "&plate=" + this.plate;
                    axios.post("/blog/add", str).then(function (value) {
                        if (value.data.msg == "true") {
                            window.location.href = "/blog/" + value.data.plate;
                        } else {
                            alert("发送失败");
                        }
                    }).catch(function (reason) {
                        console.log(reason);
                    })
                }

            }
        }
    }
    var app=new Vue({
        el:".page_container",
        data:{
            blog:{
                mLabUser:{}
            },
            blogs:[],
            hotblog:{
                mLabUser:{}
            },
            hotblogs:[],
            plateName:"",
            moodsign:"HelloWorld",
            isIndex:true,
            editCache:false,
            allKey:"",
        },
        components:{
            'blog-content':blogContentComponent,
            'hotblog-item':hotblogComponent,
            'blog-edit':blogEditComponent,
        },
        mounted:function (event) {
            var href = window.location.href;
            plateNo = href.substr(href.lastIndexOf("/") + 1);
            axios.get("/blog/indexInit/"+plateNo).then(function (value) {
                app.plateName = value.data.plateName;
                app.blogs = value.data.blogPage.list;
                app.hotblogs = value.data.hotBlogList;
                console.log(value);
            }).catch(function (reason) {
                console.log(reason);
            })
        },
        methods:{
            toggleEdit:function () {
                app.isIndex = !app.isIndex;
                setTimeout(function () {
                    if (app.editCache==false) {
                        app.editCache = true;
                        /*富文本编辑器初始化*/
                        var E = window.wangEditor;
                        editor = new E("#toolbar", "#editor_area");
                        editor.customConfig.zIndex = 0;
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

                        //初始化表情控件
                        $("#editor_area>.w-e-text").emoji({
                            showTab: false,
                            animation: 'slide',
                            button: "#emojibtn",
                            icons: [
                                {
                                    name: "贴吧表情",
                                    path: "/image/tieba/",
                                    maxNum: 50,
                                    file: ".jpg",
                                    placeholder: "#tieba_{alias}#"
                                }, {
                                    name: "QQ表情",
                                    path: "/image/qq/",
                                    maxNum: 91,
                                    file: ".gif",
                                    placeholder: "#qq_{alias}#"
                                }]
                        });
                    }
                },5)
            },
            /*油画工具淡入淡出*/
            togglePalette:function () {
                var $paint=$(".customizer");
                if (!$paint.hasClass("fadeInLeft")){
                    $(".customizer").removeClass("animated fadeOutLeft")
                        .addClass("animated fadeInLeft");
                }else {
                    $paint.removeClass("animated fadeInLeft")
                        .addClass("animated fadeOutLeft");
                }
            },
            allSearch:function () {
                var params="title="+this.allKey;
                axios.post("/blog/get",params).then(function (value) {
                    app.blogs = value.data.list;
                    console.log(value)
                }).catch(function (reason) {
                    console.log(reason);
                })
            }
        }
    })
})