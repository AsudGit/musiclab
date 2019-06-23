package com.lhs.musiclab.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lhs.musiclab.enums.Plate;
import com.lhs.musiclab.pojo.*;
import com.lhs.musiclab.service.*;
import com.lhs.musiclab.utils.MyRandom;
import com.lhs.musiclab.utils.QuickSort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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
//origins  ： 允许可访问的域列表
//maxAge：准备响应前的缓存持续的最大时间（以秒为单位）。
@CrossOrigin(origins = "*", maxAge = 3600)
public class BlogController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private TagService tagService;
    @Autowired
    private Blog_tagService blog_tagService;
    @Autowired
    private MLabUserCountService mLabUserCountService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private RedisTemplate redisTemplate;


    /***
     * 板块路由跳转，准备对应板块的信息
     * @param plate 板块号
     * @param model
     * @return 博客html首页
     */
    @RequestMapping("/{plate}")
    public String toBlog(@PathVariable(value = "plate")int plate, Model model){
        Plate currentPlate=Plate.values()[plate-1];
        model.addAttribute("plates",Plate.values());
        model.addAttribute("plateName", currentPlate.getValue());
        model.addAttribute("plateimg", "/image/" + currentPlate.toString().toLowerCase()+".jpg");
        return "blog_index";
    }

    /***
     *博客评论数量统计
     * @param plate
     * @return 统计结果
     */
    @GetMapping("/total")
    @ResponseBody
    public Map total(@RequestParam(value = "plate")Integer plate){
        Map map= (HashMap) redisTemplate.opsForValue().get("total:" +plate);
        if (map==null||map.size()==0){
            map = new HashMap();
            Integer blogNums = blogService.countBlogsByPlate(plate);
            Integer commentNums = commentService.countCommentsForBlog(plate) + commentService.countCommentsForComment(plate);
            map.put("blogNums", blogNums);
            map.put("commentNums", commentNums);
            map.put("newNums", 0);
            redisTemplate.opsForValue().set("total:"+plate,map);
        }
        return map;
    }

    /***
     * 查询出最近的热门博客
     * @return 5条热度最高的博客
     */
    @GetMapping("/hot")
    @ResponseBody
    public List<BlogItem> hotBlogList(){
        List<BlogItem> hotBlogList = (List<BlogItem>) redisTemplate.opsForValue().get("hot:blog");
        if (hotBlogList==null) {
            LinkedList<BlogItem> linkedlist = blogService.linkedlist();
            QuickSort.enableDESC();
            QuickSort.linkedlistSort(linkedlist, 0, linkedlist.size() - 1);
            hotBlogList = new ArrayList<>();
            if (linkedlist.size() >= 5) {
                for (int i = 0; i < 5; i++) {
                    hotBlogList.add(linkedlist.get(i));
                }
            } else {
                for (BlogItem blog : linkedlist) {
                    hotBlogList.add(blog);
                }
            }
            redisTemplate.opsForValue().set("hot:blog",hotBlogList);
        }
        return hotBlogList;
    }

    /***
     * 根据参数查询博客、分页
     * @param uid 用户id
     * @param bid 博客id
     * @param title 标题
     * @param plate 板块号
     * @param start 当前页数
     * @param size 一页大小
     * @return 包含分页信息和页面内博客相关信息的当前页实体
     */
    @PostMapping("/get")
    @ResponseBody
    public PageInfo<BlogItem> get(@RequestParam(value = "uid",defaultValue = "")String uid,
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

        return blogService.get(blog,start,size);
    }

    /***
     * 发表博客，增加博客表、标签表、博客关联表数据
     * @param blog_tagNames
     * @param request
     * @return 执行结果
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
                    tagService.update(tids[i], result.getHeat() + 1);
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
        Map<String,Object> map = new HashMap<>();
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
            mLabUserCountService.countBlogs(userID);
            blogService.newsIncr(blog_tagNames.getBlog().getPlate());
        }else {
            map.put("msg", "fault");
        }
        return map;
    }
}
