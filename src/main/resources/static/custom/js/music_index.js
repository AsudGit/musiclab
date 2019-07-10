$(function () {
    var $switch=$(".content-switch>i");
    var currentPage = "handle";
    var colorclass,bcgcolor,darkcolor;
    var audio;//音频
    //填色
    function coloring(colorclass,bcgcolor,darkcolor) {
        $(".bcgcolor").addClass(colorclass);
        $(".adorn_line").css("border-color",bcgcolor);
        $(".record_center_dotedge").css("background-color",darkcolor)
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
    //页面切换处理
    function togglePage(){
        if (currentPage=="handle"){
            var text = $(".current>h2").text();
            //填色
            if(text=="网易云音乐听见 · 好时光！") {
                colorclass = "redbcg";
                bcgcolor = "#da0000";
                darkcolor = "#ab0000";
                app.currentPage = "网易云音乐";
            }else if(text=="QQ音乐音乐你的生活"){
                colorclass = "greenbcg";
                bcgcolor = "#31c27c";
                darkcolor = "#22824c";
                app.currentPage = "QQ音乐";
            } else if(text=="酷狗音乐音乐总有新玩法"){
                colorclass = "bluebcg";
                bcgcolor = "#1e84e4";
                darkcolor = "#1f5fb5";
                app.currentPage = "酷狗音乐";
            } else if(text=="酷我音乐好音质用酷我"){
                colorclass = "yellowbcg";
                bcgcolor = "#f0bc0f";
                darkcolor = "#bf8e0f";
                app.currentPage = "酷我音乐";
            } else if(text=="MusicLab我们永远缺一个你的idea"){
                colorclass = "mlabbcg";
                bcgcolor = "#5cada1";
                darkcolor = "#377d67";
                app.currentPage = "MusicLab";
            }
            coloring(colorclass, bcgcolor, darkcolor);
            pageslideup();
            currentPage = "content";
        }else {
            pageslidedown();
            setTimeout(function () {
                $(".bcgcolor").removeClass("redbcg greenbcg bluebcg yellowbcg mlabbcg");
            }, 700);
            currentPage = "handle";
        }
    }
    function getPages(total,size){
        return total % size == 0 ? Math.floor(total / size) : Math.floor(total / size) + 1;
    }


    /*换页动画*/
    $(".content-switch").click(function () {
        togglePage();
    })
    $(document).keydown(function(event){
        if (event.keyCode == 40 && currentPage=="handle") togglePage();
        else if (event.keyCode==38 && currentPage=="content") togglePage();
    });
    /*换页动画end*/

    Vue.filter("timeFormat",function (value) {
        var m = parseInt(value / 60);
        var s = value % 60;
        m = m > 9 ? m : "0" + m;
        s = s > 9 ? s : "0" + s;
        return m + ":" + s;
    })

    var songComponent={
        props: ['song','index'],
        template: "#song_item",
        mounted:function () {
            $(".song_item a").css("color", bcgcolor);
        },
        methods:{
            playSong(){
                if (this.song.url == '' || this.song.url == null) {
                    axios.get("https://v1.itooi.cn/kuwo/song?id="+this.song.id+"&format=1")
                        .then(function (value) {
                            var data = value.data.data;
                            var nowsong = {
                                title: data.name,
                                artist: data.singer,
                                src: data.url,
                                pic: data.pic,
                                lrc: data.lrc,
                            };
                            app.aplayer.list.push(nowsong);
                            app.currentSong = nowsong;
                            app.aplayer.thenPlay()
                        }).catch(function (reason) {
                        console.log(reason);
                    })
                }else {
                    var nowsong = {
                        title: this.song.name,
                        artist: this.song.singer,
                        src: this.song.url,
                        pic: this.song.pic,
                        lrc: this.song.lrc,
                    };
                    app.aplayer.list.push(nowsong);
                    app.currentSong = nowsong;
                    app.aplayer.thenPlay()
                }
            },
            enteredSong:function (event) {
                $(event.target).css({
                    "background-color": bcgcolor, "color": "white"
                })
                $(event.target).children("a").css("color", "white");
            },
            leavedSong:function (event) {
                $(event.target).css({
                    "background-color": "white", "color": "black"
                })
                $(event.target).children("a").css("color", bcgcolor);
            },
            downloadSong:function () {
                var $this = this;
                var x=new XMLHttpRequest();
                if (this.song.url == '' || this.song.url == null) {
                    axios.get("https://v1.itooi.cn/kuwo/song?id="+this.song.id+"&format=1")
                        .then(function (value) {
                            $this.song = value.data.data;
                            x.open("GET", $this.song.url, true);
                            x.responseType = 'blob';
                            x.onload=function(e){download(x.response, $this.song.name+".mp3", "audio/mpeg" ); }
                            x.send();
                        }).catch(function (reason) {
                        console.log(reason);
                    })
                }else {
                    x.open("GET", $this.song.url, true);
                    x.responseType = 'blob';
                    x.onload = function (e) {download(x.response, $this.song.name + ".mp3", "audio/mpeg");}
                    x.send();
                }
            },
        },
    }

    var app=new Vue({
        el: "#app",
        data:{
            aplayer:{
                music:{},
            },//播放器对象
            currentPage:"",//当前所处页面
            theme:"",
            playerLoaded:0,//0未加载，1加载完成，2初始化完成
            currentSong:{},
            showSearch:false,
            listName:"",//歌单名
            songs:[],//查询到的歌曲总数
            songPage:[],//分页后的歌曲
            songKey:"",//歌曲关键字
            start:0,
            pages:0,
            size:10,
        },
        mounted:function(){
        },
        methods:{
            toggleSong:function () {
                var random;
                var $this = this;
                if (this.currentPage=="网易云音乐"){
                    random=Math.floor(Math.random()*1200)+1;
                    axios.get("https://v1.itooi.cn/netease/songList/hot?cat=全部&pageSize=1&page="+random)
                        .then(function (value) {
                            axios.get("https://v1.itooi.cn/netease/songList?id="+value.data.data[0].id+"&format=1")
                                .then(function (value) {
                                    $this.songs = value.data.data;
                                    app.currentSong={
                                        title: app.songs[0].name,
                                        artist: app.songs[0].singer,
                                        src: app.songs[0].url,
                                        pic: app.songs[0].pic,
                                        lrc: app.songs[0].lrc,
                                    }
                                    $this.pages = getPages($this.songs.length,$this.size);
                                    app.start = 0;
                                    $this.songPage = $this.songs.slice($this.start, $this.start + $this.size);
                                }).catch(function (reason) {
                                console.log(reason);
                            })
                        }).catch(function (reason) {
                        console.log(reason);
                    })
                }
                else if (this.currentPage=="QQ音乐"||this.currentPage=="酷狗音乐"||this.currentPage=="酷我音乐"){
                    random=Math.floor(Math.random()*5000)+1;
                    axios.get("https://v1.itooi.cn/tencent/songList/hot?categoryId=10000000&sortId=5&pageSize=1&page="+random)
                        .then(function (value) {
                            axios.get("https://v1.itooi.cn/tencent/songList?id="+value.data.data.list[0].dissid+"&pageSize=100&page=0&format=1")
                                .then(function (value) {
                                    app.songs = value.data.data;
                                    app.currentSong={
                                        title: app.songs[0].name,
                                        artist: app.songs[0].singer,
                                        src: app.songs[0].url,
                                        pic: app.songs[0].pic,
                                        lrc: app.songs[0].lrc,
                                    }
                                    app.pages = getPages(app.songs.length,app.size);
                                    app.start = 0;
                                    app.songPage = app.songs.slice(app.start, app.start + app.size);
                                }).catch(function (reason) {
                                console.log(reason);
                            })
                        }).catch(function (reason) {
                        console.log(reason);
                    })
                }
                var $myrecord_min=$("#myrecord_min");
                $myrecord_min.removeClass("animated rotateInDownRight")
                    .addClass("animated rotateOutDownRight");
                setTimeout(function () {
                    $myrecord_min.removeClass("animated rotateOutDownRight")
                        .addClass("animated rotateInDownRight");
                }, 150);
            },
            prePage:function () {
                if (this.start != 0) {
                    this.start -= 1;
                }
                this.songPage = this.songs.slice(this.start*this.size, this.start*this.size + this.size);
            },
            nextPage:function () {
                if (this.start != this.pages - 1) {
                    this.start += 1;
                }
                this.songPage = this.songs.slice(this.start*this.size, this.start*this.size+ this.size);
            },
            toStartPage:function(){
                this.start = 0;
                this.songPage = this.songs.slice(this.start*this.size, this.start*this.size + this.size);
            },
            toEndPage:function(){
                this.start = this.pages - 1;
                this.songPage = this.songs.slice(this.start*this.size, this.start*this.size + this.size);
            },
            searchSong:function () {
                var api;
                if (this.currentPage=="网易云音乐"){
                    api = "netease";
                }
                else if (this.currentPage=="QQ音乐"){
                    api = "tencent";
                }
                else if (this.currentPage=="酷狗音乐"){
                    api = "kugou";
                }
                else if (this.currentPage=="酷我音乐"){
                    api = "kuwo";
                }
                var key = "https://v1.itooi.cn/"+api+"/search?keyword=" + this.songKey + "&type=song&pageSize=100&page=0&format=1";
                var $this = this;
                axios.get(key).then(function (value) {
                    $this.songs = value.data.data;
                    $this.pages = getPages($this.songs.length,$this.size);
                    $this.start = 0;
                    $this.songPage = $this.songs.slice($this.start, $this.start + $this.size);
                }).catch(function (reason) {
                    console.log(reason);
                })
            },
            randomPlay:function () {
                if (this.songs.length>0){
                    var random=Math.floor(Math.random()*this.songs.length);
                    var song = {
                        title: this.songs[random].name,
                        artist: this.songs[random].singer,
                        src: this.songs[random].url,
                        pic: this.songs[random].pic,
                        lrc: this.songs[random].lrc,
                    };
                    app.aplayer.list.push(song);
                    app.currentSong = song;
                    console.log(app.aplayer.internalMusic);
                    app.aplayer.thenPlay();
                }
            }
        },
        components:{
            'song-item':songComponent,
            'aplayer': VueAPlayer,
        },
        watch:{
            currentPage:function () {
                var $this = this;
                this.theme = bcgcolor;
                if (this.currentPage=="网易云音乐"){
                  axios.get("https://v1.itooi.cn/netease/songList?id=3778678&format=1")
                      .then(function (value) {
                          $this.songs = value.data.data;
                          app.currentSong={
                              title: app.songs[0].name,
                              artist: app.songs[0].singer,
                              src: app.songs[0].url,
                              pic: app.songs[0].pic,
                              lrc: app.songs[0].lrc,
                          }
                          $this.pages = getPages($this.songs.length,$this.size);
                          $this.songPage = $this.songs.slice($this.start, $this.start + $this.size);
                      }).catch(function (reason) {
                      console.log(reason);
                  })
                }else if (this.currentPage == "QQ音乐"){
                  axios.get("https://v1.itooi.cn/tencent/topList?id=26&pageSize=100&page=0&format=1")
                      .then(function (value) {
                          $this.songs = value.data.data;
                          console.log($this.songs);
                          app.currentSong={
                              title: app.songs[0].name,
                              artist: app.songs[0].singer,
                              src: app.songs[0].url,
                              pic: app.songs[0].pic,
                              lrc: app.songs[0].lrc,
                          }
                          $this.pages = getPages($this.songs.length,$this.size);
                          $this.songPage = $this.songs.slice($this.start, $this.start + $this.size);
                          console.log(value);
                      }).catch(function (reason) {
                      console.log(reason);
                  })
                }else if (this.currentPage == "酷狗音乐") {
                  axios.get("https://v1.itooi.cn/kugou/search?keyword=站内一周热门单曲top&type=songList")
                      .then(function (value) {
                          //排序选出最新的热门歌单
                          var arr = value.data.data.info;
                          function compare(prop) {
                              return function(a,b){
                                  var value1 = a[prop];
                                  var value2 = b[prop];
                                  return new Date(value2) - new Date(value1);
                              }
                          }
                          arr.sort(compare('publishtime'));
                          axios.get("https://v1.itooi.cn/kugou/songList?id="+arr[0].specialid+"&pageSize=100&page=0&format=1")
                              .then(function (value) {
                                  $this.songs = value.data.data;
                                  app.currentSong={
                                      title: app.songs[0].name,
                                      artist: app.songs[0].singer,
                                      src: app.songs[0].url,
                                      pic: app.songs[0].pic,
                                      lrc: app.songs[0].lrc,
                                  }
                                  $this.pages = getPages($this.songs.length,$this.size);
                                  $this.songPage = $this.songs.slice($this.start, $this.start + $this.size);
                          }).catch(function (reason) {
                              console.log(reason);
                          })
                      }).catch(function (reason) {
                      console.log(reason);
                  })
                }else if (this.currentPage == "酷我音乐") {
                  axios.get("http://www.kuwo.cn/api/www/bang/bang/musicList?bangId=16&pn=1&rn=30&reqId=5f6b2d90-a2e9-11e9-91c2-5903ef1fdbd0")
                      .then(function (value) {
                          var data = value.data.data.musicList;
                          var formatSongs = new Array();
                          for (let i = 0; i < data.length; i++) {
                              formatSongs[i]={
                                  singer:data[i].artist,
                                  name:data[i].name,
                                  id:data[i].rid,
                                  time:data[i].duration,
                                  pic:data[i].pic,
                                  lic:'',
                                  url:'',
                              }
                          }
                          axios.get("https://v1.itooi.cn/kuwo/song?id="+formatSongs[0].id+"&format=1")
                              .then(function (value) {
                                  formatSongs[0] = value.data.data;
                                  $this.songs = formatSongs;
                                  app.currentSong={
                                      title: app.songs[0].name,
                                      artist: app.songs[0].singer,
                                      src: app.songs[0].url,
                                      pic: app.songs[0].pic,
                                      lrc: app.songs[0].lrc,
                                  }
                                  $this.pages = getPages($this.songs.length,$this.size);
                                  $this.songPage = $this.songs.slice($this.start, $this.start + $this.size);
                              }).catch(function (reason) {
                              console.log(reason);
                          })
                      }).catch(function (reason) {
                      console.log(reason);
                  })
                }
            },
            'aplayer.internalMusic':function(){
                $("#myrecord_min>.record_lines>p").text(this.aplayer.internalMusic.title);
                $("#myrecord_min>.record_center_cover").css("background-image","url("+this.aplayer.internalMusic.pic+")");
            },
            songPage:function () {
                this.$nextTick(function () {
                    $("#myrecord>.record_center_cover").css("background-image","url("+this.songPage[0].pic+")");
                    $("#myrecord>.record_center_cover>p").text(this.songPage[0].name);
                    if (this.playerLoaded==0){
                        this.playerLoaded = 1;
                    }
                })
            },
            playerLoaded:function () {
                if (this.playerLoaded == 1) {
                    audio = document.getElementsByTagName("audio")[0];
                    //播放事件
                    audio.addEventListener("playing", function () {
                            $("#myrecord_min>.record_center_cover").addClass("rotating");
                        }
                    );
                    audio.addEventListener("pause",function () {
                        $("#myrecord_min>.record_center_cover").removeClass("rotating");
                    })
                    app.aplayer = app.$root.$el.firstElementChild.lastElementChild.firstElementChild.__vue__;
                    this.playerLoaded = 2;
                }
            }
        },
    })
})
