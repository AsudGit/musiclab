package com.lhs.musiclab.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lhs.musiclab.enums.Plate;
import com.lhs.musiclab.pojo.Blog;
import com.lhs.musiclab.service.BlogService;
import com.lhs.musiclab.utils.QuickSort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.*;

@Controller
@RequestMapping("/blog")
public class BlogController {
    @Autowired
    private BlogService blogService;
    private Map<String,Object> map = new HashMap<>();

    @RequestMapping("/{plate}")
    public String toBlog(@PathVariable(value = "plate")int plate, Model model){
        Plate currentPlate=Plate.values()[plate-1];
        model.addAttribute("plates",Plate.values());
        model.addAttribute("plateName", currentPlate.getValue());
        model.addAttribute("plateimg", "/image/" + currentPlate.toString().toLowerCase()+".jpg");
        return "blog_index";
    }
    @RequestMapping("/info/{bid}")
    public String blogInfo(@PathVariable(value = "bid")String bid,ModelMap modelMap){
        System.out.println("进入");
        Blog blog = new Blog();
        blog.setBid(bid);
        List<Blog> blogList = blogService.get(blog);
        System.out.println(blogList);
        modelMap.put("blog", blogList.get(0));
        return "blog_info";
    }

    @PostMapping("/get")
    @ResponseBody
    public PageInfo<Blog> search(@RequestParam(value = "uid",defaultValue = "")String uid,
                                 @RequestParam(value = "bid",defaultValue = "")String bid,
                                 @RequestParam(value = "title",defaultValue = "")String title,
                                 @RequestParam(value = "plate",defaultValue = "0")Integer plate,
                                 @RequestParam(value = "start", defaultValue = "1") Integer start,
                                 @RequestParam(value = "size", defaultValue = "5") Integer size){
        Blog blog = new Blog();
        System.out.println(start);
        if (title!="") {
            blog.setTitle(title);
        }
        if (plate!=0){
            blog.setPlate(plate);
        }
        if (bid != "") {
            blog.setBid(bid);
        }
        if (uid != "") {
            blog.setUid(uid);
        }
        System.out.println(start);
        PageHelper.startPage(start, size);
        List<Blog> blogList = blogService.get(blog);
        System.out.println(blogList.size());
        PageInfo<Blog> blogPage = new PageInfo<>(blogList);

        return blogPage;
    }

    @PostMapping("/add")
    @ResponseBody
    public Map bloging(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("plate") Integer plate,
            HttpServletRequest request){
        HttpSession session = request.getSession();
        String userID = (String) session.getAttribute("userID");
        Blog blog = new Blog();
        blog.setBid(UUID.randomUUID().toString().replaceAll("-", ""));
        blog.setTitle(title);
        blog.setContent(content);
        blog.setPlate(plate);
        blog.setUid(userID);
        blog.setBlogged_time(new Timestamp(System.currentTimeMillis()));
        System.out.println(new Timestamp(System.currentTimeMillis()));
        if(blogService.add(blog)==1){
            map.put("msg", "true");
            map.put("plate", plate);
        }else {
            map.put("msg", "fault");
        }
        return map;
    }

    /***
     * 返回博客页面初始化数据
     * @param plate 板块号
     * @param start 页面当前页号
     * @param size 总页面大小
     * @return map
     */
    @GetMapping("/indexInit/{plate}")
    @ResponseBody
    public Map indexInit(@PathVariable(value = "plate") Integer plate,
                         @RequestParam(value = "start", defaultValue = "0") int start,
                         @RequestParam(value = "size", defaultValue = "5") int size){

        PageHelper.startPage(start, size);
        List<Blog> blogList = blogService.listByPlate(plate);
        PageInfo<Blog> blogPage = new PageInfo<>(blogList);

        LinkedList<Blog> linkedlist = blogService.linkedlist();
        QuickSort.linkedlistSort(linkedlist,0,linkedlist.size()-1);
        List<Blog> hotBlogList = new ArrayList<>();
        if (linkedlist.size()>=5){
            for (int i = 0; i < 5; i++) {
                hotBlogList.add(linkedlist.get(i));
            }
        }else {
            for (Blog blog :linkedlist) {
                hotBlogList.add(blog);
            }
        }
        map.put("blogPage", blogPage);
        map.put("hotBlogList", hotBlogList);
        return map;
    }
}
