package com.lhs.musiclab.dao;

import com.lhs.musiclab.pojo.Blog;
import com.lhs.musiclab.pojo.BlogItem;

import java.util.LinkedList;
import java.util.List;
public interface BlogMapper {
    public Integer add(Blog blog);

    public List<Blog> list();

    public LinkedList<BlogItem> linkedlist();

    public Integer countBlogsByPlate(Integer plate);

    public Integer countBlogsByUid(String uid);

    public void delete(Blog blog);

    public Integer update(Blog blog);

    public List<BlogItem> get(Blog blog);
}
