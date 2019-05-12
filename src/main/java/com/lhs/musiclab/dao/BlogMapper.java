package com.lhs.musiclab.dao;

import com.lhs.musiclab.pojo.Blog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
public interface BlogMapper {
    public Integer add(Blog blog);

    public List<Blog> list();

    public List<Blog> listByPlate(Integer plate);

    public void delete(Blog blog);

    public Integer update(Blog blog);

    public Blog get(Blog blog);
}
