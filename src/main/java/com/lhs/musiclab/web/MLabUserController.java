package com.lhs.musiclab.web;

import com.lhs.musiclab.service.MLabUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MLabUserController {
    @Autowired
    private MLabUserService mLabUserService;

    @RequestMapping("/index")
    public String index(){
        return "index";
    }


    @RequestMapping("/test")
    public String test(){
        return "test";
    }

    @RequestMapping("/test1")
    public String test1(){
        return "test1";
    }

    @RequestMapping("/blogIndex")
    public String blog(){
        return "blog_index";
    }

    @RequestMapping("/musicIndex")
    public String music(){
        return "music_index";
    }

}
