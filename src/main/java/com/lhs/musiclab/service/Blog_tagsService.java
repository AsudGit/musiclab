package com.lhs.musiclab.service;

import com.lhs.musiclab.pojo.Blog_tags;

import java.util.List;

public interface Blog_tagsService {
    public Integer add(Blog_tags blog_tags);

    public List<Blog_tags> list();

    public void delete(Integer id);

    public Integer update(Blog_tags blog_tags);

    public Blog_tags get(Integer id);
}
