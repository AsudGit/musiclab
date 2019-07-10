package com.lhs.musiclab.dao;

import com.lhs.musiclab.pojo.BlogCount;

import java.util.List;

public interface BlogCountMapper {
    public Integer add(BlogCount blogCount);

    public void delete(Integer id);

    public Integer update(BlogCount blogCount);

    public BlogCount get(String bid);
}
