package com.lhs.musiclab.service;

import com.lhs.musiclab.pojo.UploadedImg;

import java.util.List;

public interface UploadedImgService {
    public Integer add(UploadedImg uploadedImg);

    public List<UploadedImg> list();

    public List<UploadedImg> list(String uid);

    public void delete(UploadedImg uploadedImg);
}
