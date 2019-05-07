package com.lhs.musiclab.dao;

import com.lhs.musiclab.pojo.MLabUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MLabUserMapper {
    public Integer add(MLabUser mLabUser);

    public List<MLabUser> list();

    public void delete(Integer id);

    public Integer update(MLabUser mLabUser);

    public MLabUser get(Integer id);
}
