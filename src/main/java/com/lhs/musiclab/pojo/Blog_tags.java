package com.lhs.musiclab.pojo;

import java.io.Serializable;

public class Blog_tags implements Serializable {
    //多对多关系
    private String id;
    //博客的id
    private String bid;
    //标签的id
    private String tid;

    @Override
    public String toString() {
        return "Blog_tags{" +
                "id='" + id + '\'' +
                ", bid='" + bid + '\'' +
                ", tid='" + tid + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }
}
