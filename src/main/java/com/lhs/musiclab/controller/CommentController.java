package com.lhs.musiclab.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lhs.musiclab.pojo.Comment;
import com.lhs.musiclab.pojo.CommentItem;
import com.lhs.musiclab.pojo.MLabUser;
import com.lhs.musiclab.service.CommentService;
import com.lhs.musiclab.service.MLabUserCountService;
import com.lhs.musiclab.utils.MyRandom;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
        if(commentService.add(comment)>0){
            comment.setLikes(0);
            CommentItem commentItem = new CommentItem();
            MLabUser mLabUser = new MLabUser();
            mLabUser.setUid(comment.getUid());
            mLabUser.setName((String) session.getAttribute("userName"));
            mLabUser.setHead_img((String) session.getAttribute("headImg"));
            commentItem.setComment(comment);
            commentItem.setmLabUser(mLabUser);
            map.put("commentItem", commentItem);
            map.put("msg", true);
            mLabUserCountService.countComments(userID);
            commentService.commentsIncr(plate);
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
    public PageInfo<CommentItem> listById(@RequestParam(value = "id") String id,
                                   @RequestParam(value = "start",defaultValue = "1")Integer start,
                                   @RequestParam(value = "size",defaultValue = "5")Integer size){
        return commentService.list(id,start,size);
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
