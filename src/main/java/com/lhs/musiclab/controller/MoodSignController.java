package com.lhs.musiclab.controller;

import com.github.pagehelper.PageInfo;
import com.lhs.musiclab.pojo.MoodSign;
import com.lhs.musiclab.service.MoodSignService;
import com.lhs.musiclab.utils.MyRandom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
@RestController
@RequestMapping("/sign")
public class MoodSignController {
    @Autowired
    private MoodSignService moodSignService;
    @GetMapping("/add")
    public Map add(@RequestParam(value = "content")String content,
                   @RequestParam(value = "uid")String uid,
                   HttpServletRequest request){
        Map<String,Object> map = new HashMap();
        HttpSession session = request.getSession();
        MoodSign moodSign = new MoodSign();
        moodSign.setContent(content);
        moodSign.setUid(uid);
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
    @GetMapping("/list")
    public PageInfo<MoodSign> listByUid(@RequestParam(value = "uid")String uid,
                              @RequestParam(value = "start",defaultValue = "1")Integer start,
                              @RequestParam(value = "size",defaultValue = "5")Integer size){
        PageInfo<MoodSign> pageInfo = moodSignService.listByUid(uid, start, size);
        return pageInfo;
    }

    @GetMapping("/get/{uid}")
    public MoodSign get(@PathVariable(value = "uid") String uid){
        MoodSign moodSign = moodSignService.getByUid(uid);
        return moodSign;
    }
}
