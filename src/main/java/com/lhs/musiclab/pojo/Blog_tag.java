package com.lhs.musiclab.pojo;

import java.io.Serializable;

public class Blog_tag implements Serializable {
    private static final long serialVersionUID = 1556233170832088577L;
    //多对多关系
    private String id;
    private String bid;
    private String tid;

    @Override
    public String toString() {
        return "Blog_tag{" +
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
