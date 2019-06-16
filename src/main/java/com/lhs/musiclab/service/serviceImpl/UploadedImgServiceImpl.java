package com.lhs.musiclab.service.serviceImpl;

import com.lhs.musiclab.dao.UploadedImgMapper;
import com.lhs.musiclab.pojo.UploadedImg;
import com.lhs.musiclab.service.UploadedImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UploadedImgServiceImpl implements UploadedImgService {
    @Autowired
    private UploadedImgMapper uploadedImgMapper;

    @Override
    public Integer add(UploadedImg uploadedImg) {
        return uploadedImgMapper.add(uploadedImg);
    }

    @Override
    public List<UploadedImg> list() {
        return uploadedImgMapper.list();
    }

    @Override
    public void delete(UploadedImg uploadedImg) {
        uploadedImgMapper.delete(uploadedImg);
    }
}
