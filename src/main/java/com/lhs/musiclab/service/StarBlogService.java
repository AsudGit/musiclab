package com.lhs.musiclab.service;

import com.lhs.musiclab.pojo.StarBlog;

import java.util.List;

public interface StarBlogService {
    public Integer add(StarBlog starBlog);

    public List<StarBlog> list();

    public void delete(Integer id);

    public Integer update(StarBlog starBlog);

    public StarBlog get(Integer id);
}
