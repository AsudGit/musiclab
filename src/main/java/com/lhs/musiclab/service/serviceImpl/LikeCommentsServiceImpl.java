package com.lhs.musiclab.service.serviceImpl;

import com.lhs.musiclab.dao.LikeCommentsMapper;
import com.lhs.musiclab.pojo.LikeComments;
import com.lhs.musiclab.service.LikeCommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class LikeCommentsServiceImpl implements LikeCommentsService {
    @Autowired
    private LikeCommentsMapper likeCommentsMapper;
    @Override
    public Integer add(LikeComments likeComments) {
        return likeCommentsMapper.add(likeComments);
    }

    @Override
    public List<LikeComments> list() {
        return likeCommentsMapper.list();
    }

    @Override
    public void delete(Integer id) {
        likeCommentsMapper.delete(id);
    }

    @Override
    public Integer update(LikeComments likeComments) {
        return likeCommentsMapper.update(likeComments);
    }

    @Override
    public LikeComments get(Integer id) {
        return likeCommentsMapper.get(id);
    }
}
