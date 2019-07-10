package com.lhs.musiclab.pojo;

import java.io.Serializable;

public class LikeBlogs_status implements Serializable {
    private static final long serialVersionUID = 2976252797988323127L;
    private LikeBlogs likeBlogs;
    private Integer status;

    @Override
    public String toString() {
        return "LikeBlogs_status{" +
                "likeBlogs=" + likeBlogs +
                ", status=" + status +
                '}';
    }

    public LikeBlogs getLikeBlogs() {
        return likeBlogs;
    }

    public void setLikeBlogs(LikeBlogs likeBlogs) {
        this.likeBlogs = likeBlogs;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
