package com.lhs.musiclab.dao;

import com.lhs.musiclab.pojo.LikeSongs;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface LikeSongsMapper {
    public Integer add(LikeSongs likeSongs);

    public List<LikeSongs> list();

    public void delete(Integer id);

    public Integer update(LikeSongs likeSongs);

    public LikeSongs get(Integer id);
}
