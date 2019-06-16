package com.lhs.musiclab.pojo;

import java.io.Serializable;

public class StarFolder implements Serializable {
    private static final long serialVersionUID = 2134260059681981539L;
    private String sfid;
    //用户id
    private String uid;
    //文件夹号
    private Integer folder;
    //每个用户都有定制的文件夹
    private String folderName;

    @Override
    public String toString() {
        return "StarFolder{" +
                "sfid='" + sfid + '\'' +
                ", uid='" + uid + '\'' +
                ", folder=" + folder +
                ", folderName='" + folderName + '\'' +
                '}';
    }

    public String getSfid() {
        return sfid;
    }

    public void setSfid(String sfid) {
        this.sfid = sfid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Integer getFolder() {
        return folder;
    }

    public void setFolder(Integer folder) {
        this.folder = folder;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }
}
