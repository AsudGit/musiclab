package com.lhs.musiclab.service.serviceImpl;

import com.lhs.musiclab.dao.TagsMapper;
import com.lhs.musiclab.pojo.Tags;
import com.lhs.musiclab.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TagsServiceImpl implements TagsService {
    @Autowired
    private TagsMapper tagsMapper;
    @Override
    public Integer add(Tags tags) {
        return tagsMapper.add(tags);
    }

    @Override
    public List<Tags> list() {
        return tagsMapper.list();
    }

    @Override
    public void delete(Integer id) {
        tagsMapper.delete(id);
    }

    @Override
    public Integer update(Tags tags) {
        return tagsMapper.update(tags);
    }

    @Override
    public Tags get(Integer id) {
        return tagsMapper.get(id);
    }
}
