package com.lhs.musiclab.dao;

import com.lhs.musiclab.pojo.MLabUserCount;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MLabUserCountMapper {
    public Integer add(MLabUserCount mLabUserCount);

    public List<MLabUserCount> list();

    public void delete(Integer id);

    public Integer update(MLabUserCount mLabUserCount);

    public MLabUserCount get(Integer id);
}
