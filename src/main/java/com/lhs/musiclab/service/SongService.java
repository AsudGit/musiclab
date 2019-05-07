package com.lhs.musiclab.service;

import com.lhs.musiclab.pojo.Song;

import java.util.List;

public interface SongService {
    public Integer add(Song song);

    public List<Song> list();

    public void delete(Integer id);

    public Integer update(Song song);

    public Song get(Integer id);
}
