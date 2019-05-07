package com.lhs.musiclab.dao;

import com.lhs.musiclab.pojo.Tags;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TagsMapper {
    public Integer add(Tags tags);

    public List<Tags> list();

    public void delete(Integer id);

    public Integer update(Tags tags);

    public Tags get(Integer id);
}
