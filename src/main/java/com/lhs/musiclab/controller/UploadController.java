package com.lhs.musiclab.controller;

import com.lhs.musiclab.pojo.UploadedImg;
import com.lhs.musiclab.pojo.WangEditor;
import com.lhs.musiclab.service.UploadedImgService;
import com.lhs.musiclab.utils.MyRandom;
import com.lhs.musiclab.utils.SendCode;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/upload")
public class UploadController {
    @Autowired
    private UploadedImgService uploadedImgService;
    private Logger logger = LoggerFactory.getLogger(getClass());
    // 获取文件上传路径
    @Value("${web.upload-path}")
    private String uploadPath;
    @Value("${web.domain}")
    private String domain;

    // 图片上传
    @ResponseBody
    @PostMapping(value = "/image")
    public WangEditor uploadFile(
            @RequestParam("imgFile") MultipartFile[] multipartFiles,
            HttpServletRequest request) {
        String userID= (String) request.getSession().getAttribute("userID");
        String[] paths = new String[multipartFiles.length];
        String[] urls = new String[multipartFiles.length];
        int i = 0;
        for (MultipartFile m : multipartFiles) {
            String fileName = getFileName(m.getOriginalFilename());
            //图片上传文件夹路径
            String suffixPath = "image" + '/' + userID ;
            //图片上传全路径
            String uploadedFileName = uploadPath + suffixPath + '/' + fileName;
            try {
                File destFile = new File(uploadedFileName);
                //如果文件夹不存在，生成文件夹
                if(!destFile.getParentFile().exists()&&!destFile.getParentFile().isDirectory()){
                    destFile.getParentFile().mkdirs();
                }
                m.transferTo(destFile);
                logger.info("上传图片至："+uploadedFileName);
                paths[i] = suffixPath + '/' + fileName;
                urls[i] = domain + paths[i];
                System.out.println(paths[i]);
                uploadedImgService.add(
                        new UploadedImg(MyRandom.getUUID(),
                        userID,
                        urls[i],
                        new Date(System.currentTimeMillis()
                        )
                        ));
                i++;
            } catch (IOException e) {
                logger.error("上传文件失败", e);
                return new WangEditor(1, new String[]{"文件传输过程异常"});
            }catch (Exception e){
                logger.error("图片地址插入数据库失败",e);
                //将已上传的图片删除
                for (int j = 0; j < i+1; j++) {
                    File file=new File(uploadPath+paths[j]);
                    if(file.exists()&&file.isFile())
                        file.delete();
                }
                return new WangEditor(2, new String[]{"插入数据库异常"});
            }
        }
        return new WangEditor(urls);
    }

    public String getFileName(String oldName) {
        String suffix = oldName.substring(oldName.lastIndexOf("."));
        String fileName = System.currentTimeMillis() + SendCode.getVerifyCode(SendCode.CODE_NUMBER_CHAR, SendCode.CODE_LENGTH) + suffix;
        return fileName;
    }
}