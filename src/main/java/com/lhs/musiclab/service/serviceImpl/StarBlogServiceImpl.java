package com.lhs.musiclab.service.serviceImpl;

import com.lhs.musiclab.dao.StarBlogMapper;
import com.lhs.musiclab.pojo.StarBlog;
import com.lhs.musiclab.service.StarBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class StarBlogServiceImpl implements StarBlogService {
    @Autowired
    private StarBlogMapper starBlogMapper;
    @Override
    public Integer add(StarBlog starBlog) {
        return starBlogMapper.add(starBlog);
    }

    @Override
    public List<StarBlog> list() {
        return starBlogMapper.list();
    }

    @Override
    public void delete(Integer id) {
        starBlogMapper.delete(id);
    }

    @Override
    public Integer update(StarBlog starBlog) {
        return starBlogMapper.update(starBlog);
    }

    @Override
    public StarBlog get(Integer id) {
        return starBlogMapper.get(id);
    }
}
