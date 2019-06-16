package com.lhs.musiclab.service.serviceImpl;

import com.lhs.musiclab.dao.TagMapper;
import com.lhs.musiclab.pojo.Tag;
import com.lhs.musiclab.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagMapper tagMapper;
    @Override
    public Integer add(Tag tag) {
        return tagMapper.add(tag);
    }

    @Override
    public List<Tag> list() {
        return tagMapper.list();
    }

    @Override
    public void delete(Integer id) {
        tagMapper.delete(id);
    }

    @Override
    public Integer update(Tag tag) {
        return tagMapper.update(tag);
    }

    @Override
    public Tag get(Tag tag) {
        return tagMapper.get(tag);
    }
}
