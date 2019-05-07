package com.lhs.musiclab.service.serviceImpl;

import com.lhs.musiclab.dao.BlogMapper;
import com.lhs.musiclab.pojo.Blog;
import com.lhs.musiclab.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogMapper blogMapper;
    @Override
    public Integer add(Blog blog) {
        return blogMapper.add(blog);
    }

    @Override
    public List<Blog> list() {
        return blogMapper.list();
    }

    @Override
    public void delete(Integer id) {
        blogMapper.delete(id);
    }

    @Override
    public Integer update(Blog blog) {
        return blogMapper.update(blog);
    }

    @Override
    public Blog get(Integer id) {
        return blogMapper.get(id);
    }
}
