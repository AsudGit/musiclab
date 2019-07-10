package com.lhs.musiclab.service;

import com.lhs.musiclab.pojo.LikeBlogs;
import com.lhs.musiclab.pojo.LikeBlogs_status;

import java.util.List;
import java.util.Map;

public interface LikeBlogsService {
    public Integer add(LikeBlogs likeBlogs);

    public List<LikeBlogs> list();

    public void delete(String lid);

    public void setForRedis(LikeBlogs_status likeBlogs_status);

    public LikeBlogs_status get(String uid, String bid);

    public List<LikeBlogs_status> get(String uid);
}
