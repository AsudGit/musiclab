package com.lhs.musiclab.service.serviceImpl;

import com.lhs.musiclab.dao.Blog_tagMapper;
import com.lhs.musiclab.pojo.Blog_tag;
import com.lhs.musiclab.service.Blog_tagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class Blog_tagServiceImpl implements Blog_tagService {
    @Autowired
    private Blog_tagMapper blog_tagMapper;
    @Override
    public Integer add(Blog_tag blog_tag) {
        return blog_tagMapper.add(blog_tag);
    }

    @Override
    public List<Blog_tag> list() {
        return blog_tagMapper.list();
    }

    @Override
    public void delete(Integer id) {
        blog_tagMapper.delete(id);
    }

    @Override
    public Integer update(Blog_tag blog_tag) {
        return blog_tagMapper.update(blog_tag);
    }

    @Override
    public Blog_tag get(Integer id) {
        return blog_tagMapper.get(id);
    }
}
