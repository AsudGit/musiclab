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