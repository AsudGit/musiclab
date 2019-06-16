package com.lhs.musiclab.pojo;

import java.io.Serializable;
import java.util.Date;

public class UploadedImg implements Serializable {
    private static final long serialVersionUID = -2840444344049542902L;
    private String id;
    //上传的用户的id
    private String uid;
    //上传图片访问地址
    private String url;
    private Date uploaded_time;

    public UploadedImg() {
    }

    public UploadedImg(String id, String uid, String url, Date uploaded_time) {
        this.id = id;
        this.uid = uid;
        this.url = url;
        this.uploaded_time = uploaded_time;
    }

    @Override
    public String toString() {
        return "UploadedImg{" +
                "id='" + id + '\'' +
                ", uid='" + uid + '\'' +
                ", url='" + url + '\'' +
                ", uploaded_time=" + uploaded_time +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getUploaded_time() {
        return uploaded_time;
    }

    public void setUploaded_time(Date uploaded_time) {
        this.uploaded_time = uploaded_time;
    }
}
