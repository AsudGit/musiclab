package com.lhs.musiclab.dao;

import com.lhs.musiclab.pojo.MLabUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface MLabUserMapper {

    public Integer add(MLabUser mLabUser);

    public List<MLabUser> list();

    public void delete(String id);

    public Integer update(MLabUser mLabUser);

    public List<MLabUser> get(MLabUser mLabUser);

    public MLabUser match(MLabUser mLabUser);

    public List<MLabUser> matchOr(MLabUser mLabUser);
}
