package com.lhs.musiclab.dao;

import com.lhs.musiclab.pojo.MLabUserCount;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
public interface MLabUserCountMapper {

    public Integer add(String uid, Date date);

    public List<MLabUserCount> list();

    public void delete(Integer id);

    public Integer update(MLabUserCount mLabUserCount);

    public MLabUserCount get(String uid);
}
