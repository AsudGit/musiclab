package com.lhs.musiclab.service.serviceImpl;

import com.lhs.musiclab.dao.LikeMoodSignsMapper;
import com.lhs.musiclab.pojo.LikeMoodSigns;
import com.lhs.musiclab.service.LikeMoodSignsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class LikeMoodSignsServiceImpl implements LikeMoodSignsService {
    @Autowired
    private LikeMoodSignsMapper likeMoodSignsMapper;
    @Override
    public Integer add(LikeMoodSigns likeMoodSigns) {
        return likeMoodSignsMapper.add(likeMoodSigns);
    }

    @Override
    public List<LikeMoodSigns> list() {
        return likeMoodSignsMapper.list();
    }

    @Override
    public void delete(Integer id) {
        likeMoodSignsMapper.delete(id);
    }

    @Override
    public Integer update(LikeMoodSigns likeMoodSigns) {
        return likeMoodSignsMapper.update(likeMoodSigns);
    }

    @Override
    public LikeMoodSigns get(Integer id) {
        return likeMoodSignsMapper.get(id);
    }
}
