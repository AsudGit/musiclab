package com.lhs.musiclab.dao;

import com.lhs.musiclab.pojo.StarBlog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
public interface StarBlogMapper {
    public Integer add(StarBlog starBlog);

    public List<StarBlog> list();

    public void delete(Integer id);

    public Integer update(StarBlog starBlog);

    public StarBlog get(Integer id);
}
