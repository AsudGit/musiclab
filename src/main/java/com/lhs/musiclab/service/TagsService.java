package com.lhs.musiclab.service;

import com.lhs.musiclab.pojo.Tags;

import java.util.List;

public interface TagsService {
    public Integer add(Tags tags);

    public List<Tags> list();

    public void delete(Integer id);

    public Integer update(Tags tags);

    public Tags get(Integer id);
}
