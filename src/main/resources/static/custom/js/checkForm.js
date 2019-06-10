/**
 * Created by 我的样子平平无奇 on 2018/6/25.
 */
function slidcode() {
    /*验证码初始化设置*/
    $('#slidecode').slideVerify({
        //滑动验证码type=1，拼图验证码type=2
        type: 2,

        //拼图验证码或选择验证码图片名称
        imgName: ['1.jpg', '2.jpg'],

        //拼图验证码的图片尺寸
        imgSize: {
            width: '270px',
            height: '135px',
        }, barSize: {
            width: '270px',
            height: '30px',
        },

        //......更多参数设置请查阅文档

        //验证成功以后的回调
        success: function () {
            $(".slidecode").val("true");
            //触发一下该input的input事件
        }
    });
}
//获取长度
function getStrLength( str ){
    return str.replace(/[\u0391-\uFFE5]/g,"aa").length; //"g" 表示全局匹配
}
$(function () {
    /*注册表单验证begin*/
    var reg = {
        "name": /^[a-zA-Z\u4e00-\u9fa5]+[_a-zA-Z0-9\u4e00-\u9fa5]*$/,
        //用来判断用户名，第一位不能为数字，4-14个由英文或数字下划线组成的字符
        //要求是4-20位字符，所以出去第一位，还需要3-19位的\w
        "pwd": /^[\da-zA-Z]{8,20}$/,
        //用来判断密码，html结构中要求是数字字符8到20位，\d表示数字
        "email": /^[1-9a-zA-Z_]\w*@[a-zA-Z0-9]+(\.[a-zA-Z]{2,})+$/,
        //用来判断邮箱，通常邮箱没有以0开头的，所以第一位为1-9数字或者小写字母或者大写字母，第二位开始任意字符
        //也可以只有第一位没有第二位，*表示至少0个，@后面同理，小写字母或者大写字母或者数字，.需要转意符，所以写成\.
        //点后面通常是com或者cn或者com.cn，所以是小写字母或者大写字母至少两位
        "phone": /^1[34578]\d{9}$/
        //用来判断电话号码，通常手机号第一位为1，第二位只可能出现3.4.5.7.8，后面剩下的9位数字随机
    };
    var prompt = {
        "name": "由英文或中文字符开头，14位字符组成<br>包含下划线,汉字最多为7个",
        "pwd": "由8到20位数字字符组成，不包含下划线",
        "email": "以登录名@主机名.域名的格式",
        "phone": "以1开头，第二位为3,4,5,7,8的11位数字"
    }
    var arr = [
        $("#name"),
        $("#r_pwd"),
        $("#email"),
        $("#phone")
    ];
    $("#birthday").keydown(function (e) {
        e.preventDefault();
    })
    var v=new Vue({
        el:"#myNav",
        data:{
            userName:"",
            headImg:"",
            account:null,
            l_pwd:null,
            name:"",
            pwd:"",
            email:"",
            birthday:"",
            phone:"",
            phone_code:"",
            checkpwd:"",
            slidecode:"false"
        },
        mounted:function(){
            var self=this;
            axios.get("/mlabuser/isLogin").then(function (value) {
                self.userName = value.data.userName;
                self.headImg = value.data.headImg;
            }).catch(function (reason) {
                console.log(reason);
            })
        },
        methods: {
                register: function (event) {
                    var $event = $("#" + event.target.id);
                    var checkform = null;
                    $("#register_form div input").each(function () {
                        if ("" == $event.val()) {
                            checkform = "表单有空项";
                        }
                    })

                    if ($("#r_pwd").val() != $("#checkpwd").val()) {
                        checkform = "密码不一致";
                    }

                    if (checkform != "表单有空项" && checkform != "密码不一致") {
                        for (var i = 0; i < arr.length; i++) {
                            if (reg[arr[i].prop("name")].test(arr[i].val())) {
                                checkform = arr[i].prev().text() + "格式不正确";
                            }
                        }
                        ;
                    }
                    if (checkform == null) {
                        var self = this;
                        axios.get("/sendsms/code/" + this.phone + "/" + this.phone_code)
                            .then(function (value) {
                                if (value.data.msg == "false") {
                                    checkform = "验证码错误";
                                }
                                if (checkform == null) {
                                    axios.post("/mlabuser/add", $("#register_form").serialize())
                                        .then(function (value) {
                                            if(value.data.msg=="true") {
                                                $("#regitser_modal").modal('hide');
                                                self.userName = value.data.userName;
                                                self.headImg = value.data.headImg;
                                            }else {
                                                alert("注册失败")
                                            }
                                        }).catch(function (reason) {
                                        console.log(reason)
                                    })
                                } else {
                                    alert(checkform);
                                    checkform = null;
                                }
                            }).catch(function (reason) {
                            console.log(reason);
                        })
                    }
                },
                login: function () {
                    var checkform = null;
                    if (this.account == "") {
                        checkform = "请填写你的账号";
                    }
                    if (this.l_pwd == "") {
                        checkform = "请填写你的密码";
                    }
                    if ($(".slidecode").val() != "true") {
                        checkform = "请滑动完成验证";
                    }
                    if (checkform != null) {
                        alert(checkform);
                        slidereinit();
                    } else {
                        var self = this;
                        $("#login_modal").modal('hide');
                        axios.post("/mlabuser/login",$("#login_form").serialize())
                            .then(function (value) {
                                slidereinit();
                                console.log(value)
                                if (value.data.msg=="true") {
                                    self.userName = value.data.userName;
                                    self.headImg = value.data.headImg;
                                }else {
                                    alert("账号或密码错误");
                                }
                            }).catch(function (reason) {
                            console.log(reason);
                        })
                    }
                },
                sendCode: function (event) {
                    var self = this;
                    var $event = $("#" + event.target.id);
                    if (this.phone=="") {
                        alert("请先填写手机号");
                    } else {
                        $event.text("已发送").prop("disabled", "disabled");
                        axios.get("/sendsms/phone/" + this.phone)
                            .then(function (value) {
                                $("#phone").after("<h5 style='color:dodgerblue;'>验证码有效时间为10分钟，请勿重复发送</h5>")
                            }).catch(function (reason) {
                            console.log(reason);
                        })
                    }
                },
                checkRepeat: function (event) {
                    var $event = $("#" + event.target.id);
                    if ($event.val() != "") {
                        var flag = true;
                        if (!reg[$event.prop("name")].test($event.val())) {/*格式验证*/
                            $event.after("<h5 style='color: red'>" + prompt[$event.prop("name")] + "</h5>");
                            flag = false;
                        } else if ($event.prop("name") == "name" && getStrLength($event.val()) > 14) {
                            $event.after("<h5 style='color: red'>" + prompt[$event.prop("name")] + "</h5>");
                            flag = false;
                        }
                        if (flag == true && $event.prop("name") != "pwd") {//账号冲突判断
                            axios.get("/mlabuser/" + $event.prop("name") + "/" + $event.val())
                                .then(function (value) {
                                    if (value.data.msg == "true") {
                                        $("#" + $event.prop("name")).after("<h5 style='color:red;'>该" + $("#" + $event.prop("name")).prev().text() + "已存在</h5>");
                                    }
                                }).catch(function (reason) {
                                console.log(reason)
                            })
                        }
                    }
                    ;
                },
                checkPassword: function (event) {
                    var $event = $("#" + event.target.id);
                    if (this.checkpwd != "") {
                        if (this.pwd != this.checkpwd) {
                            $event.after("<h5 style='color: red'>密码不一致</h5>");
                            checkform = false;
                        }
                    }
                },
                logout:function (event) {
                    var self = this;
                    axios.get("/mlabuser/logout").then(function () {
                        self.userName = "";
                        self.headImg = "";
                    }).catch(function (reason) {
                        console.log(reason);
                    })
                },
                scrollbtn:function () {
                    $("html,body").animate({scrollTop: $(".page2").css("top")}, 500);
                },
            }
        })
    /*注册表单验证end*/
    /*表单非空验证begin*/
    $("form div input").blur(function () {
        if ("" == $(this).val()) {
            $(this).after("<h5 style='color: red;'>" + $(this).prev().text() + "不能为空</h5>");
        }
    })
    $("form div input").focus(function () {
        if ($(this).next()[0].tagName == "H5") {
            $(this).nextAll().each(function () {
                $(this).remove();
            })
        }
    })
    /*表单非空验证end*/
    function slidereinit() {
        $(".verify-img-out").remove();
        $(".verify-bar-area").remove();
        slidcode();
        /*验证码滑动图淡入淡出*/
        $(".verify-bar-area").mouseenter(function () {
            setTimeout(function () {
                $(".verify-img-out").show();
                $(".verify-sub-block").show();
            }, 100);
        }).mouseleave(function () {
            setTimeout(function () {
                $(".verify-img-out").hide();
                $(".verify-sub-block").hide();
            }, 500);
        })
    }

    slidereinit();
})
