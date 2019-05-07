package com.lhs.musiclab.service.serviceImpl;

import com.lhs.musiclab.dao.LikeBlogsMapper;
import com.lhs.musiclab.pojo.LikeBlogs;
import com.lhs.musiclab.service.LikeBlogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class LikeBlogsServiceImpl implements LikeBlogsService {
    @Autowired
    private LikeBlogsMapper likeBlogsMapper;
    @Override
    public Integer add(LikeBlogs likeBlogs) {
        return likeBlogsMapper.add(likeBlogs);
    }

    @Override
    public List<LikeBlogs> list() {
        return likeBlogsMapper.list();
    }

    @Override
    public void delete(Integer id) {
        likeBlogsMapper.delete(id);
    }

    @Override
    public Integer update(LikeBlogs likeBlogs) {
        return likeBlogsMapper.update(likeBlogs);
    }

    @Override
    public LikeBlogs get(Integer id) {
        return likeBlogsMapper.get(id);
    }
}
