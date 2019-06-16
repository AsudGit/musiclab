package com.lhs.musiclab.dao;

import com.lhs.musiclab.pojo.UploadedImg;

import java.util.List;

public interface UploadedImgMapper {
    public Integer add(UploadedImg uploadedImg);

    public List<UploadedImg> list();

    public void delete(UploadedImg uploadedImg);
}
