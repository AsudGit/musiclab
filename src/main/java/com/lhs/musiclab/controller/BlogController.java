package com.lhs.musiclab.controller;

import com.lhs.musiclab.pojo.Blog;
import com.lhs.musiclab.pojo.Plate;
import com.lhs.musiclab.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/blog")
public class BlogController {
    @Autowired
    private BlogService blogService;

    @RequestMapping("/{plate}")
    public String toBlog(@PathVariable(value = "plate")String plate){
        return "blog_index";
    }
    @GetMapping("/indexInit/{plate}")
    @ResponseBody
    public List indexInit(@PathVariable(value = "plate") Integer plate, HttpServletRequest request){
        String userName = (String) request.getSession().getAttribute("userName");
        String headImg = (String) request.getSession().getAttribute("headImg");
        List<Blog> blogList = blogService.listByPlate(plate);

        return null;
    }
}
