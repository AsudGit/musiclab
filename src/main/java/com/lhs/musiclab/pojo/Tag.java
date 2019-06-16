package com.lhs.musiclab.pojo;

import java.io.Serializable;

public class Tag implements Serializable {
    private static final long serialVersionUID = 2264758158894164744L;
    private String tid;
    private String name;

    public Tag() {
    }

    @Override
    public String toString() {
        return "Tag{" +
                "tid='" + tid + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
