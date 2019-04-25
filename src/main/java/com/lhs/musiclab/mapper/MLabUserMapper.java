package com.lhs.musiclab.mapper;

import com.lhs.musiclab.pojo.MLabUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MLabUserMapper {
    public Integer add(MLabUser mLabUser);

    public List<MLabUser> list();

    public void delete(Integer id);

    public Integer update(MLabUser mLabUser);

    public MLabUser get(Integer id);
}
