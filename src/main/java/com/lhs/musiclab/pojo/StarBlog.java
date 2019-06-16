package com.lhs.musiclab.pojo;

import java.io.Serializable;

public class StarBlog implements Serializable {
    private static final long serialVersionUID = 8798573207247478032L;
    private String sbid;
    //用户id
    private String uid;
    //博客id
    private String bid;
    //文件夹号
    private Integer folder;

    @Override
    public String toString() {
        return "StarBlog{" +
                "sbid='" + sbid + '\'' +
                ", uid='" + uid + '\'' +
                ", bid='" + bid + '\'' +
                ", folder=" + folder +
                '}';
    }

    public String getSbid() {
        return sbid;
    }

    public void setSbid(String sbid) {
        this.sbid = sbid;
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

    public Integer getFolder() {
        return folder;
    }

    public void setFolder(Integer folder) {
        this.folder = folder;
    }
}
