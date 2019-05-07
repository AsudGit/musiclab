package com.lhs.musiclab.pojo;

import java.io.Serializable;
import java.sql.Timestamp;

public class Comment implements Serializable {
    private String cid;
    private String content;
    //    发评论的时间
    private Timestamp commented_time;
    private Integer likes;
    //    被评论的博客的bid
    private String bid;
    //博主id
    private String uid;

    @Override
    public String toString() {
        return "Comment{" +
                "cid='" + cid + '\'' +
                ", content='" + content + '\'' +
                ", commented_time=" + commented_time +
                ", likes=" + likes +
                ", bid='" + bid + '\'' +
                ", uid='" + uid + '\'' +
                '}';
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCommented_time() {
        return commented_time;
    }

    public void setCommented_time(Timestamp commented_time) {
        this.commented_time = commented_time;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
