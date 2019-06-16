package com.lhs.musiclab.service;

import com.lhs.musiclab.pojo.Tag;

import java.util.List;

public interface TagService {
    public Integer add(Tag tag);

    public List<Tag> list();

    public void delete(Integer id);

    public Integer update(Tag tag);

    public Tag get(Tag tag);
}
