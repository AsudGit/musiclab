package com.lhs.musiclab.dao;

import com.lhs.musiclab.pojo.FansList;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface FansListMapper {
    public Integer add(FansList fansList);

    public List<FansList> list();

    public void delete(Integer id);

    public Integer update(FansList fansList);

    public FansList get(Integer id);
}
