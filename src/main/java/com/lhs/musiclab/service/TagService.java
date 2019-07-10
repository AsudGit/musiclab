package com.lhs.musiclab.service;

import com.lhs.musiclab.pojo.Tag;

import java.util.List;

public interface TagService {
    public Integer add(Tag tag);

    public List<Tag> list(String bid);

    public List<Tag> list();

    public List<Tag> listByLimit(Integer start, Integer size);

    public void delete(Integer id);

    public Integer update(String tid,Integer heat);

    public Tag get(Tag tag);

    public List<Tag> getHotTag(Integer start,Integer size);

    public void setHotTagForRedis(Integer start, Integer size);
}
