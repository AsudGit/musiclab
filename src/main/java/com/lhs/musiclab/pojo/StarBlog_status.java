package com.lhs.musiclab.pojo;

import java.io.Serializable;

public class StarBlog_status implements Serializable {
    private static final long serialVersionUID = -6915761885053958942L;
    private StarBlog starBlog;
    private Integer status;

    @Override
    public String toString() {
        return "StarBlog_status{" +
                "starBlog=" + starBlog +
                ", status=" + status +
                '}';
    }

    public StarBlog getStarBlog() {
        return starBlog;
    }

    public void setStarBlog(StarBlog starBlog) {
        this.starBlog = starBlog;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
