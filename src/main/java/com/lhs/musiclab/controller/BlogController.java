package com.lhs.musiclab.controller;

import com.github.pagehelper.PageInfo;
import com.lhs.musiclab.enums.Plate;
import com.lhs.musiclab.pojo.*;
import com.lhs.musiclab.service.*;
import com.lhs.musiclab.utils.MyRandom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
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
    private BlogCountService blogCountService;
    @Autowired
    private LikeBlogsService likeBlogsService;
    @Autowired
    private StarBlogService starBlogService;
    @Autowired
    private StarFolderService starFolderService;
    @Autowired
    private TagService tagService;
    @Autowired
    private Blog_tagService blog_tagService;
    @Autowired
    private MLabUserCountService mLabUserCountService;
    @Autowired
    private CommentService commentService;


    /***
     * 板块路由跳转，准备对应板块的信息
     * @param plate 板块号
     * @param model
     * @return 博客html首页
     */
    @RequestMapping(value ="/{plate}")
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
        Map map = mLabUserCountService.getTotalForRedis(plate);
        if (map==null||map.size()==0){//redis中没有统计信息则重新计算
            map = new HashMap();
            Integer blogNums = blogService.countBlogsByPlate(plate);
            Integer commentNums = commentService.countCommentsForBlog(plate) + commentService.countCommentsForComment(plate);
            map.put("blogNums", blogNums);
            map.put("commentNums", commentNums);
            map.put("newNums", 0);
            mLabUserCountService.setTotalForRedis(plate,map);
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
        return blogService.getHotBlog();
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
                                     @RequestParam(value = "size", defaultValue = "5") Integer size, HttpServletRequest request){
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
        HttpSession session = request.getSession();
        String userID = (String) session.getAttribute("userID");
        PageInfo<BlogItem> pageInfo = blogService.get(blog, start, size);
        List<BlogItem> blogItems = pageInfo.getList();//查出博客和用户信息
        int i = 0;
        for (BlogItem b:blogItems) {//查出缓存中的博客统计信息
            String id=b.getBlog().getBid();
            BlogCount blogCount = blogCountService.get(id);
            blogItems.get(i).setBlogCount(blogCount);

            //TODO:收藏做redis部分，点赞定时任务未作，其他优化未作，评论数量redis未作
            //TODO:原则，热点数据以redis为数据库，mysql为备份，温数据以mysql为数据库，redis为缓存，设置过期时间
            //查出用户是否对博客点赞和收藏
            LikeBlogs_status like = likeBlogsService.get(userID, id);
            List<StarBlog_status> starList = starBlogService.get(userID, id);
            boolean star = false;
            //1表示新点赞需要插入数据库，3表示旧点赞已存在于数据库，0表示没有点赞，2表示取消点赞需要删除数据库
            blogItems.get(i).setLike(like.getStatus()==1||like.getStatus()==3);
            for (StarBlog_status s :starList) {
                if (s.getStatus()==1||s.getStatus()==3){
                    star = true;
                    break;
                }
            }
            blogItems.get(i).setStar(star);
            i++;
        }
        pageInfo.setList(blogItems);
        return pageInfo;
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
                if (result == null) {//当标签不存在时新增标签
                    tids[i] = MyRandom.getUUID();
                    tag.setTid(tids[i]);
                    tagService.add(tag);
                } else {//当标签存在时更新标签被引用的次数
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
        BlogCount blogCount = new BlogCount();
        blogCount.setBid(bid);
        blogCount.setRevised_time(blog.getBlogged_time());
        Map<String,Object> map = new HashMap<>();
        if(blogService.add(blog)==1&&blogCountService.add(blogCount)==1){//插入博客表成功
            if (tids.length!=0) {
                for (i = 0;i<tids.length;i++) {
                    Blog_tag blogTag = new Blog_tag();
                    blogTag.setId(MyRandom.getUUID());
                    blogTag.setBid(bid);
                    blogTag.setTid(tids[i]);
                    blog_tagService.add(blogTag);
                }
            }
            //更新用户的发博量
            MLabUserCount mLabUserCount = mLabUserCountService.get(userID);
            mLabUserCount.setBlogs(mLabUserCount.getBlogs()+1);
            mLabUserCountService.updateForRedis(userID,mLabUserCount);
            //更新板块博客数和新鲜事
            Map total = mLabUserCountService.getTotalForRedis(blog_tagNames.getBlog().getPlate());//更新板块统计数量
            total.put("blogNums",(int)total.get("blogNums")+1);
            total.put("newNums",(int)total.get("newNums")+1);
            mLabUserCountService.setTotalForRedis(blog_tagNames.getBlog().getPlate(),total);

            map.put("msg", "true");
            map.put("plate", blog.getPlate());
        }else {
            map.put("msg", "fault");
        }
        return map;
    }

    @GetMapping("/like")
    @ResponseBody
    public Map likeBlog(@RequestParam(value = "like")boolean like,
            @RequestParam(value = "bid")String bid,
                        HttpServletRequest request){
        String userID = (String) request.getSession().getAttribute("userID");
        //更新redis点赞关联
        LikeBlogs_status likeBlogsStatus = likeBlogsService.get(userID, bid);
        if (likeBlogsStatus.getStatus()==1||likeBlogsStatus.getStatus()==3){
            likeBlogsStatus.setStatus(likeBlogsStatus.getStatus() - 1);
        }else {
            likeBlogsStatus.setStatus(likeBlogsStatus.getStatus() + 1);
        }
        likeBlogsService.setForRedis(likeBlogsStatus);
        //更新redis点赞数量
        BlogCount blogCount = blogCountService.get(bid);
        if (like){//已经点过赞的取消赞,否则点赞
            blogCount.setLikes(blogCount.getLikes()-1);
        }else {
            blogCount.setLikes(blogCount.getLikes()+1);
        }
        blogCountService.setForRedis(blogCount);
        Map<String, Object> map = new HashMap<>();
        map.put("msg", true);
        return map;
    }
    @GetMapping("/like/get")
    @ResponseBody
    public List<BlogItem> getByLike(@RequestParam(value = "uid")String uid){
        List<LikeBlogs_status> likeBlogsStatuses = likeBlogsService.get(uid);
        List<BlogItem> blogItems = new ArrayList<>();
        for (LikeBlogs_status l:likeBlogsStatuses) {
            if (l.getStatus()==0||l.getStatus()==2)continue;
            Blog blog = new Blog();
            blog.setBid(l.getLikeBlogs().getBid());
            List<BlogItem> list = blogService.get(blog);
            blogItems.add(list.get(0));
        }
        return blogItems;
    }

    @GetMapping("/star/folder/get")
    @ResponseBody
    public List<StarFolder> getStarFolder(HttpServletRequest request){
        String userID = (String) request.getSession().getAttribute("userID");
        return starFolderService.get(userID);
    }

    @GetMapping("/star/folder/add")
    @ResponseBody
    public StarFolder addStarFolder(@RequestParam(value = "folderName")String folderName,
                             @RequestParam(value = "folder")Integer folder,
                             HttpServletRequest request){
        String userID = (String) request.getSession().getAttribute("userID");
        StarFolder starFolder = new StarFolder();
        starFolder.setFolderName(folderName);
        starFolder.setUid(userID);
        starFolder.setFolder(folder+1);
        starFolderService.add(starFolder);
        return starFolder;
    }

    @PostMapping("/star")
    @ResponseBody
    public Map starBlog(@RequestBody Map map,
                        HttpServletRequest request){
        String userID = (String) request.getSession().getAttribute("userID");
        String bid = (String) map.get("bid");
        List<Integer> folders = (List<Integer>) map.get("folders");
        for (int i = 0; i < folders.size(); i++) {
            StarBlog_status starBlog_status = starBlogService.get(userID, bid, folders.get(i));
            starBlog_status.setStatus(starBlog_status.getStatus()+1);
            starBlogService.setForRedis(starBlog_status);
        }
        map.clear();
        map.put("msg", true);
        return map;
        //TODO:js测试
    }

    @GetMapping("/star/cancel/{bid}")
    @ResponseBody
    public Map CancelStar(@PathVariable(value = "bid")String bid,
                          HttpServletRequest request){
        String userID = (String) request.getSession().getAttribute("userID");
        List<StarBlog_status> starBlogStatuses = starBlogService.get(userID, bid);
        for (StarBlog_status s:starBlogStatuses) {
            if (s.getStatus()==1||s.getStatus()==3) {
                s.setStatus(s.getStatus() - 1);
            }
            starBlogService.setForRedis(s);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("msg",true);
        return map;
    }

    @GetMapping("/star/get")
    @ResponseBody
    public List<BlogItem> getByFolder(@RequestParam(value = "uid")String uid,
                                          @RequestParam(value = "folder")Integer folder){
        List<StarBlog_status> starBlogStatuses = starBlogService.get(uid, folder);
        List<BlogItem> blogItems = new ArrayList<>();
        for (StarBlog_status s : starBlogStatuses) {
            if (s.getStatus()==0||s.getStatus()==2)continue;
            Blog blog = new Blog();
            blog.setBid(s.getStarBlog().getBid());
            List<BlogItem> list = blogService.get(blog);
            blogItems.add(list.get(0));
        }
        return blogItems;
    }

    /***
     * 浏览次数增加
     * @param bid
     * @param request
     * @return 执行结果
     */
    @GetMapping("/view/{bid}")
    @ResponseBody
    public Map viewBlog(@PathVariable(value = "bid")String bid,
                        HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        String ip = request.getRemoteAddr();
        if(blogCountService.getViewHistoryForRedis(bid,ip)==null){//该IP1小时内未访问过该博客
            BlogCount blogCount = blogCountService.get(bid);
            blogCount.setViews(blogCount.getViews()+1);
            blogCountService.setForRedis(blogCount);
            blogCountService.setViewHistoryForRedis(bid,ip);
            map.put("msg", true);
        }else {
            map.put("msg", false);
        }
        return map;
    }

}
