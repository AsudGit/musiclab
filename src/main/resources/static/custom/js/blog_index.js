$(function () {
    var plateNo;
    var editor;

    getPlateNo();
    function getPlateNo() {
        var href = window.location.href;
        var start = href.indexOf("blog/") + 5;
        var length = href.indexOf("#") - start;
        plateNo = href.substr(start,length);
    }


    //获得字符串字节长度
    function getStrLength( str ){
        return str.replace(/[\u0391-\uFFE5]/g,"aa").length; //"g" 表示全局匹配
    }
    //限制字符串长度
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
    //进入详情页
    function toInformation(blogitem,$this){
        routerPush("/info",{bloginfo: blogitem})
    }
    function search(plateKey) {
        var params = "title=" + plateKey + "&plate=" + plateNo;
        axios.post("/blog/get",params).then(function (value) {
            app.plateNo = plateNo;
            routerPush("/index", {blogpage: value.data});
        }).catch(function (reason) {
            console.log(reason);
        })
    }
    //路由跳转
    function routerPush(path,value){
        routerObj.push({
            path:path,
            query:{value:JSON.stringify(value)}
        })
    }
    //获取路由传送的参数，并转为json
    function routerGet($this){
        return JSON.parse($this.$route.query.value);
    }
    //表情代码转化为表情包
    function emojiParse(content){
        var re2 = new RegExp("#tieba_[0-9]+#", "g");
        content = content.replace(re2, "<img src=\"/image/$&.jpg\">")
            .replace(/tieba_/g, "tieba/");
        var re3 = new RegExp("#qq_[0-9]+#", "g");
        content = content.replace(re3, "<img src=\"/image/$&.png\">")
            .replace(/qq_/g, "qq/");
        content=content.replace(/#/g,'');
        return content;
    }
    //切换回复框
    function toggle_comment(cid,$this){
        var $temp = $(".comment_edit");
        var $target = $(event.target);
        $(".comment_edit").remove();
        if($target.text()=='回复') {
            $(".comment_item .btn-info:contains('取消回复')").text('回复');
            $target.text('取消回复');
            $(event.target).closest(".col-md-11").after($temp);
            app.targetID = cid;
            if ($this.commentitem!=null){
                $(".comment_edit>textarea").val("回复@"+$this.commentitem.mLabUser.name+":");
            } else {
                $(".comment_edit>textarea").val("回复@"+$this.childcomment.mLabUser.name+":");
            }
        }else {
            $target.text('回复');
            $("#info_comment>h3").prepend($temp);
            app.targetID = $this.bid;
            $(".comment_edit>textarea").val('');
        }
    }
    //获得相差的天数
    function getDays(closest,farthest){
        var date = (closest.getTime() - farthest.getTime()) / (1000 * 60 * 60 * 24);
        return parseInt(date);
    }

    //日期格式转换
    Vue.filter("dateformat",function (value) {
        return new Date(value).toLocaleString().replace(/:\d{1,2}$/, ' ');
    })
    //获得纯文本
    Vue.filter("getSimpleText",function (html) {
        var re1 = new RegExp("<.+?>|&.+;","g");//匹配html标签的正则表达式，"g"是搜索匹配多个符合的内容
        var msg = html.replace(re1,'');//执行替换成空字符
        if (msg.length>200){
            msg = msg.substr(0, 200);
        }
        return msg;
    })
    var blogComponent={
        props:["blogitem"],
        template:"#blog_item",
        methods:{
            toInfo:function (blogitem) {
                toInformation(blogitem,this);
            }
        }
    };

    var blogTagComponent={
        props:["tag"],
        template:"#blog_tag",
    }

    var commentChildItemComponent={
        props:["cid","childcomment","bid"],
        template:"#comment_child_item",
        methods:{
            toggleComment:function (cid) {
                toggle_comment(cid,this);
            }
        }
    }

    var commentParentItemComponent={
        data: function () {
            return {
                childComments:[],
                pageNum:0,
                pages:0,
                pageSize:0,
                total:0,
                cid:"",
            }
        },
        props:["commentitem","bid"],
        template:"#comment_parent_item",
        components:{
            "comment-child": commentChildItemComponent
        },
        mounted:function(){
            this.cid = this.commentitem.comment.cid;
            var $this = this;
            axios.get("/comment/list",{params:{id:this.cid,}}).then(function (value) {
                $this.childComments = value.data.list;
                $this.pageNum = value.data.pageNum;
                $this.pages = value.data.pages;
                $this.pageSize = value.data.pageSize;
                $this.total = value.data.total;
                console.log(value.data);
            }).catch(function (reason) {
                console.log(reason);
            })
        },
        methods:{
            toggleComment:function (cid) {
                toggle_comment(cid,this);
            },
            allComment:function (event) {
                var $this = this;
                axios.get("/comment/list/limit",{
                    params:{
                        id:this.cid,
                        start:this.pageSize,
                        size:this.total-this.pageSize,
                    }
                }).then(function (value) {
                    var comments = event.target.parentElement.parentElement.__vue__.childComments;
                    var newComments = value.data;
                    for (let i = 0; i < newComments.length; i++) {
                        comments.push(newComments[i]);
                    }
                    $this.pageNum = $this.pages;
                }).catch(function (reason) {
                    console.log(reason);
                })
            }
        },
    }

    var blogCommentComponent={
        data:function(){
            return{
                commentItems:[],
                pageNum:1,
                pages:1,
            }
        },
        props:["bid"],
        template:"#blog_comment",
        components:{
          "comment-parent":commentParentItemComponent,
        },
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
                        maxNum: 99,
                        file: ".png",
                        placeholder: "#qq_{alias}#"
                    }]
            });
        },
        watch:{
          bid:function () {
              var $this = this;
              app.targetID = this.bid;
              axios.get("/comment/list",{params:{id:this.bid,}}).then(function (value) {
                  $this.commentItems = value.data.list;
                  $this.pageNum = value.data.pageNum;
                  $this.pages = value.data.pages;
              }).catch(function (reason) {
                  console.log(reason);
              })
          }
        },
        methods:{
            editfocus:function (event) {
                $(event.target).animate({"height":"100px"},500);
                $(".comment_warm").text("");
            },
            editblur:function (event) {
                if($(event.explicitOriginalTarget).closest(".emoji_container").length==0&&!$(event.relatedTarget).hasClass("emoji_btn")){
                    $(event.target).animate({"height":"40px"},500);
                }
            },
            commenting:function (event) {
                var html = $(".comment_edit>textarea").val();
                if(html==""||html==null){
                    $(".comment_warm").text("不能为空");
                    return;
                }
                var regExp = new RegExp("@.+:");
                html = html.replace(regExp, "<a style='color:orangered;'>$&<a/>");
                var re1 = new RegExp("#tieba_[0-9]+#|#qq_[0-9]+#","g");
                var text = html.replace(re1, "");
                if (getStrLength(text) >250){
                    $(".comment_warm").text("字数超出范围");
                    return;
                }
                var content = emojiParse(html);
                if (getStrLength(content) >500){
                    $(".comment_warm").text("表情包数量超出范围");
                    return;
                }
                var $this = this;
                axios.get("/comment/add",{
                    params:{
                        content:content,
                        id:app.targetID,
                        plate:plateNo,
                    }
                }).then(function (value) {
                    if (value.data.msg == true) {
                        $(".comment_edit>textarea").val("");
                        if (app.targetID==$this.bid) {
                            $this.commentItems.unshift(value.data.commentItem);
                            console.log(value.data.comment);
                        }else {
                            //异步渲染子评论
                            if (event.target.parentElement.parentElement.__vue__.childComments!=null) {
                                event.target.parentElement.parentElement.__vue__.childComments.unshift(value.data.commentItem);
                            }else {
                                event.target.parentElement.parentElement.parentElement.parentElement
                                    .__vue__.childComments.unshift(value.data.commentItem);
                            }
                            var $temp = $(".comment_edit");
                            $(".comment_edit").remove();
                            $("#info_comment .btn-info:contains(取消回复)").text('回复');
                            $("#info_comment>h3").prepend($temp);
                            app.targetID = $this.bid;
                            $(".emoji_container").css("top", $(".comment_emoji").offset().top + "px");
                        }
                    }else {
                        $(".comment_warm").text("评论失败");
                    }
                }).catch(function (reason) {
                    console.log(reason);
                })
            },
            moreComment:function () {
                var $this = this;
                axios.get("/comment/list",{
                    params:{
                        id:this.bid,
                        start:this.pageNum+1,
                    }
                }).then(function (value) {
                    var newCommentItems=value.data.list;
                    for (let i = 0; i < newCommentItems.length; i++) {
                        $this.commentItems.push(newCommentItems[i]);
                    }
                    $this.pageNum = value.data.pageNum;
                    $this.pages = value.data.pages;
                    console.log(value)
                }).catch(function (reason) {
                    console.log(reason);
                })
            },
            reOffset:function () {
                //重定位表情面板
                $(".emoji_container").css("top", $(".comment_emoji").offset().top + "px");
            }
        }
    }

    var blogInfoComponent={
        data:function(){
            return {
                Replying: false,
                bloginfo:{
                    blog:{},
                    mLabUser:{},
                    },
                tags:[],
            };
        },
        template:"#blog_info",
        components:{
            'blog-comment':blogCommentComponent,
            'blog-tag':blogTagComponent,
        },
        watch:{
            $route:function () {
              this.bloginfo = routerGet(this).bloginfo;
            },
            bloginfo:function () {
                this.$nextTick(function () {
                    var $this = this;
                    axios.get("/tag/list/"+this.bloginfo.blog.bid).then(function (value) {
                        $this.tags = value.data;
                    }).catch(function (reason) {
                        console.log(reason);
                    })
                })
            }
        },
        mounted:function () {
            this.bloginfo = routerGet(this).bloginfo;
            $(".tooltip-goal").each(function () {
                $(this).tooltip({
                    //指定显示时延迟和消失时延迟
                    delay: {show: 100, hide: 300}
                })
            })
        },
        methods:{
            toIndex:function () {
                search("");
            },
            toComment:function () {
                $("html,body").animate({
                    scrollTop: $(".comment_edit>textarea").offset().top-270},
                    500,
                    function () {
                    $(".comment_edit>textarea").focus();
                })
            },
            toTop:function () {
                $("html,body").animate({scrollTop: 0}, 500);
            }
        },
    };

    var moodsignItemComponent={
        props:["moodsign"],
        template:"#moodsign_item",
    }

    var mlabuserInfoComponent={
        data:function(){
          return{
              enteredCover:false,
              enteredEdit:false,
              content:"",
              moodsigns:[],
          }
        },
        template:"#mlabuser_info",
        components:{
            'moodsign-item':moodsignItemComponent,
        },
        mounted:function(){
            var $this = this;
            axios.get("/sign/get").then(function (value) {
                $this.moodsigns = value.data.list;
            }).catch(function (reason) {
                console.log(reason);
            })
        },
        methods:{
            signing:function () {
                if (this.content==null||this.content==""){
                    return;
                }
                if (getStrLength(this.content)>40) {
                    alert("写得太多啦");
                }else {
                    var $this = this;
                    axios.get("/sign/add",{
                        params:{
                            content: this.content,
                            uid:app.userID,
                        }
                    }).then(function (value) {
                        if (value.data.msg = "true") {
                            $this.moodsigns.unshift(value.data.moodsign);
                            $this.content = "";
                        }
                    }).catch(function (reason) {
                        console.log(reason);
                    })
                }
            }
        }
    }

    var blogContentComponent={
        data:function(){
            return{
                plateKey:"",
                blogpage:{
                    list:[],
                },
                total:{
                    blogNums:0,
                    commentNums:0,
                    newNums:0,
                }
            }
        },
        template:"#blog_content",
        components: {
            'blog-item':blogComponent,
        },
        mounted:function(){
            getPlateNo();
            var params = "plate=" + plateNo;
            var $this = this;
            axios.post("/blog/get",params).then(function (value) {
                $this.blogpage = value.data;
            }).catch(function (reason) {
                console.log(reason);
            })
            axios.get("/blog/total",{
                params:{
                    plate:plateNo,
                }
            }).then(function (value) {
                $this.total = value.data;
            }).catch(function (reason) {
                console.log(reason);
            })
        },
        watch:{
            $route:function () {
              this.blogpage = routerGet(this).blogpage;
            },
            /*blogpage:function () {
                this.$nextTick(function(){
                    /!*现在数据已经渲染完毕*!/
                })
            },*/
        },
        methods:{
            plateSearch:function () {
                search(this.plateKey);
            },
            forwardTo:function (pageNum) {
                if (pageNum<1||pageNum>this.blogpage.pages) return;
                if (app.plateNo == 0){
                    var params = "title=" + app.allKey + "&start=" + (pageNum);
                }else {
                    var params = "title=" + this.plateKey + "&plate=" + app.plateNo + "&start=" + (pageNum);
                }
                axios.post("/blog/get",params).then(function (value) {
                    routerPush("/index",{blogpage: value.data})
                }).catch(function (reason) {
                    console.log(reason);
                })
            }
        },
        computed:{
            prePages:function(){
                var num = this.blogpage.pageNum;
                if (num==1 ||num ==null||this.blogpage.list.length==0)return '';
                else if (num == 2) {
                    var prePages = [1];
                }
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
                else if (num == pages - 1) {
                    var afterPages = [pages];
                }
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
            toInfo:function (blogitem) {
                toInformation(blogitem,this);
            }
        },
    };
    var mLabUserCountComponent={
        data:function(){
          return{
              mLabUserCount:"",
          }
        },
        template:"#mlabuser_count",
        mounted:function () {
            var $this = this;
            axios.get("/count/get").then(function(value) {
                $this.mLabUserCount = value.data;
                console.log(value);
            }).catch(function (reason) {
                console.log(reason);
            })
        },
        computed: {
            recentlyLogin:function () {
                var current = new Date();
                var recent = new Date(this.mLabUserCount.recentlyLogin);
                return getDays(current,recent);
            },
            registerDay:function () {
                var current = new Date();
                var register = new Date(this.mLabUserCount.registeredDate);
                var day = getDays(current, register);
                var year = parseInt(day / 365);
                if (year > 0) {
                    if (day % 365 == 0) {
                        return year + "年";
                    }else {
                        return year + "年" + day % 365 + "天";
                    }
                }else {
                    return day+"天"
                }
            }
        }
    }
    var rightNavComponent={
        template:"#right_nav",
        data:function(){
          return{
              hotblogs:[],
              tags:[],
          }
        },
        components:{
            'hotblog-item':hotblogComponent,
            'mlabuser-count':mLabUserCountComponent,
            'blog-tag':blogTagComponent,
        },
        mounted:function () {
            var $this = this;
            axios.get("/blog/hot").then(function (value) {
                $this.hotblogs = value.data;
            }).catch(function (reason) {
                console.log(reason);
            })
            axios.get("/tag/hot",{
                params:{
                    start:0,
                    size: 20,
                }
            }).then(function (value) {
                $this.tags = value.data;
                console.log(value);
            }).catch(function (reason) {
                console.log(reason);
            })
        }
    }
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
            getPlateNo();
            this.plate = plateNo;
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
                        // 'undo',  // 撤销
                        // 'redo'  // 重复
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
                                maxNum: 99,
                                file: ".png",
                                placeholder: "#qq_{alias}#"
                            }]
                    });
                }
            },5)
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
                    this.check[this.tagval] = 1;//标记出现一次
                    Vue.set(this.tags, this.tags.length, this.tagval);
                }
            },
            deleteTag:function(index){
                this.check.splice(this.tags[index], 1);//删除标记
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
    var routes=[
        {
            path:'/index',
            name:'index',
            alias:'/',
            component:blogContentComponent,
        },
        {
            path:'/user',
            name:'user',
            component:mlabuserInfoComponent,
        },
        {
            path:'/info',
            name:'info',
            component:blogInfoComponent,
        },
        {
            path:'/edit',
            name:'edit',
            component:blogEditComponent,
            meta:{
                keepAlive:true,
            }
        },
    ]

    var routerObj=new VueRouter({
        // mode:'history',
        routes:routes
    })

    var app=new Vue({
        el:"#app",
        data:{
            moodsign:"HelloWorld",//TODO:签名
            editCache:false,
            plates:false,//板块列表开关
            allKey:"",
            userID:"",
            plateNo:0,//用于区分博客页当前的结果集属于哪个板块
            targetID:"",//当前评论框指向的id
        },
        components:{
            'right-nav':rightNavComponent
        },
        router:routerObj,
        mounted:function () {
            this.plateNo = plateNo;
            axios.get("/mlabuser/isLogin").then(function (value) {
                app.userID = value.data.userID;
            }).catch(function (reason) {
                console.log(reason);
            })
        },
        methods:{
            toggleEdit:function () {
                if (this.$route.name != 'edit') {
                    routerObj.push({name:'edit'})
                }else{
                    routerObj.go(-1)
                }
            },
            toUserInfo:function(){
              routerObj.push({name: 'user'})
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
                var $this = this;
                axios.post("/blog/get",params).then(function (value) {
                    app.plateNo = 0;
                    routerPush("/index",{blogpage: value.data})
                }).catch(function (reason) {
                    console.log(reason);
                })
            }
        }
    })
})