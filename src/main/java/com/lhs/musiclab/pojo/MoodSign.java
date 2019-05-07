package com.lhs.musiclab.pojo;

import java.io.Serializable;
import java.sql.Timestamp;

public class MoodSign implements Serializable {
    private String msid;
    private String content;
    private Timestamp signed_time;
    private Integer likes;
    //用户id
    private String uid;

    @Override
    public String toString() {
        return "MoodSign{" +
                "msid='" + msid + '\'' +
                ", content='" + content + '\'' +
                ", signed_time=" + signed_time +
                ", likes=" + likes +
                ", uid='" + uid + '\'' +
                '}';
    }

    public String getMsid() {
        return msid;
    }

    public void setMsid(String msid) {
        this.msid = msid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getSigned_time() {
        return signed_time;
    }

    public void setSigned_time(Timestamp signed_time) {
        this.signed_time = signed_time;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
