package com.lhs.musiclab.dao;

import com.lhs.musiclab.pojo.LikeMoodSigns;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface LikeMoodSignsMapper {
    public Integer add(LikeMoodSigns likeMoodSigns);

    public List<LikeMoodSigns> list();

    public void delete(Integer id);

    public Integer update(LikeMoodSigns likeMoodSigns);

    public LikeMoodSigns get(Integer id);
}
