package com.lhs.musiclab.service.serviceImpl;

import com.lhs.musiclab.dao.TagMapper;
import com.lhs.musiclab.pojo.Tag;
import com.lhs.musiclab.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@CacheConfig(cacheNames = "tag")
public class TagServiceImpl implements TagService {
    @Autowired
    private TagMapper tagMapper;

    @Override
    @CacheEvict(allEntries = true)
    public Integer add(Tag tag) {
        return tagMapper.add(tag);
    }

    @Override
    public List<Tag> list() {
        return tagMapper.list();
    }

    @Override
    public List<Tag> listByLimit(Integer start, Integer size) {
        return tagMapper.listByLimit(start,size);
    }

    @Override
    @Cacheable
    public List<Tag> list(String bid) {
        return tagMapper.list(bid);
    }

    @Override
    public void delete(Integer id) {
        tagMapper.delete(id);
    }

    @Override
    @CacheEvict(allEntries = true)
    public Integer update(String tid,Integer heat) {
        return tagMapper.update(tid,heat);
    }

    @Override
    public Tag get(Tag tag) {
        return tagMapper.get(tag);
    }
}
