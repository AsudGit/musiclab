package com.lhs.musiclab.service;

import com.github.pagehelper.PageInfo;
import com.lhs.musiclab.pojo.Blog;
import com.lhs.musiclab.pojo.BlogItem;
import com.lhs.musiclab.pojo.StarFolder;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public interface BlogService {
    public List<BlogItem> listForBlogItem();

    public Integer countBlogsByPlate(Integer plate);

    public Integer countBlogsByUid(String uid);

    public List<BlogItem> getHotBlog();

    public void setHotBlogForRedis();

    public Integer add(Blog blog);

    public void delete(Blog blog);

    public Integer update(Blog blog);

    public List<BlogItem> get(Blog blog);

    public PageInfo<BlogItem> get(Blog blog,Integer start,Integer size);
}
