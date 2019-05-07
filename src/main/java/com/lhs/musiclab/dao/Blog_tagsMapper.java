package com.lhs.musiclab.dao;

import com.lhs.musiclab.pojo.Blog_tags;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface Blog_tagsMapper {
    public Integer add(Blog_tags blog_tags);

    public List<Blog_tags> list();

    public void delete(Integer id);

    public Integer update(Blog_tags blog_tags);

    public Blog_tags get(Integer id);
}
