package com.lhs.musiclab.pojo;

import java.io.Serializable;

public class Tags implements Serializable {
    private String tid;
    private String tag;

    @Override
    public String toString() {
        return "Tags{" +
                "tid='" + tid + '\'' +
                ", tag='" + tag + '\'' +
                '}';
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
