package com.lhs.musiclab.service;

import com.lhs.musiclab.pojo.LikeSongs;

import java.util.List;

public interface LikeSongsService {
    public Integer add(LikeSongs likeSongs);

    public List<LikeSongs> list();

    public void delete(Integer id);

    public Integer update(LikeSongs likeSongs);

    public LikeSongs get(Integer id);
}
