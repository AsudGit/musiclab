package com.lhs.musiclab.dao;

import com.lhs.musiclab.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CommentMapper {
    public Integer add(Comment comment);

    public List<Comment> list();

    public void delete(Integer id);

    public Integer update(Comment comment);

    public Comment get(Integer id);
}
