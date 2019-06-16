package com.lhs.musiclab.service.serviceImpl;

import com.lhs.musiclab.dao.BlogMapper;
import com.lhs.musiclab.pojo.Blog;
import com.lhs.musiclab.pojo.BlogItem;
import com.lhs.musiclab.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
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
    public LinkedList<BlogItem> linkedlist() {
        return blogMapper.linkedlist();
    }

    @Override
    public List<BlogItem> listByPlate(Integer plate) {
        return blogMapper.listByPlate(plate);
    }

    @Override
    public void delete(Blog blog) {
        blogMapper.delete(blog);
    }

    @Override
    public Integer update(Blog blog) {
        return blogMapper.update(blog);
    }

    @Override
    public List<BlogItem> get(Blog blog) {
        return blogMapper.get(blog);
    }
}
