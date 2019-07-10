package com.lhs.musiclab.dao;

import com.lhs.musiclab.pojo.Blog;
import com.lhs.musiclab.pojo.BlogItem;
import com.lhs.musiclab.pojo.StarFolder;

import java.util.LinkedList;
import java.util.List;
public interface BlogMapper {
    public Integer add(Blog blog);

    public List<BlogItem> listForBlogItem();

    public Integer countBlogsByPlate(Integer plate);

    public Integer countBlogsByUid(String uid);

    public void delete(Blog blog);

    public Integer update(Blog blog);

    public List<BlogItem> get(Blog blog);
}
