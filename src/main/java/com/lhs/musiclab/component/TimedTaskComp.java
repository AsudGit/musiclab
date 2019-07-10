package com.lhs.musiclab.component;

import com.lhs.musiclab.enums.Plate;
import com.lhs.musiclab.pojo.*;
import com.lhs.musiclab.service.*;
import com.lhs.musiclab.utils.MyRandom;
import com.lhs.musiclab.utils.QuickSort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class TimedTaskComp {
    @Autowired
    private BlogService blogService;
    @Autowired
    private BlogCountService blogCountService;
    @Autowired
    private MLabUserCountService mLabUserCountService;
    @Autowired
    private TagService tagService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private LikeBlogsService likeBlogsService;
    @Autowired
    private StarBlogService starBlogService;
    @Autowired
    private RedisTemplate redisTemplate;
    private Logger logger = LoggerFactory.getLogger(getClass());

    /***
     * 凌晨6点同步redis与数据库数据
     */
    @Scheduled(cron = "0 0 6 * * ?")
    public void update(){
        logger.info("----------------------发布数据同步任务----------------------");
        updateMlabuserCount();
        updateBlogCount();
        updateLikeBlogs();
        updateStarBlogs();
    }

    /***
     * 更新redis热门数据
     */
    @Scheduled(cron = "0 30 6 * * ?")
    public void hot(){
        updateHot();
        updateTotal();
    }

    /***
     * 同步redis与数据库的mlabusercount表
     */
    @Async
    public void updateMlabuserCount(){
        logger.info("----------------------同步mlabusercount表----------------------");
        Set<String> keys = redisTemplate.keys("count:mlabuser:*");
        Iterator<String> it= keys.iterator();
        while (it.hasNext()){
            String uid = it.next().substring(15);
            Integer blogNums = blogService.countBlogsByUid(uid);
            Integer commentNums = commentService.countCommentsByUid(uid);
            MLabUserCount mLabUserCount= (MLabUserCount) redisTemplate.opsForValue().get("count:mlabuser:"+uid);
            mLabUserCount.setBlogs(blogNums);
            mLabUserCount.setComments(commentNums);
//            mLabUserCount.setFans();//TODO:同步粉丝数量
            mLabUserCountService.update(mLabUserCount);
        }
        //删除redis中的count表
        redisTemplate.delete(keys);
        logger.info("----------------------mlabusercount表同步结束----------------------");
    }



    /***
     * 同步redis与数据库的blogcount表
     */
    @Async
    public void updateBlogCount(){
        logger.info("----------------------同步blogcount表----------------------");
        Set<String> keys = redisTemplate.keys("count:blog:*");
        Iterator<String> it= keys.iterator();
        while (it.hasNext()){
            String key = it.next();
            BlogCount blogCount = (BlogCount) redisTemplate.opsForValue().get(key);
            blogCountService.update(blogCount);
        }
        //删除redis中的count表
        redisTemplate.delete(keys);
        logger.info("----------------------blogcount表同步结束----------------------");
    }

    public void updateLikeBlogs(){
        logger.info("----------------------同步likeblogs表----------------------");
        Set<String> keys = redisTemplate.keys("like:blog:*");
        Iterator<String> it= keys.iterator();
        while (it.hasNext()){
            String key = it.next();
            LikeBlogs_status likeBlogsStatus = (LikeBlogs_status) redisTemplate.opsForValue().get(key);
            if (likeBlogsStatus.getStatus()==1){//增加
                likeBlogsStatus.getLikeBlogs().setLid(MyRandom.getUUID());
                likeBlogsService.add(likeBlogsStatus.getLikeBlogs());
            }else if (likeBlogsStatus.getStatus()==2){//删除
                likeBlogsService.delete(likeBlogsStatus.getLikeBlogs().getLid());
            }
        }
        //删除redis中的count表
        redisTemplate.delete(keys);
        logger.info("----------------------likeblogs表同步结束----------------------");
    }

    public void updateStarBlogs(){
        logger.info("----------------------同步starblogs表----------------------");
        Set<String> keys = redisTemplate.keys("star:blog:*");
        Iterator<String> it= keys.iterator();
        while (it.hasNext()){
            String key = it.next();
            StarBlog_status starBlog_status = (StarBlog_status) redisTemplate.opsForValue().get(key);
            if (starBlog_status.getStatus()==1){//增加
                starBlog_status.getStarBlog().setSbid(MyRandom.getUUID());
                starBlogService.add(starBlog_status.getStarBlog());
            }else if (starBlog_status.getStatus()==2){//删除
                starBlogService.delete(starBlog_status.getStarBlog().getSbid());
            }
        }
        //删除redis中的count表
        redisTemplate.delete(keys);
        logger.info("----------------------starblogs表同步结束----------------------");
    }

    /***
     * 更新热门数据
     */
    @Async
    public void updateHot(){
        logger.info("----------------------更新热门数据----------------------");
        //更新热词
        redisTemplate.opsForValue().set("hot:tag",tagService.listByLimit(0, 20));
        //更新热门博客
        blogService.setHotBlogForRedis();
        logger.info("----------------------热门数据更新结束----------------------");
    }

    /***
     * 更新板块博客评论数量统计
     */
    @Async
    public void updateTotal(){
        logger.info("----------------------更新板块博客评论数量统计----------------------");
        for (Plate p: Plate.values()) {
            int plate = p.getIndex();
            Map map = new HashMap();
            Integer blogNums = blogService.countBlogsByPlate(plate);
            Integer commentNums = commentService.countCommentsForBlog(plate) + commentService.countCommentsForComment(plate);
            map.put("blogNums", blogNums);
            map.put("commentNums", commentNums);
            map.put("newNums", 0);
            mLabUserCountService.setTotalForRedis(plate,map);
        }
        logger.info("----------------------板块博客评论数量统计更新结束----------------------");
    }

}
