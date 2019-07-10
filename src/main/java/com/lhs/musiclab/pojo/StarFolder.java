package com.lhs.musiclab.pojo;

import java.io.Serializable;

public class StarFolder implements Serializable {
    private static final long serialVersionUID = 1578201086646660872L;
    //用户id
    private String uid;
    //收藏夹号
    private Integer folder;
    //每个用户都有定制的收藏夹
    private String folderName;

    @Override
    public String toString() {
        return "StarFolder{" +
                "uid='" + uid + '\'' +
                ", folder=" + folder +
                ", folderName='" + folderName + '\'' +
                '}';
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
