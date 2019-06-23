package com.lhs.musiclab.controller;

import com.lhs.musiclab.pojo.MLabUserCount;
import com.lhs.musiclab.service.MLabUserCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/count")
public class MLabUserCountController {
    @Autowired
    private MLabUserCountService mLabUserCountService;

    @GetMapping("/get")
    public MLabUserCount get(HttpServletRequest request){
        String userID = (String) request.getSession().getAttribute("userID");
        MLabUserCount mLabUserCount = mLabUserCountService.get(userID);
        return mLabUserCount;
    }
}
