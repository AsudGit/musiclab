package com.lhs.musiclab.service;

import com.lhs.musiclab.pojo.Blog;

import java.util.List;

public interface BlogService {
    public Integer add(Blog blog);

    public List<Blog> list();

    public List<Blog> listByPlate(Integer plate);

    public void delete(Blog blog);

    public Integer update(Blog blog);

    public Blog get(Blog blog);
}
