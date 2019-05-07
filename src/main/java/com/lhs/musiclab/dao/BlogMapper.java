package com.lhs.musiclab.dao;

import com.lhs.musiclab.pojo.Blog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BlogMapper {
    public Integer add(Blog blog);

    public List<Blog> list();

    public void delete(Integer id);

    public Integer update(Blog blog);

    public Blog get(Integer id);
}
