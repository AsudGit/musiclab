package com.lhs.musiclab.pojo;

import java.io.Serializable;

public class LikeComments implements Serializable {
    private static final long serialVersionUID = -6701081380341818720L;
    private String lid;
    //用户id
    private String uid;
    //评论id
    private String cid;

    @Override
    public String toString() {
        return "LikeComments{" +
                "lid='" + lid + '\'' +
                ", uid='" + uid + '\'' +
                ", cid='" + cid + '\'' +
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

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }
}
