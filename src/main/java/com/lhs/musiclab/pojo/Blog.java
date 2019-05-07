package com.lhs.musiclab.pojo;

import java.io.Serializable;
import java.sql.Timestamp;

public class Blog implements Serializable {
    private String bid;
    private String title;
    private String content;
    private Timestamp blogged_time;
    private Integer views;
    private Integer likes;
    private Integer comments;
    //1:正常,2:置顶
    private Integer status;
    //板块号
    private Integer plate;
    //博主id
    private String uid;

    @Override
    public String toString() {
        return "Blog{" +
                "bid='" + bid + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", blogged_time=" + blogged_time +
                ", views=" + views +
                ", likes=" + likes +
                ", comments=" + comments +
                ", status=" + status +
                ", plate=" + plate +
                ", uid='" + uid + '\'' +
                '}';
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getBlogged_time() {
        return blogged_time;
    }

    public void setBlogged_time(Timestamp blogged_time) {
        this.blogged_time = blogged_time;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPlate() {
        return plate;
    }

    public void setPlate(Integer plate) {
        this.plate = plate;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
