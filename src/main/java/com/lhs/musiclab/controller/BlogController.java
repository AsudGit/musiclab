package com.lhs.musiclab.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lhs.musiclab.enums.Plate;
import com.lhs.musiclab.pojo.*;
import com.lhs.musiclab.service.BlogService;
import com.lhs.musiclab.service.Blog_tagService;
import com.lhs.musiclab.service.TagService;
import com.lhs.musiclab.utils.MyRandom;
import com.lhs.musiclab.utils.QuickSort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
    @Autowired
    private TagService tagService;
    @Autowired
    private Blog_tagService blog_tagService;
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
        List<BlogItem> blogList = blogService.get(blog);
        System.out.println(blogList);
        modelMap.put("blog", blogList.get(0));
        return "blog_info";
    }

    @PostMapping("/get")
    @ResponseBody
    public PageInfo<BlogItem> search(@RequestParam(value = "uid",defaultValue = "")String uid,
                                     @RequestParam(value = "bid",defaultValue = "")String bid,
                                     @RequestParam(value = "title",defaultValue = "")String title,
                                     @RequestParam(value = "plate",defaultValue = "0")Integer plate,
                                     @RequestParam(value = "start", defaultValue = "1") Integer start,
                                     @RequestParam(value = "size", defaultValue = "5") Integer size){
        Blog blog = new Blog();
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
        PageHelper.startPage(start, size);
        List<BlogItem> blogList = blogService.get(blog);
        PageInfo<BlogItem> blogPage = new PageInfo<>(blogList);

        return blogPage;
    }

    /***
     * 发表博客，增加博客表、标签表、博客关联表数据
     * @param blog_tagNames
     * @param request
     * @return map
     */
    @PostMapping(value = "/add")
    @Transactional
    @ResponseBody
    public Map bloging(@RequestBody Blog_tagNames blog_tagNames, HttpServletRequest request){

        String[] tagNames = blog_tagNames.getNames();
        String[] tids=new String[tagNames.length];
        int i = 0;
        if(tagNames.length!=0) {
            //检查并增加新的标签
            for (String name : tagNames) {
                Tag tag = new Tag();
                tag.setName(name);
                Tag result = tagService.get(tag);
                if (result == null) {
                    tids[i] = MyRandom.getUUID();
                    tag.setTid(tids[i]);
                    tagService.add(tag);
                } else {
                    tids[i] = result.getTid();
                }
                i++;
            }
        }

        HttpSession session = request.getSession();
        String userID = (String) session.getAttribute("userID");
        Blog blog = blog_tagNames.getBlog();
        String bid = MyRandom.getUUID();
        blog.setBid(bid);
        blog.setUid(userID);
        blog.setBlogged_time(new Timestamp(System.currentTimeMillis()));
        blog.setRevised_time(blog.getBlogged_time());
        if(blogService.add(blog)==1){//插入博客表
            if (tids.length!=0) {
                for (i = 0;i<tids.length;i++) {
                    Blog_tag blogTag = new Blog_tag();
                    blogTag.setId(MyRandom.getUUID());
                    blogTag.setBid(bid);
                    blogTag.setTid(tids[i]);
                    blog_tagService.add(blogTag);
                }
            }
            map.put("msg", "true");
            map.put("plate", blog.getPlate());
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
        List<BlogItem> blogList = blogService.listByPlate(plate);
        PageInfo<BlogItem> blogPage = new PageInfo<>(blogList);

        LinkedList<BlogItem> linkedlist = blogService.linkedlist();
        QuickSort.linkedlistSort(linkedlist,0,linkedlist.size()-1);
        List<BlogItem> hotBlogList = new ArrayList<>();
        if (linkedlist.size()>=5){
            for (int i = 0; i < 5; i++) {
                hotBlogList.add(linkedlist.get(i));
            }
        }else {
            for (BlogItem blog :linkedlist) {
                hotBlogList.add(blog);
            }
        }
        map.put("blogPage", blogPage);
        map.put("hotBlogList", hotBlogList);
        return map;
    }
}
