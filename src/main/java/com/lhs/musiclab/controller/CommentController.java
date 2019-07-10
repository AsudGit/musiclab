package com.lhs.musiclab.controller;

import com.github.pagehelper.PageInfo;
import com.lhs.musiclab.pojo.*;
import com.lhs.musiclab.service.BlogCountService;
import com.lhs.musiclab.service.CommentService;
import com.lhs.musiclab.service.MLabUserCountService;
import com.lhs.musiclab.utils.MyRandom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private MLabUserCountService mLabUserCountService;
    @Autowired
    private BlogCountService blogCountService;

    /***
     * 增加一条评论
     * @param content 内容
     * @param id 被评论的对象的id
     * @param plate 板块号
     * @param request
     * @return 执行结果
     */
    @GetMapping("/add")
    public Map add(@RequestParam(value = "content") String content,
                   @RequestParam(value = "id")String id,
                   @RequestParam(value = "bid")String bid,
                   @RequestParam(value = "plate") Integer plate,
                   HttpServletRequest request){
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setId(id);
        HttpSession session = request.getSession();
        String userID = (String) session.getAttribute("userID");
        Map<String,Object> map = new HashMap();
        comment.setCid(MyRandom.getUUID());
        comment.setCommented_time(new Timestamp(System.currentTimeMillis()));
        comment.setUid(userID);
        if(commentService.add(comment)>0){//增加一条评论成功
            comment.setLikes(0);
            CommentItem commentItem = new CommentItem();
            MLabUser mLabUser = new MLabUser();
            mLabUser.setUid(comment.getUid());
            mLabUser.setName((String) session.getAttribute("userName"));
            mLabUser.setHead_img((String) session.getAttribute("headImg"));
            commentItem.setComment(comment);
            commentItem.setmLabUser(mLabUser);
            map.put("commentItem", commentItem);//返回新增的评论项
            map.put("msg", true);
            //更新评论总数
            BlogCount blogCount = blogCountService.get(bid);
            blogCount.setComments(blogCount.getComments()+1);
            blogCountService.setForRedis(blogCount);
            //更新用户的评论数
            MLabUserCount mLabUserCount = mLabUserCountService.get(userID);
            mLabUserCount.setComments(mLabUserCount.getComments()+1);
            mLabUserCountService.updateForRedis(userID,mLabUserCount);
            //更新板块评论数
            Map total = mLabUserCountService.getTotalForRedis(plate);//更新板块统计数量
            total.put("commentNums",(int)total.get("commentNums")+1);
            mLabUserCountService.setTotalForRedis(plate,total);
        }else {
            map.put("msg", false);
        }
        return map;
    }

    /***
     * 根据被评论的博客或者评论的id获取分页后的评论数组
     * @param id  被评论的博客或者评论的id
     * @param start 当前页数
     * @param size 页面大小
     * @return 评论数组
     */
    @GetMapping("/list")
    public PageInfo<CommentItem> listById(@RequestParam(value = "id",defaultValue = "") String id,
                                          @RequestParam(value = "uid",defaultValue = "") String uid,
                                   @RequestParam(value = "start",defaultValue = "1")Integer start,
                                   @RequestParam(value = "size",defaultValue = "5")Integer size){
        Comment comment = new Comment();
        if (id!=null && id!=""){
            comment.setId(id);
        }
        if (uid!=null && uid!=""){
            comment.setUid(uid);
        }
        return commentService.list(comment,start,size);
    }

    /***
     * 根据被评论的博客或者评论的id以及限制获取的范围获取评论数组
     * @param id  被评论的博客或者评论的id
     * @param start 当前页数
     * @param size 页面大小
     * @return 评论数组
     */
    @GetMapping("/list/limit")
    public List<CommentItem> listByIdLimit(@RequestParam(value = "id")String id,
                                           @RequestParam(value = "start")Integer start,
                                           @RequestParam(value = "size")Integer size){
        return commentService.listByIdLimit(id, start, size);
    }
}
