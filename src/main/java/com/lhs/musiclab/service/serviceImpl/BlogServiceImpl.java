package com.lhs.musiclab.service.serviceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lhs.musiclab.dao.BlogMapper;
import com.lhs.musiclab.pojo.Blog;
import com.lhs.musiclab.pojo.BlogItem;
import com.lhs.musiclab.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
@CacheConfig(cacheNames = "blog")
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    @CacheEvict(allEntries = true)
    public Integer add(Blog blog) {
        return blogMapper.add(blog);
    }

    @Override
    public Integer countBlogsByPlate(Integer plate) {
        return blogMapper.countBlogsByPlate(plate);
    }

    @Override
    public Integer countBlogsByUid(String uid) {
        return blogMapper.countBlogsByUid(uid);
    }

    @Override
    @Async
    public void newsIncr(Integer plate) {
        Map map=(HashMap)redisTemplate.opsForValue().get("total:"+plate);
        map.put("newNums",(int)map.get("newNums")+1);
        map.put("blogNums",(int)map.get("blogNums")+1);
        redisTemplate.opsForValue().set("total:"+plate,map);
    }


    @Override
    public List<Blog> list() {
        return blogMapper.list();
    }

    @Override
    public LinkedList<BlogItem> linkedlist() {
        return blogMapper.linkedlist();
    }

    @Override
    public void delete(Blog blog) {
        blogMapper.delete(blog);
    }

    @Override
    public Integer update(Blog blog) {
        return blogMapper.update(blog);
    }

    @Override
    @Cacheable
    public List<BlogItem> get(Blog blog){
        return blogMapper.get(blog);
    }

    @Override
    @Cacheable
    public PageInfo<BlogItem> get(Blog blog,Integer start,Integer size){
        PageHelper.startPage(start, size);
        List<BlogItem> blogItems = blogMapper.get(blog);
        PageInfo<BlogItem> pageInfo = new PageInfo<>(blogItems);
        return pageInfo;
    }
}
