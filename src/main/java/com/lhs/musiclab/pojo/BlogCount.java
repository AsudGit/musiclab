package com.lhs.musiclab.pojo;

import java.io.Serializable;
import java.sql.Timestamp;

public class BlogCount implements Serializable {
    private static final long serialVersionUID = -5637849527393212115L;
    private String bid;
    private Timestamp revised_time;
    private Integer views;
    private Integer likes;
    private Integer comments;
    //1:正常,2:置顶
    private Integer status;

    @Override
    public String toString() {
        return "BlogCount{" +
                "bid='" + bid + '\'' +
                ", revised_time=" + revised_time +
                ", views=" + views +
                ", likes=" + likes +
                ", comments=" + comments +
                ", status=" + status +
                '}';
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public Timestamp getRevised_time() {
        return revised_time;
    }

    public void setRevised_time(Timestamp revised_time) {
        this.revised_time = revised_time;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
