package com.lhs.musiclab.service.serviceImpl;

import com.lhs.musiclab.dao.LikeSongsMapper;
import com.lhs.musiclab.pojo.LikeSongs;
import com.lhs.musiclab.service.LikeSongsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class LikeSongsServiceImpl implements LikeSongsService {
    @Autowired
    private LikeSongsMapper likeSongsMapper;
    @Override
    public Integer add(LikeSongs likeSongs) {
        return likeSongsMapper.add(likeSongs);
    }

    @Override
    public List<LikeSongs> list() {
        return likeSongsMapper.list();
    }

    @Override
    public void delete(Integer id) {
        likeSongsMapper.delete(id);
    }

    @Override
    public Integer update(LikeSongs likeSongs) {
        return likeSongsMapper.update(likeSongs);
    }

    @Override
    public LikeSongs get(Integer id) {
        return likeSongsMapper.get(id);
    }
}
