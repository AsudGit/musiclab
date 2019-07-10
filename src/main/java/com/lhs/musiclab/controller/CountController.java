package com.lhs.musiclab.controller;

import com.lhs.musiclab.pojo.BlogCount;
import com.lhs.musiclab.pojo.LikeBlogs;
import com.lhs.musiclab.pojo.MLabUserCount;
import com.lhs.musiclab.service.BlogCountService;
import com.lhs.musiclab.service.LikeBlogsService;
import com.lhs.musiclab.service.MLabUserCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/count")
public class CountController {
    @Autowired
    private MLabUserCountService mLabUserCountService;
    @Autowired
    private BlogCountService blogCountService;
    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/mlabuser/get")
    public MLabUserCount get(HttpServletRequest request){
        String userID = (String) request.getSession().getAttribute("userID");
        MLabUserCount mLabUserCount = mLabUserCountService.get(userID);
        return mLabUserCount;
    }
}
