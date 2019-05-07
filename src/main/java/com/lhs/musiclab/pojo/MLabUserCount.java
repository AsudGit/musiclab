package com.lhs.musiclab.pojo;

import java.io.Serializable;
import java.util.Date;

public class MLabUserCount implements Serializable {
    private String uid;
    private Integer fans;
    private Integer blogs;
    private Integer comments;
    private Date registeredDate;
    private Date recentlyLogin;

    @Override
    public String toString() {
        return "MLabUserCount{" +
                "uid='" + uid + '\'' +
                ", fans=" + fans +
                ", blogs=" + blogs +
                ", comments=" + comments +
                ", registeredDate=" + registeredDate +
                ", recentlyLogin=" + recentlyLogin +
                '}';
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Integer getFans() {
        return fans;
    }

    public void setFans(Integer fans) {
        this.fans = fans;
    }

    public Integer getBlogs() {
        return blogs;
    }

    public void setBlogs(Integer blogs) {
        this.blogs = blogs;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public Date getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(Date registeredDate) {
        this.registeredDate = registeredDate;
    }

    public Date getRecentlyLogin() {
        return recentlyLogin;
    }

    public void setRecentlyLogin(Date recentlyLogin) {
        this.recentlyLogin = recentlyLogin;
    }
}
