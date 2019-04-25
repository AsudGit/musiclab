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
        }
    });
}

$(function () {
    slidcode();
    /*注册表单验证begin*/
    var reg = {
        "userInfo.user_name": /^([\u4e00-\u9fa5][\u4e00-\u9fa5]{0,9}|[\u4e00-\u9fa5][_a-zA-Z0-9]{0,18}|[a-zA-Z][\u4e00-\u9fa5]{0,9}|[a-zA-Z][_a-zA-Z0-9]{0,19}){1}$/,
        //用来判断用户名，第一位不能为数字，也就是小写字母或者大写字母，后面的内容\w表示字符（数字字母下划线）
        //要求是4-20位字符，所以出去第一位，还需要3-19位的\w
        "userInfo.password": /^[\da-zA-Z]{8,20}$/,
        //用来判断密码，html结构中要求是数字字符8到20位，\d表示数字
        "userInfo.email": /^[1-9a-zA-Z_]\w*@[a-zA-Z0-9]+(\.[a-zA-Z]{2,})+$/,
        //用来判断邮箱，通常邮箱没有以0开头的，所以第一位为1-9数字或者小写字母或者大写字母，第二位开始任意字符
        //也可以只有第一位没有第二位，*表示至少0个，@后面同理，小写字母或者大写字母或者数字，.需要转意符，所以写成\.
        //点后面通常是com或者cn或者com.cn，所以是小写字母或者大写字母至少两位
        "userInfo.phone_num": /^1[34578]\d{9}$/
        //用来判断电话号码，通常手机号第一位为1，第二位只可能出现3.4.5.7.8，后面剩下的9位数字随机
    };
    var prompt = {
        "userInfo.user_name": "由英文或中文字符开头，20位字符组成<br>包含下划线",
        "userInfo.password": "由8到20位数字字符组成，不包含下划线",
        "userInfo.email": "以登录名@主机名.域名的格式",
        "userInfo.phone_num": "以1开头，第二位为3,4,5,7,8的11位数字"
    }
    var arr = [
        $("#user_name"),
        $("#r_pwd"),
        $("#email"),
        $("#phone_num")
    ];
    for (var i = 0; i < arr.length; i++) {
        arr[i].blur(function () {
            if ($(this).val() != "") {
                if (!reg[$(this).prop("name")].test($(this).val())) {/*格式验证*/
                    $(this).after("<h5 style='color: red'>" + prompt[$(this).prop("name")] + "</h5>");
                } else if (reg[$(this).prop("name")] != "password") {
                    $.ajax({
                        type: "post",

                        url: "checkParam",

                        data: {
                            val: $(this).val(),
                            name: $(this).prop("name")
                        },

                        dataType: 'json',
                        success: function (data) {
                            if (data.val == "exist") {
                                $(data.id).after("<h5 style='color:red;'>该" + $(data.id).prev().text() + "已存在</h5>");
                            }
                        },
                        error: function (e) {
                            alert("error");
                        }
                    });
                }
            }
            ;
        });
    }
    ;
    $("#checkpwd").blur(function () {
        if ($("#checkpwd").val() != "") {
            if ($("#r_pwd").val() != $(this).val()) {
                $(this).after("<h5 style='color: red'>密码不一致</h5>");
                checkform = false;
            }
        }
    })
    $("#birthday").keydown(function (e) {
        e.preventDefault();
    })
    $("#post_pcode").click(function () {
        if ($("#phone_num").val() == "") {
            alert("请先填写手机号");
        } else {
            $(this).text("已发送").prop("disabled", "disabled");
            $.ajax({
                type: "post",

                url: "songPhoneCode",

                data: {phone_num: $("#phone_num").val()},

                dataType: 'json',

                success: function (data) {
                    alert(data.message);
                },
                error: function (e) {
                    alert("error");
                }
            })
            setTimeout(function () {
                $("#post_pcode").text("发送验证码").removeAttr("disabled");
            }, 50000)
        }
    })

    $("#regitser").click(function () {
        var checkform = null;
        $("#register_form div input").each(function () {
            if ("" == $(this).val()) {
                checkform = "表单有空项";
            }
        })

        if ($("#r_pwd").val() != $("#checkpwd").val()) {
            checkform = "密码不一致";
        }

        if (checkform != "表单有空项" && checkform != "密码不一致") {
            for (var i = 0; i < arr.length; i++) {
                if (!reg[arr[i].prop("name")].test(arr[i].val())) {
                    checkform = arr[i].prev().text() + "格式不正确";
                }
            }
            ;
        }
        if (checkform == null) {
            $.ajax({
                type: "post",

                url: "checkForm",

                data: {
                    user_name: arr[0].val(),
                    email: arr[2].val(),
                    phone_num: arr[3].val(),
                    phone_code: $("#phone_code").val()

                },

                dataType: 'json',
                success: function (data) {
                    if (data.val == "exist") {
                        alert($(data.id + "").prev().text() + "已经存在");
                    } else if (data.message == "验证码不正确") {
                        alert(data.message);
                    } else {
                        $("form:eq(0)").submit();
                    }
                },
                error: function (e) {
                    alert("error");
                }
            });
        } else {
            alert(checkform);
            checkform = null;
        }
    })
    $("#login").click(function () {
        var checkform = null;
        if ($("#account").val() == "") {
            checkform = "请填写你的账号";
        }
        if ($("#l_pwd").val() == "") {
            checkform = "请填写你的密码";
        }
        if ($(".slidecode").val() != "true") {
            checkform = "请滑动完成验证";
        }
        if (checkform != null) {
            alert(checkform);
            $(".verify-img-out").remove();
            $(".verify-bar-area").remove();
            slidcode();
        } else {
            $.ajax({
                type: "post",

                url: "loginUser",

                data: {
                    "account": $("#account").val(),
                    "password": $("#l_pwd").val()
                },

                dataType: 'json',
                success: function (data) {
                    if (data.message == "密码或账号错误") {
                        alert(data.message);
                    } else {
                        window.location.reload();
                    }
                },
                error: function (e) {
                    alert("error");
                }
            })
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
    /*表单非空验证end/
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
})
