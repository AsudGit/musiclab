package com.lhs.musiclab.service.serviceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lhs.musiclab.dao.BlogMapper;
import com.lhs.musiclab.pojo.Blog;
import com.lhs.musiclab.pojo.BlogItem;
import com.lhs.musiclab.service.BlogService;
import com.lhs.musiclab.utils.QuickSort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;

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
    public List<BlogItem> getHotBlog() {
        List<BlogItem> hotBlogList = (List<BlogItem>) redisTemplate.opsForValue().get("hot:blog");
        if (hotBlogList==null) {//redis中无热门则重新生成
            setHotBlogForRedis();
            hotBlogList = (List<BlogItem>) redisTemplate.opsForValue().get("hot:blog");
        }
        return hotBlogList;
    }

    @Override
    public void setHotBlogForRedis(){
        List<BlogItem> hotBlogList = blogMapper.listForBlogItem();
        QuickSort.enableDESC();
        QuickSort.sortForBlogItem(hotBlogList, 0, hotBlogList.size() - 1);
        if (hotBlogList.size() > 5) {
            hotBlogList = new ArrayList<>(hotBlogList.subList(0,5));
        }
        redisTemplate.opsForValue().set("hot:blog",hotBlogList);
    }


    @Override
    public List<BlogItem> listForBlogItem() {
        return blogMapper.listForBlogItem();
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
