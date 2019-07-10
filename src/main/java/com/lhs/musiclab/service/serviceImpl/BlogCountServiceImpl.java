package com.lhs.musiclab.service.serviceImpl;

import com.lhs.musiclab.dao.BlogCountMapper;
import com.lhs.musiclab.pojo.BlogCount;
import com.lhs.musiclab.service.BlogCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class BlogCountServiceImpl implements BlogCountService {
    @Autowired
    private BlogCountMapper blogCountMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public Integer add(BlogCount blogCount) {
        return blogCountMapper.add(blogCount);
    }

    @Override
    public void delete(Integer id) {
        blogCountMapper.delete(id);
    }

    @Override
    public void setForRedis(BlogCount blogCount){
        redisTemplate.opsForValue().set("count:blog:"+blogCount.getBid(),blogCount);
    }

    @Override
    public void setViewHistoryForRedis(String bid, String ip) {
        redisTemplate.opsForValue().set("view:"+bid+"-"+ip,1,1, TimeUnit.HOURS);
    }

    @Override
    public Integer getViewHistoryForRedis(String bid, String ip) {
        return (Integer) redisTemplate.opsForValue().get("view:"+bid+"-"+ip);
    }

    @Override
    public Integer update(BlogCount blogCount) {
        return blogCountMapper.update(blogCount);
    }

    @Override
    public BlogCount get(String bid) {
        BlogCount blogCount = (BlogCount) redisTemplate.opsForValue().get("count:blog:" + bid);
        if (blogCount==null){
            blogCount = blogCountMapper.get(bid);
            setForRedis(blogCount);
        }
        return blogCount;
    }
}
