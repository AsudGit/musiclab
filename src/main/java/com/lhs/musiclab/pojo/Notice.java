package com.lhs.musiclab.pojo;

import java.io.Serializable;
import java.sql.Timestamp;

public class Notice implements Serializable {
    private String nid;
    private String title;
    private String content;
    private Timestamp noticed_time;
    //管理员id
    private String mid;

    @Override
    public String toString() {
        return "Notice{" +
                "nid='" + nid + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", noticed_time=" + noticed_time +
                ", mid='" + mid + '\'' +
                '}';
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getNoticed_time() {
        return noticed_time;
    }

    public void setNoticed_time(Timestamp noticed_time) {
        this.noticed_time = noticed_time;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }
}
