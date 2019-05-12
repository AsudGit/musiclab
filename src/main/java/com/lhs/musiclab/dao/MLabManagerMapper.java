package com.lhs.musiclab.dao;

import com.lhs.musiclab.pojo.MLabManager;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
public interface MLabManagerMapper {
    public Integer add(MLabManager mLabManager);

    public List<MLabManager> list();

    public void delete(Integer id);

    public Integer update(MLabManager mLabManager);

    public MLabManager get(Integer id);
}
