package com.lhs.musiclab.controller;

import com.github.pagehelper.PageInfo;
import com.lhs.musiclab.pojo.MoodSign;
import com.lhs.musiclab.service.MoodSignService;
import com.lhs.musiclab.utils.MyRandom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/sign")
public class MoodSignController {
    @Autowired
    private MoodSignService moodSignService;
    @GetMapping("/add")
    public Map add(@RequestParam(value = "content")String content,
                   HttpServletRequest request){
        Map<String,Object> map = new HashMap();
        HttpSession session = request.getSession();
        String userID = (String) session.getAttribute("userID");
        MoodSign moodSign = new MoodSign();
        moodSign.setContent(content);
        moodSign.setUid(userID);
        moodSign.setMsid(MyRandom.getUUID());
        moodSign.setSigned_time(new Timestamp(System.currentTimeMillis()));
        if (moodSignService.add(moodSign)>0){
            moodSign.setLikes(0);
            map.put("moodsign",moodSign);
            map.put("msg", "true");
        }else {
            map.put("msg", "false");
        }
        return map;
    }
    @GetMapping("/get")
    public PageInfo<MoodSign> get(@RequestParam(value = "uid",required = false)String uid,
                              @RequestParam(value = "start",defaultValue = "1")Integer start,
                              @RequestParam(value = "size",defaultValue = "5")Integer size,
                                  HttpServletRequest request){
        PageInfo<MoodSign> pageInfo = moodSignService.get(uid, start, size);
        return pageInfo;
    }
}
