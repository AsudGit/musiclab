package com.lhs.musiclab.service;

import com.lhs.musiclab.pojo.LikeBlogs;

import java.util.List;

public interface LikeBlogsService {
    public Integer add(LikeBlogs likeBlogs);

    public List<LikeBlogs> list();

    public void delete(Integer id);

    public Integer update(LikeBlogs likeBlogs);

    public LikeBlogs get(Integer id);
}
