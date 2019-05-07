package com.lhs.musiclab.service.serviceImpl;

import com.lhs.musiclab.dao.SongMapper;
import com.lhs.musiclab.pojo.Song;
import com.lhs.musiclab.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SongServiceImpl implements SongService {
    @Autowired
    private SongMapper songMapper;
    @Override
    public Integer add(Song song) {
        return songMapper.add(song);
    }

    @Override
    public List<Song> list() {
        return songMapper.list();
    }

    @Override
    public void delete(Integer id) {
        songMapper.delete(id);
    }

    @Override
    public Integer update(Song song) {
        return songMapper.update(song);
    }

    @Override
    public Song get(Integer id) {
        return songMapper.get(id);
    }
}
