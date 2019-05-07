package com.lhs.musiclab.service.serviceImpl;

import com.lhs.musiclab.dao.Blog_tagsMapper;
import com.lhs.musiclab.pojo.Blog_tags;
import com.lhs.musiclab.service.Blog_tagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class Blog_tagsServiceImpl implements Blog_tagsService {
    @Autowired
    private Blog_tagsMapper blog_tagsMapper;
    @Override
    public Integer add(Blog_tags blog_tags) {
        return blog_tagsMapper.add(blog_tags);
    }

    @Override
    public List<Blog_tags> list() {
        return blog_tagsMapper.list();
    }

    @Override
    public void delete(Integer id) {
        blog_tagsMapper.delete(id);
    }

    @Override
    public Integer update(Blog_tags blog_tags) {
        return blog_tagsMapper.update(blog_tags);
    }

    @Override
    public Blog_tags get(Integer id) {
        return blog_tagsMapper.get(id);
    }
}
