package com.lhs.musiclab.service;

import com.lhs.musiclab.pojo.Blog;

import java.util.List;

public interface BlogService {
    public Integer add(Blog blog);

    public List<Blog> list();

    public void delete(Integer id);

    public Integer update(Blog blog);

    public Blog get(Integer id);
}
