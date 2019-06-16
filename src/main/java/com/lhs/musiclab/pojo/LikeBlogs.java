package com.lhs.musiclab.pojo;

import java.io.Serializable;

public class LikeBlogs implements Serializable {
    private static final long serialVersionUID = 571904611242039016L;
    private String lid;
    //用户id
    private String uid;
    //博客id
    private String bid;

    @Override
    public String toString() {
        return "LikeBlogs{" +
                "lid='" + lid + '\'' +
                ", uid='" + uid + '\'' +
                ", bid='" + bid + '\'' +
                '}';
    }

    public String getLid() {
        return lid;
    }

    public void setLid(String lid) {
        this.lid = lid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }
}
