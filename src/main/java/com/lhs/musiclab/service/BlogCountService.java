package com.lhs.musiclab.service;

import com.lhs.musiclab.pojo.BlogCount;

import java.util.List;

public interface BlogCountService {
    public Integer add(BlogCount blogCount);

    public void delete(Integer id);

    public void setForRedis(BlogCount blogCount);

    public void setViewHistoryForRedis(String bid,String ip);

    public Integer getViewHistoryForRedis(String bid, String ip);

    public Integer update(BlogCount blogCount);

    public BlogCount get(String bid);
}
