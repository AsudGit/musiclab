package com.lhs.musiclab.service;

import com.lhs.musiclab.pojo.StarBlog;
import com.lhs.musiclab.pojo.StarBlog_status;

import java.util.List;
import java.util.Map;

public interface StarBlogService {
    public Integer add(StarBlog starBlog);

    public void setForRedis(StarBlog_status starBlog_status);

    public List<StarBlog> list();

    public void delete(String sbid);

    public Integer update(StarBlog starBlog);

    public StarBlog get(Integer id);

    public StarBlog_status get(String uid,String bid,Integer folder);

    public List<StarBlog_status> get(String uid,String bid);

    public List<StarBlog_status> get(String uid, Integer folder);
}
