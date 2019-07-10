package com.lhs.musiclab.dao;

import com.lhs.musiclab.pojo.StarBlog;
import com.lhs.musiclab.pojo.StarBlog_status;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

public interface StarBlogMapper {
    public Integer add(StarBlog starBlog);

    public List<StarBlog> list();

    public void delete(String sbid);

    public Integer update(StarBlog starBlog);

    public StarBlog get(Integer id);

    public StarBlog_status get(StarBlog starBlog);

    public List<StarBlog_status> get(Map map);
}
