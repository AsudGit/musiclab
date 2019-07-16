# MusicLab音乐论坛

## 描述

该项目主要目标是完成BBS系统和音乐播放的开发。
该系统分为用户模块、博客模块、评论模块、热门模块、收藏模块、相册模块、音乐模块、7个模块。

## DEMO

[访问云服务器上的实例](https://www.musiclab.club "musiclab音乐论坛")

## 采用的技术

1. 采用JQuery,bootstrap3,VUE,ajax等技术用于实现前端页面的样式、数据渲染和路由功能。
2. 后台使用springboot+mybatis做成restful风格的响应JSON数据的API，实现了前后端分离。
3. 使用redis中间件进行缓存，并根据数据的更新频率将数据分为热数据和冷数据采用不同的缓存策略，rabbitMQ作为消息队列实现用户的信息订阅.
4. 使用阿里提供的短信接口进行发送服务，使用网上的音乐接口，实现音乐的查询、播放、下载等功能。

## 页面展示

### 首页

![Image text](https://raw.githubusercontent.com/AsudGit/img-folder/master/musiclab/index1.png)

![Image text](https://raw.githubusercontent.com/AsudGit/img-folder/master/musiclab/index2.png)

### 博客页

![Image text](https://raw.githubusercontent.com/AsudGit/img-folder/master/musiclab/blog1.png)

### 博客详情页

![Image text](https://raw.githubusercontent.com/AsudGit/img-folder/master/musiclab/blog5.png)

### 评论区

![Image text](https://raw.githubusercontent.com/AsudGit/img-folder/master/musiclab/comment1.png)

### 用户详情页

![Image text](https://raw.githubusercontent.com/AsudGit/img-folder/master/musiclab/blog2.png)

### 相册

![Image text](https://raw.githubusercontent.com/AsudGit/img-folder/master/musiclab/blog3.png)

### 富文本编辑页

![Image text](https://raw.githubusercontent.com/AsudGit/img-folder/master/musiclab/blog4.png)

### 音乐播放页

![Image text](https://raw.githubusercontent.com/AsudGit/img-folder/master/musiclab/music1.png)

![Image text](https://raw.githubusercontent.com/AsudGit/img-folder/master/musiclab/music2.png)

### 切换音乐接口

![Image text](https://raw.githubusercontent.com/AsudGit/img-folder/master/musiclab/music3.png)
