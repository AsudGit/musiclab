package com.lhs.musiclab.dao;

import com.lhs.musiclab.pojo.Blog_tag;

import java.util.List;
public interface Blog_tagMapper {
    public Integer add(Blog_tag blog_tag);

    public void delete(Integer id);

    public Blog_tag get(Integer id);
}
