package com.lhs.musiclab.dao;

import com.lhs.musiclab.pojo.Tag;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;
public interface TagMapper {
    public Integer add(Tag tag);

    public List<Tag> list();

    public List<Tag> listByLimit(Integer start,Integer size);

    public List<Tag> list(String bid);

    public void delete(Integer id);

    public Integer update(String tid,Integer heat);

    public Tag get(Tag tag);
}
