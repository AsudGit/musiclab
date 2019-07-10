package com.lhs.musiclab.controller;

import com.lhs.musiclab.pojo.UploadedImg;
import com.lhs.musiclab.service.UploadedImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/myimg")
public class UploadedImgController {
    @Autowired
    private UploadedImgService uploadedImgService;

    @GetMapping("/list/{uid}")
    public List<UploadedImg> listByUid(@PathVariable(value = "uid")String uid) {
        return uploadedImgService.list(uid);
    }
}
