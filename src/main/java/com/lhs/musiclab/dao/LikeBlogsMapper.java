package com.lhs.musiclab.dao;

import com.lhs.musiclab.pojo.LikeBlogs;
import com.lhs.musiclab.pojo.LikeBlogs_status;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

public interface LikeBlogsMapper {
    public Integer add(LikeBlogs likeBlogs);

    public List<LikeBlogs> list();

    public void delete(String lid);

    public List<LikeBlogs_status> get(LikeBlogs likeBlogs);
}
