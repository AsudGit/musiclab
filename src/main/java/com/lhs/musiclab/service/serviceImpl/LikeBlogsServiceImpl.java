package com.lhs.musiclab.service.serviceImpl;

import com.lhs.musiclab.dao.LikeBlogsMapper;
import com.lhs.musiclab.pojo.LikeBlogs;
import com.lhs.musiclab.pojo.LikeBlogs_status;
import com.lhs.musiclab.service.LikeBlogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LikeBlogsServiceImpl implements LikeBlogsService {
    @Autowired
    private LikeBlogsMapper likeBlogsMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public Integer add(LikeBlogs likeBlogs) {
        return likeBlogsMapper.add(likeBlogs);
    }

    @Override
    public List<LikeBlogs> list() {
        return likeBlogsMapper.list();
    }

    @Override
    public void delete(String lid) {
        likeBlogsMapper.delete(lid);
    }

    public void setForRedis(LikeBlogs_status likeBlogs_status) {
        String key = "like:blog:uid=" + likeBlogs_status.getLikeBlogs().getUid() + ",bid=" + likeBlogs_status.getLikeBlogs().getBid();
        redisTemplate.opsForValue().set(key,likeBlogs_status);
    }

    //1表示新点赞需要插入数据库，3表示旧点赞已存在于数据库，0表示没有点赞，2表示取消点赞需要删除数据库
    @Override
    public LikeBlogs_status get(String uid, String bid) {
        LikeBlogs likeBlogs = new LikeBlogs();
        likeBlogs.setUid(uid);
        likeBlogs.setBid(bid);
        LikeBlogs_status likeBlogsStatus = new LikeBlogs_status();
        Object o = redisTemplate.opsForValue().get("like:blog:uid=" + uid + ",bid=" + bid);
        if (o!=null){
            likeBlogsStatus = (LikeBlogs_status) o;
        }else {
            List<LikeBlogs_status> likeBlogs_statuses = likeBlogsMapper.get(likeBlogs);
            if(likeBlogs_statuses!=null && !likeBlogs_statuses.isEmpty()){
                likeBlogs_statuses.get(0).setStatus(3);
                likeBlogsStatus = likeBlogs_statuses.get(0);
            }else {
                likeBlogsStatus.setLikeBlogs(likeBlogs);
                likeBlogsStatus.setStatus(0);
            }
            setForRedis(likeBlogsStatus);
        }
        return likeBlogsStatus;
    }

    @Override
    public List<LikeBlogs_status> get(String uid) {
        LikeBlogs likeBlogs = new LikeBlogs();
        likeBlogs.setUid(uid);
        List<LikeBlogs_status> list = new ArrayList<>();
        Set keys = redisTemplate.keys("like:blog:uid=" + uid + "*");
        if (keys.size()!=0||keys!=null){
            Iterator iterator = keys.iterator();
            while (iterator.hasNext()){
                list.add((LikeBlogs_status) redisTemplate.opsForValue().get(iterator.next()));
            }
        }else {
            list = likeBlogsMapper.get(likeBlogs);
            if (list!=null && !list.isEmpty()){
                for (LikeBlogs_status l :list) {
                    l.setStatus(3);
                    setForRedis(l);
                }
            }
        }
        return list;
    }
}
