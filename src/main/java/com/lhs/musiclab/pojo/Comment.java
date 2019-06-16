package com.lhs.musiclab.pojo;

import java.io.Serializable;
import java.sql.Timestamp;

public class Comment implements Serializable {
    private static final long serialVersionUID = 8540396035406625189L;
    private String cid;
    private String content;
    //    发评论的时间
    private Timestamp commented_time;
    private Integer likes;
    //    被评论的博客或者评论的id
    private String id;
    //博主id
    private String uid;

    @Override
    public String toString() {
        return "Comment{" +
                "cid='" + cid + '\'' +
                ", content='" + content + '\'' +
                ", commented_time=" + commented_time +
                ", likes=" + likes +
                ", id='" + id + '\'' +
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
