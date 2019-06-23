package com.lhs.musiclab.pojo;

import java.io.Serializable;

public class Tag implements Serializable,Comparable<Tag> {
    private static final long serialVersionUID = -2730271140335892466L;
    private String tid;
    private String name;
    private Integer heat;

    @Override
    public String toString() {
        return "Tag{" +
                "tid='" + tid + '\'' +
                ", name='" + name + '\'' +
                ", heat=" + heat +
                '}';
    }

    @Override
    public int compareTo(Tag o) {
        return this.heat-o.getHeat();
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

    public Integer getHeat() {
        return heat;
    }

    public void setHeat(Integer heat) {
        this.heat = heat;
    }
}
