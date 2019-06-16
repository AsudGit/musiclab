package com.lhs.musiclab.pojo;

import java.io.Serializable;

public class LikeSongs implements Serializable {
    private static final long serialVersionUID = -2706101593969329208L;
    private String lid;
    //用户id
    private String uid;
    //歌曲id
    private String sid;

    @Override
    public String toString() {
        return "LikeSongs{" +
                "lid='" + lid + '\'' +
                ", uid='" + uid + '\'' +
                ", sid='" + sid + '\'' +
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

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }
}
