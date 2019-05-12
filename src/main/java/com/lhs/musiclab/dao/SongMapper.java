package com.lhs.musiclab.dao;

import com.lhs.musiclab.pojo.Song;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
public interface SongMapper {
    public Integer add(Song song);

    public List<Song> list();

    public void delete(Integer id);

    public Integer update(Song song);

    public Song get(Integer id);
}
