$(function () {
    //初始化表情控件
    $("#editor_area>.w-e-text").emoji({
        showTab: false,
        animation: 'slide',
        icons: [
            {
                name: "贴吧表情",
                path: "/image/tieba/",
                maxNum: 50,
                file: ".jpg",
                placeholder: "#tieba_{alias}#"
            },{
                name: "QQ表情",
                path: "/image/qq/",
                maxNum: 91,
                file: ".gif",
                placeholder: "#qq_{alias}#"
            }]
    });

    $("#editor_title").blur(function () {
        if($(this).val().length>20){
            $(this).val($(this).val().substr(0, 20));
        }
    })
})