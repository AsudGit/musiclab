package com.lhs.musiclab.pojo;

import java.io.Serializable;

public class FansList implements Serializable {
    private String fid;
    //关注者的id
    private String follower;
    //被关注者的id
    private String blogger;

    @Override
    public String toString() {
        return "FansList{" +
                "fid='" + fid + '\'' +
                ", follower='" + follower + '\'' +
                ", blogger='" + blogger + '\'' +
                '}';
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getFollower() {
        return follower;
    }

    public void setFollower(String follower) {
        this.follower = follower;
    }

    public String getBlogger() {
        return blogger;
    }

    public void setBlogger(String blogger) {
        this.blogger = blogger;
    }
}
