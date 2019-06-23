package com.lhs.musiclab.component;

import com.lhs.musiclab.enums.Plate;
import com.lhs.musiclab.pojo.BlogItem;
import com.lhs.musiclab.pojo.MLabUserCount;
import com.lhs.musiclab.service.BlogService;
import com.lhs.musiclab.service.CommentService;
import com.lhs.musiclab.service.MLabUserCountService;
import com.lhs.musiclab.service.TagService;
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
    private MLabUserCountService mLabUserCountService;
    @Autowired
    private TagService tagService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private RedisTemplate redisTemplate;
    private Logger logger = LoggerFactory.getLogger(getClass());

    /***
     * 凌晨6点同步redis与数据库数据，更新redis热门数据
     */
    @Scheduled(cron = "0 0 6 * * ?")
    public void update(){
        logger.info("----------------------发布数据同步任务----------------------");
        updateCount();
        updateHot();
        updateTotal();
    }

    /***
     * 同步redis与数据库的mlabusercount表
     */
    @Async
    public void updateCount(){
        logger.info("----------------------同步mlabusercount表----------------------");
        Set<String> keys = redisTemplate.keys("count:*");
        Iterator<String> it= keys.iterator();
        while (it.hasNext()){
            String uid = it.next().substring(6);
            Integer blogNums = blogService.countBlogsByUid(uid);
            Integer commentNums = commentService.countCommentsByUid(uid);
            MLabUserCount mLabUserCount= (MLabUserCount) redisTemplate.opsForValue().get("count:"+uid);
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
     * 更新热门数据
     */
    @Async
    public void updateHot(){
        logger.info("----------------------更新热门数据----------------------");
        //更新热词
        redisTemplate.opsForValue().set("hot:tag",tagService.listByLimit(0, 20));
        //更新热门博客
        LinkedList<BlogItem> linkedlist = blogService.linkedlist();
        QuickSort.enableDESC();
        QuickSort.linkedlistSort(linkedlist, 0, linkedlist.size() - 1);
        List<BlogItem> hotBlogList = new ArrayList<>();
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
            redisTemplate.opsForValue().set("total:"+plate,map);
        }
        logger.info("----------------------板块博客评论数量统计更新结束----------------------");
    }

}
