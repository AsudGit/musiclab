package com.lhs.musiclab.dao;

import com.lhs.musiclab.pojo.LikeBlogs;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface LikeBlogsMapper {
    public Integer add(LikeBlogs likeBlogs);

    public List<LikeBlogs> list();

    public void delete(Integer id);

    public Integer update(LikeBlogs likeBlogs);

    public LikeBlogs get(Integer id);
}
