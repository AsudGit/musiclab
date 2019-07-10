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
    //博客点赞函数
    function likeBlog(thisBlog){
        if (thisBlog.like) {
            thisBlog.blogCount.likes = thisBlog.blogCount.likes - 1;
        } else {
            thisBlog.blogCount.likes = thisBlog.blogCount.likes + 1;
        }
        axios.get("/blog/like",{
            params:{
                like:thisBlog.like,
                bid:thisBlog.blog.bid,
            }
        })
        thisBlog.like = !thisBlog.like;
    }


    //日期格式转换
    Vue.filter("dateformat",function (value) {
        return new Date(value).toLocaleString().replace(/:\d{1,2}$/, ' ');
    })
    Vue.filter("standardDate",function (value) {
        var date = new Date(value);
        var mouth = date.getMonth() > 9 ? date.getMonth() : '0' + date.getMonth();
        var day = date.getDate() > 9 ? date.getDate() : '0' + date.getDate();
        return date.getFullYear()+'-'+mouth+'-'+day;
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
        data:function(){
            return{
                showBlogger: false,
                wait: false,
                loaded:false,
                mLabUser:{},
                mLabUserCount:{},
                moodsign:{},
                liking:false,
            }
        },
        props:["blogitem"],
        template:"#blog_item",
        methods:{
            toInfo:function (blogitem) {
                routerPush("/info",{bloginfo: blogitem})
            },
            toUserPage:function () {
                routerPush("/user",{
                    uid: this.blogitem.mLabUser.uid,
                    target:"sign",
                })
            },
            like:function(){
                if (!this.liking){
                    this.liking = true;
                    likeBlog(this.blogitem);
                    this.liking = false;
                }
            },
            starModal:function(){
                app.starBlogComp = this;
                axios.get("/blog/star/folder/get").then(function (value) {
                    app.starFolders = value.data;
                }).catch(function (reason) {
                    console.log(reason);
                })
            },
            cancelStar:function(){
                var $this = this;
                axios.get("/blog/star/cancel/" + this.blogitem.blog.bid).then(function (value) {
                    $this.blogitem.star = false;
                }).catch(function (reason) {
                    console.log(reason);
                })
            },
            showBloggerInfo:function () {
                this.wait = true;
                var $this = this;
                setTimeout(function () {
                    if ($this.wait == true) {
                        $this.showBlogger = true;
                        //TODO:把settimeout改为请求
                        if($this.loaded==false) {
                            axios.get("/mlabuser/info/" + $this.blogitem.mLabUser.uid).then(function (value) {
                                $this.moodsign = value.data.moodsign;
                                $this.mLabUser = value.data.mLabUser;
                                $this.mLabUserCount = value.data.mLabUserCount;
                                $this.loaded = true;
                            }).catch(function (reason) {
                                console.log(reason);
                            })
                        }
                    }
                },400)
            },
            hideBloggerInfo:function () {
                this.wait = false;
                this.showBlogger = false;
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
            },
            toUserPage:function () {
                routerPush("/user",{
                    uid: this.childcomment.mLabUser.uid,
                    target:"sign",
                })
            },
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
            },
            toUserPage:function () {
                routerPush("/user",{
                    uid: this.commentitem.mLabUser.uid,
                    target:"sign",
                })
            },
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
        props:["bid","commentnum"],
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
                        bid:this.bid,
                        plate:plateNo,
                    }
                }).then(function (value) {
                    if (value.data.msg == true) {
                        $this.commentnum += 1;
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
                    mLabUser:{},
                    blog:{},
                    blogCount:{},
                    like:false,
                    star:false,
                },
                tags:[],
                liking:false,
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
                var $this = this;
                axios.get("/blog/view/"+this.bloginfo.blog.bid).then(function (value) {
                    if(value.data==true){
                        $this.bloginfo.blogCount.views += 1;
                    }
                }).catch(function (reason) {
                    console.log(reason);
                })
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
            var $this = this;
            axios.get("/blog/view/"+this.bloginfo.blog.bid).then(function (value) {
                if(value.data==true){
                    $this.bloginfo.blogCount.views += 1;
                }
            }).catch(function (reason) {
                console.log(reason);
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
            },
            like:function(){
                if (!this.liking) {
                    this.liking = true;
                    likeBlog(this.bloginfo);
                    this.liking = false;
                }
            },
            toUserPage:function () {
                routerPush("/user",{
                    uid: this.bloginfo.mLabUser.uid,
                    target:"sign",
                })
            }
        },
    };

    var moodsignItemComponent={
        props:["moodsign"],
        template:"#moodsign_item",
    }
    var moodsignContentComponent={
        data:function(){
            return{
                content:"",
                moodsigns:[],
                enteredEdit:false,
            }
        },
        props:["uid"],
        template:"#moodsign_content",
        components:{
            'moodsign-item':moodsignItemComponent,
        },
        mounted:function(){
            var $this = this;
            axios.get("/sign/list",{
                params:{
                    uid:this.uid,
                }
            }).then(function (value) {
                $this.moodsigns = value.data.list;
            }).catch(function (reason) {
                console.log(reason);
            })
        },
        watch:{
            $route:function () {
                var $this = this;
                axios.get("/sign/list",{
                    params:{
                        uid:this.uid,
                    }
                }).then(function (value) {
                    $this.moodsigns = value.data.list;
                }).catch(function (reason) {
                    console.log(reason);
                })
            }
        },
        methods:{
            signing:function (event) {
                if (this.content==null||this.content==""){
                    return;
                }
                if (getStrLength(this.content)>40) {
                    alert("写得太多啦");
                    this.content = getLegalStr(this.content, 40);
                }else {
                    var $this = this;
                    axios.get("/sign/add",{
                        params:{
                            content: this.content,
                            uid:this.uid,
                        }
                    }).then(function (value) {
                        if (value.data.msg = "true") {
                            $this.moodsigns.unshift(value.data.moodsign);
                            $this.content = "";
                            event.target.parentElement.parentElement.parentElement.parentElement
                                .__vue__.moodsign = value.data.moodsign;
                        }
                    }).catch(function (reason) {
                        console.log(reason);
                    })
                }
            }
        }
    }

    var myImagesComponent={
        props:["uid"],
        data:function(){
            return{
                myImages:[],
                bigImage:"",
                current:0,
            }
        },
        template:"#my_images",
        mounted:function () {
            var $this = this;
            axios.get("/myimg/list/"+this.uid).then(function (value) {
                $this.myImages = value.data;
            }).catch(function (reason) {
                console.log(reason);
            })
        },
        watch:{
            myImages:function () {
                this.$nextTick(function(){
                    /!*现在数据已经渲染完毕*!/
                    var $myImages = $(".my_images");
                    $myImages.imagesLoaded(function () {
                        $myImages.masonry({
                            itemSelector : '.image_item',
                            isFitWidth: true,//是否根据浏览器窗口大小自动适应默认false
                            isAnimated: true,//是否采用jquery动画进行重拍版
                            isRTL:false,//设置布局的排列方式，即：定位砖块时，是从左向右排列还是从右向左排列。默认值为false，即从左向右
                            isResizable: false,//是否自动布局默认true
                            animationOptions: {
                                duration: 800,
                                easing: 'easeInOutBack',//如果你引用了jQeasing这里就可以添加对应的动态动画效果，如果没引用删除这行，默认是匀速变化
                                queue: false//是否队列，从一点填充瀑布流
                            }
                        })
                    });
                })
            }
        },
        methods:{
            browseImage:function (url,index) {
                this.bigImage = url;
                this.cerrent = index;
            },
            nextImage:function () {
                if (this.cerrent+1<this.myImages.length) {
                    this.cerrent += 1;
                    this.bigImage = this.myImages[this.cerrent].url;
                }
            },
            prevImage:function () {
                if (this.cerrent>0) {
                    this.cerrent -= 1;
                    this.bigImage = this.myImages[this.cerrent].url;
                }
            },
            showTag:function (event) {
                $(event.target).closest(".image_item").find(".image_date")
                    .removeClass("animated fadeOut")
                    .addClass("animated fadeIn");
                $(event.target).closest(".image_item").find(".image_menu")
                    .removeClass("animated fadeOut")
                    .addClass("animated fadeIn");
            },
            hideTag:function (event) {
                $(event.target).next().removeClass("animated fadeIn")
                    .addClass("animated fadeOut");
                $(event.target).closest(".image_item").find(".image_menu")
                    .removeClass("animated fadeIn")
                    .addClass("animated fadeOut");
            }
        }
    }

    var commentComponent={
        template:"#comment_item",
        props:["commentitem"],
    }

    var starContentComponent={
        props:["uid"],
        data:function(){
            return{
                blogitems:[],
                starFolders:[],
            }
        },
        template:"#star_content",
        components:{
            'blog-item':blogComponent,
        },
        mounted:function () {
            var $this = this;
            axios.get("/blog/star/folder/get").then(function (value) {
                $this.starFolders = value.data;
            }).catch(function (reason) {
                console.log(reason);
            })
            axios.get("/blog/star/get",{
                params:{
                    uid:this.uid,
                    folder:0,
                }
            }).then(function (value) {
                $this.blogitems = value.data;
            }).catch(function (reason) {
                console.log(reason);
            })
        },
        methods:{
            isDefault:function (starFolder) {
                return starFolder.folder==0;
            },
            toFolder:function (folder) {
                $(".ml-bcg").removeClass("ml-bcg");
                $(event.target).parents("li").addClass("ml-bcg");
                var $this = this;
                axios.get("/blog/star/get",{
                    params:{
                        uid:this.uid,
                        folder:folder,
                    }
                }).then(function (value) {
                    $this.blogitems = value.data;
                }).catch(function (reason) {
                    console.log(reason);
                })
            },
        },
    }

    var likeContentComponent={
        props:["uid"],
        data:function(){
            return{
                page:"blog",
                blogitems:[],
            }
        },
        template:"#likes_content",
        components:{
            'blog-item':blogComponent,
        },
        mounted:function () {
            var $this = this;
            axios.get("/blog/like/get",{
                params:{
                    uid:this.uid,
                }
            }).then(function (value) {
                $this.blogitems = value.data;
            }).catch(function (reason) {
                console.log(reason);
            })
        },
    }

    var mlabuserInfoComponent={
        data:function(){
          return{
              enteredCover:false,
              uid:"",
              target:"",
              mLabUser:{},
              mLabUserCount:{},
              moodsign:{},
              bcgimg:"/image/defaulthead.jpg",
              blogpage:{},
              commentpage:{},
          }
        },
        template:"#mlabuser_info",
        mounted:function(){
            this.uid = routerGet(this).uid;
            this.target = routerGet(this).target;
            var $this = this;
            axios.get("/mlabuser/info/"+this.uid).then(function (value) {
                $this.moodsign = value.data.moodsign;
                $this.mLabUser = value.data.mLabUser;
                $this.mLabUserCount = value.data.mLabUserCount;
                $this.bcgimg = $this.mLabUser.blogbcg_img;
            }).catch(function (reason) {
                console.log(reason);
            })
            if(this.target=="blogs"){
                var params = "uid=" + this.uid;
                axios.post("/blog/get",params).then(function (value) {
                    $this.blogpage = value.data;
                }).catch(function (reason) {
                    console.log(reason);
                })
            }else if (this.target == "comments") {
                axios.get("/comment/list",{
                    params:{
                        uid:this.uid,
                    }
                }).then(function (value) {
                    $this.commentpage = value.data;
                }).catch(function (reason) {
                    console.log(reason);
                })
            }
            $(".my_info>.mybtn_active").removeClass("mybtn_active");
            $(".my_info>li").eq(this.activeIndex).addClass("mybtn_active");
        },
        watch:{
          $route:function () {
              this.uid = routerGet(this).uid;
              this.target = routerGet(this).target;
              var $this = this;
              axios.get("/mlabuser/info/"+this.uid).then(function (value) {
                  $this.moodsign = value.data.moodsign;
                  $this.mLabUser = value.data.mLabUser;
                  $this.mLabUserCount = value.data.mLabUserCount;
                  $this.bcgimg = $this.mLabUser.blogbcg_img;
              }).catch(function (reason) {
                  console.log(reason);
              })

              if(this.target=="blogs"){
                  var params = "uid=" + this.uid;
                  axios.post("/blog/get",params).then(function (value) {
                      $this.blogpage = value.data;
                  }).catch(function (reason) {
                      console.log(reason);
                  })
              }else if (this.target == "comments") {
                  axios.get("/comment/list",{
                      params:{
                          uid:this.uid,
                      }
                  }).then(function (value) {
                      $this.commentpage = value.data;
                  }).catch(function (reason) {
                      console.log(reason);
                  })
              }
              $(".my_info>.mybtn_active").removeClass("mybtn_active");
              $(".my_info>li").eq(this.activeIndex).addClass("mybtn_active");
          }
        },
        components:{
            'moodsign-content':moodsignContentComponent,
            'my-images':myImagesComponent,
            'blog-item':blogComponent,
            'comment-item':commentComponent,
            'star-content':starContentComponent,
            'like-content':likeContentComponent,
        },
        computed:{
            activeIndex:function () {
                var str={
                    "sign":0,
                    "images":1,
                    "star":2,
                    "likes":3,
                    "comments":4,
                    "blogs":5,
                }
                return str[this.target];
            }
        },
        methods:{
            toMySign:function(){
                routerPush("/user",{
                    uid: this.uid,
                    target:"sign",
                })
            },
            toMyImages:function(){
                routerPush("/user",{
                    uid: this.uid,
                    target:"images",
                })
            },
            toMyLikes:function(){
                routerPush("/user",{
                    uid: this.uid,
                    target:"likes",
                })
            },
            toMyStar:function(){
                routerPush("/user",{
                    uid: this.uid,
                    target:"star",
                })
            },
            toMyBlogs:function () {
                routerPush("/user",{
                    uid: this.uid,
                    target:"blogs",
                })
            },
            toMyComments:function () {
                routerPush("/user",{
                    uid: this.uid,
                    target:"comments",
                })
            }
        },
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
                routerPush("/info",{bloginfo: blogitem})
            },
            toUserPage:function () {
                routerPush("/user",{
                    uid: this.hotblog.mLabUser.uid,
                    target:"sign",
                })
            },
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
            axios.get("/count/mlabuser/get").then(function(value) {
                $this.mLabUserCount = value.data;
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
            moodsign:{},//TODO:签名
            editCache:false,
            plates:false,//板块列表开关
            allKey:"",
            userID:"",
            plateNo:0,//用于区分博客页当前的结果集属于哪个板块
            targetID:"",//当前评论框指向的id
            starFolders:[],
            showFolderInput:false,
            folderName:"",
            checkedFolder:[0],
            starBlogComp:{},
        },
        components:{
            'right-nav':rightNavComponent
        },
        router:routerObj,
        watch:{
            showFolderInput:function () {
                this.$nextTick(function () {
                    $("#starModal .input-group>.form-control").focus();
                })
            }
        },
        mounted:function () {
            this.plateNo = plateNo;
            axios.get("/mlabuser/isLogin").then(function (value) {
                app.userID = value.data.userID;
                app.moodsign = value.data.moodsign;
            }).catch(function (reason) {
                console.log(reason);
            })
        },
        methods:{
            toIndex:function () {
                search("");
            },
            toggleEdit:function () {
                if (this.$route.name != 'edit') {
                    routerObj.push({name:'edit'})
                }else{
                    routerObj.go(-1)
                }
            },
            toUserPage:function(){
                routerPush("/user",{
                    uid: app.userID,
                    target:"sign",
                })
            },
            toMyImages:function(){
                routerPush("/user",{
                    uid: app.userID,
                    target:"images",
                })
            },
            toMyLikes:function(){
                routerPush("/user",{
                    uid: app.userID,
                    target:"likes",
                })
            },
            toMyStar:function(){
                routerPush("/user",{
                    uid: app.userID,
                    target:"star",
                })
            },
            toMyComments:function(){
                routerPush("/user",{
                    uid: app.userID,
                    target:"comments",
                })
            },
            addFolder:function(){
                if (this.folderName==null||this.folderName==""){
                    return;
                }
                if (getStrLength(this.folderName)>20){
                    alert("名字太长啦");
                    this.folderName=getLegalStr(this.folderName, 20);
                }else {
                    this.showFolderInput = false;
                    var $this = this;
                    axios.get("/blog/star/folder/add",{
                        params:{
                            folderName:this.folderName,
                            folder:this.starFolders[this.starFolders.length-1].folder,
                        }
                    }).then(function (value) {
                        $this.starFolders.push(value.data);
                    }).catch(function (reason) {
                        console.log(reason);
                    })
                }

            },
            checkFolder:function(folder){
                var target;
                if (event.target.tagName=="A"){
                    target = event.target;
                }else {
                    target = event.target.parentElement;
                }
                var index= this.checkedFolder.indexOf(folder);
                if(index>=0){
                    this.checkedFolder.splice(index, 1);
                    $(target.firstElementChild).removeClass("fa-check-square")
                        .addClass("fa-square-o");
                }else {
                    this.checkedFolder.push(folder);
                    $(target.firstElementChild).addClass("fa-check-square")
                        .removeClass("fa-square-o");
                }
            },
            star:function(){
                if (this.starFolders == null || this.checkedFolder.length == 0) {
                    alert("至少选择一个收藏夹")
                    return;
                }else {
                    axios.post("/blog/star",{
                        bid:this.starBlogComp.blogitem.blog.bid,
                        folders:this.checkedFolder,
                    }).then(function (value) {
                        app.starBlogComp.blogitem.star = true;
                        $("#starModal").modal('hide');
                    }).catch(function (reason) {
                        console.log(reason);
                    })
                }
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
    $('#starModal').on('hidden.bs.modal', function (e) {
        app.showFolderInput = false;
    })
})