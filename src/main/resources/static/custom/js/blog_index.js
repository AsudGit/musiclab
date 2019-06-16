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
    //重设容器高度
    function resetHeight(){
        var rightnavheight = $(".right_nav>div").height();
        var contentHeight = $(".blog_content>div").height();
        var cotainerHeight = (contentHeight > rightnavheight ? contentHeight : rightnavheight) + 50;
        $(".container-fluid").css("height", cotainerHeight + $("#myNav2").height() + "px");
        $(".page_content").css("height", cotainerHeight + "px");
        $(".right_nav").css("height", cotainerHeight+ "px");
    }

    function getLegalStr(str,maxLength){
        var val = str.replace(/\s*/g,"");;
        if (getStrLength(val) > maxLength) {
            var i;
            for (i=maxLength/2;i<=maxLength+1;i++){
                if(getStrLength(val.substr(0,i))>maxLength){
                    break;
                }
            }
            return val.substr(0, i-1);
        }else {
            return val;
        }
    }

    Vue.filter("dateformat",function (value) {
        return new Date(value).toLocaleString().replace(/:\d{1,2}$/, ' ');
    })
    Vue.filter("getSimpleText",function (html) {
        var re1 = new RegExp("<.+?>|&.+;","g");//匹配html标签的正则表达式，"g"是搜索匹配多个符合的内容
        var msg = html.replace(re1,'');//执行替换成空字符
        if (msg.length>200){
            msg = msg.substr(0, 200);
        }
        return msg;
    })
    var blogComponent={
        props:["blog"],
        template:"#blog_item",
        methods:{
            toInfo:function (blog) {
                app.blogContent = 'info';
                app.bloginfo = blog;
                setTimeout(function () {
                    resetHeight();
                }, 500);
            }
        }
    };
    var blogCommentComponent={
        template:"#blog_comment",
        mounted:function(){
            //初始化表情控件
            $(".comment_edit>textarea").emoji({
                showTab: false,
                animation: 'slide',
                button: ".comment_emoji",
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
        },
        methods:{
            editfocus:function (event) {
                $(event.target).animate({"height":"100px"},500);
            },
            editblur:function (event) {
                if($(event.explicitOriginalTarget).closest(".emoji_container").length==0&&!$(event.relatedTarget).hasClass("emoji_btn")){
                    $(event.target).animate({"height":"40px"},500);
                }
            },
            toggleComment:function (event) {
                var $temp = $(".comment_edit");
                var $target = $(event.target);
                $(".comment_edit").remove();
                if($target.text()=='回复') {
                    $(".comment_item .btn-info:contains('取消回复')").text('回复');
                    $target.text('取消回复');
                    $(event.target).closest(".col-md-11").after($temp);
                }else {
                    $target.text('回复');
                    $("#info_comment>h3").prepend($temp);
                }
                $(".comment_edit>textarea").val('');
                $(".emoji_container").css("top", $(".comment_emoji").offset().top + "px");
            },
        }
    }
    var blogInfoComponent={
        data:function(){
            return {
                Replying: false,
            };
        },
        props:["bloginfo"],
        template:"#blog_info",
        components:{
          'blog-comment':  blogCommentComponent,
        },
        methods:{
            toIndex:function () {
                app.blogContent = 'index';
                setTimeout(function () {
                    resetHeight();
                },5);
            },
        },
        mounted:function () {
            $(".tooltip-goal").each(function () {
                $(this).tooltip({
                    //指定显示时延迟和消失时延迟
                    delay: {show: 100, hide: 300}
                })
            })
        },
    };
    var blogContentComponent={
        data:function(){
            return{
                plateKey:"",
            }
        },
        props:["blogpage"],
        template:"#blog_content",
        components: {
            'blog-item':blogComponent,
        },
        methods:{
            plateSearch:function () {
                var params = "title=" + this.plateKey + "&plate=" + plateNo;
                axios.post("/blog/get",params).then(function (value) {
                    app.blogContent = 'index';
                    app.blogpage = value.data;
                    app.plateNo = plateNo;
                    console.log(value)
                }).catch(function (reason) {
                    console.log(reason);
                })
            },
            forwardTo:function (pageNum) {
                if (pageNum<1||pageNum>app.blogpage.pages) return;
                if (app.plateNo == 0){
                    var params = "title=" + app.allKey + "&start=" + (pageNum);
                }else {
                    var params = "title=" + this.plateKey + "&plate=" + app.plateNo + "&start=" + (pageNum);
                }
                axios.post("/blog/get",params).then(function (value) {
                    app.blogpage = value.data;
                    console.log(value)
                }).catch(function (reason) {
                    console.log(reason);
                })

            }
        },
        computed:{
            prePages:function(){
                var num = this.blogpage.pageNum;
                if (num==1 ||num ==null||this.blogpage.list.length==0)return '';
                else if (this.blogpage.pages>1 && this.blogpage.pages<5) {
                    var prePages = new Array();
                    for (let i = 0; i < num-1; i++) {
                        prePages[i] = i+1;
                    }
                }
                else if (num ==(this.blogpage.pages-1)||num==this.blogpage.pages) {
                    var prePages = new Array();
                    for (let i = this.blogpage.pages-4,j=0; i < this.blogpage.pageNum; i++,j++) {
                        prePages[j] = i;
                    }
                }else {
                    var prePages = new Array();
                    for (let i = this.blogpage.pageNum-2,j=0; i < this.blogpage.pageNum; i++,j++) {
                        prePages[j] = i;
                    }
                }
                return prePages;
            },
            afterPages:function () {
                var num = this.blogpage.pageNum;
                var pages = this.blogpage.pages;
                if (num==pages || num==null)return '';
                else if (pages>1&&pages<5) {
                    var afterPages = new Array();
                    for (let i = num+1,j=0; i <= pages; i++,j++) {
                        afterPages[j] = i;
                    }
                }
                else if (num ==2||num==1) {
                    var afterPages = new Array();
                    for (let i = num+1,j=0; i < 6; i++,j++) {
                        afterPages[j] = i;
                    }
                }else {
                    var afterPages = new Array();
                    for (let i = num+1,j=0; i < num+3; i++,j++) {
                        afterPages[j] = i;
                    }
                }
                return afterPages;
            },
            banBackward:function () {
                return this.blogpage.pageNum==1||this.blogpage.list.length==0;
            },
            banForward:function () {
                return this.blogpage.pageNum == this.blogpage.pages;
            }
        }
    };
    var hotblogComponent={
        props:["hotblog"],
        template: "#hotblog",
        methods:{
            toInfo:function (blog) {
                app.blogContent = 'info';
                app.bloginfo = blog;
            }
        },
    };
    var blogEditComponent={
        data:function(){
            return{
                title:"",
                plate:0,
                tagval:"",
                check:[],
                tags:[],
            }
        },
        template:"#blog_edit",
        mounted:function(){
            this.plate = plateNo;
        },
        methods:{
            checkTitle:function () {
                this.title = getLegalStr(this.title, 40);
            },
            checkTag:function(){
                this.tagval=getLegalStr(this.tagval, 14);
            },
            addTag:function(){
                if (this.tagval==""||this.check[this.tagval]==1){
                    return;
                } else {
                    this.check[this.tagval] = 1;
                    Vue.set(this.tags, this.tags.length, this.tagval);
                }
            },
            deleteTag:function(index){
                this.check.splice(this.tags[index], 1);
                this.tags.splice(index,1);
            },
            submitBlog:function () {
                var html = editor.txt.html()
                var filterHtml = filterXSS(html)  // 此处进行 xss 攻击过滤

                if (this.title == '') {
                    alert("标题不能为空");
                } else if (editor.txt.text() == '') {
                    alert("内容不能为空");
                } else {
                    axios.post("/blog/add",{
                        blog:{
                            title:this.title,
                            content:filterHtml,
                            plate:this.plate,
                        },
                        names: this.tags,
                    }).then(function (value) {
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
        },
    }

    var app=new Vue({
        el:".page_container",
        data:{
            bloginfo:{
            },
            blogpage:{
                list:[],
            },
            hotblogs:[],
            plateName:"",
            moodsign:"HelloWorld",
            blogContent:"index",
            editCache:false,
            plates:false,
            allKey:"",
            plateNo:0,//用于区分博客页当前的结果集属于哪个板块
        },
        components:{
            'blog-content':blogContentComponent,
            'hotblog-item':hotblogComponent,
            'blog-edit':blogEditComponent,
            'blog-info':blogInfoComponent,
        },
        watch:{
            blogpage:function () {
                this.$nextTick(function(){
                    /*现在数据已经渲染完毕*/
                    resetHeight();
                })
            },
            bloginfo:function () {
                this.$nextTick(function () {
                    resetHeight();
                })
            }
        },
        mounted:function (event) {
            var href = window.location.href;
            plateNo = href.substr(href.lastIndexOf("/")+1);
            this.plateNo = plateNo;
            axios.get("/blog/indexInit/"+plateNo).then(function (value) {
                app.plateName = value.data.plateName;
                app.blogpage = value.data.blogPage;
                app.hotblogs = value.data.hotBlogList;
                console.log(value);
            }).catch(function (reason) {
                console.log(reason);
            })
        },
        methods:{
            toggleEdit:function () {
                if (app.blogContent != 'edit') {
                    app.blogContent = 'edit';
                }else{
                    app.blogContent = 'index';
                }
                setTimeout(function () {
                    if (app.editCache==false) {
                        app.editCache = true;
                        /*富文本编辑器初始化*/
                        var E = window.wangEditor;
                        editor = new E("#toolbar", "#editor_area");
                        //开启debug模式
                        editor.customConfig.debug = true;
                        // 关闭粘贴内容中的样式
                        editor.customConfig.pasteFilterStyle = true;
                        // 忽略粘贴内容中的图片
                        editor.customConfig.pasteIgnoreImg = true;
                        // 上传图片到服务器
                        editor.customConfig.uploadFileName = "imgFile"; //设置文件上传的参数名称
                        editor.customConfig.uploadImgServer = "/upload/image"; //设置上传文件的服务器路径
                        editor.customConfig.uploadImgMaxSize = 3 * 1024 * 1024; // 将图片大小限制为 3M
                        editor.customConfig.uploadImgMaxLength = 9;// 限制一次最多上传 9 张图片
                        // editor.customConfig.withCredentials = true;//跨域上传中如果需要传递 cookie 需设置 withCredentials
                        //自定义上传图片事件
                        editor.customConfig.uploadImgHooks = {
                            before : function(xhr, editor, files) {

                            },
                            success : function(xhr, editor, result) {
                                console.log("上传成功");
                            },
                            fail : function(xhr, editor, result) {
                                alert("上传失败");
                                console.log("上传失败,原因是"+result.data[0]);
                            },
                            error : function(xhr, editor) {
                                alert("上传出错");
                                console.log("上传出错");
                            },
                            timeout : function(xhr, editor) {
                                console.log("上传超时");
                            }
                        }
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
                            // 'video',  // 插入视频
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
                    app.blogContent='index'
                    app.blogpage = value.data;
                    app.plateNo = 0;
                    console.log(app.blogpage)
                }).catch(function (reason) {
                    console.log(reason);
                })
            }
        }
    })
})