package com.lhs.musiclab.pojo;

import java.io.Serializable;

public class LikeMoodSigns implements Serializable {
    private static final long serialVersionUID = -1284549476655034887L;
    private String lid;
    //用户id
    private String uid;
    //心情标签id
    private String msid;

    @Override
    public String toString() {
        return "LikeMoodSigns{" +
                "lid='" + lid + '\'' +
                ", uid='" + uid + '\'' +
                ", msid='" + msid + '\'' +
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

    public String getMsid() {
        return msid;
    }

    public void setMsid(String msid) {
        this.msid = msid;
    }
}
