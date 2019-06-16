package com.lhs.musiclab.pojo;

import java.io.Serializable;

public class Song implements Serializable {
    private static final long serialVersionUID = 6834119667753172841L;
    //    从接口获取的id
    private String sid;
    //    歌名
    private String title;
    private String author;
    //    专辑封面
    private String song_pic;
    //    歌曲播放路径
    private String song_url;
    //    歌词
    private String song_lrc;
    //    歌曲时长(单位秒)
    private Integer time;
    private Integer likes;
    private Integer downloads;

    @Override
    public String toString() {
        return "Song{" +
                "sid='" + sid + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", song_pic='" + song_pic + '\'' +
                ", song_url='" + song_url + '\'' +
                ", song_lrc='" + song_lrc + '\'' +
                ", time=" + time +
                ", likes=" + likes +
                ", downloads=" + downloads +
                '}';
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSong_pic() {
        return song_pic;
    }

    public void setSong_pic(String song_pic) {
        this.song_pic = song_pic;
    }

    public String getSong_url() {
        return song_url;
    }

    public void setSong_url(String song_url) {
        this.song_url = song_url;
    }

    public String getSong_lrc() {
        return song_lrc;
    }

    public void setSong_lrc(String song_lrc) {
        this.song_lrc = song_lrc;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getDownloads() {
        return downloads;
    }

    public void setDownloads(Integer downloads) {
        this.downloads = downloads;
    }
}
