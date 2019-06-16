package com.lhs.musiclab.service;

import com.lhs.musiclab.pojo.Blog;
import com.lhs.musiclab.pojo.BlogItem;

import java.util.LinkedList;
import java.util.List;

public interface BlogService {
    public Integer add(Blog blog);

    public List<Blog> list();

    public LinkedList<BlogItem> linkedlist();

    public List<BlogItem> listByPlate(Integer plate);

    public void delete(Blog blog);

    public Integer update(Blog blog);

    public List<BlogItem> get(Blog blog);
}
