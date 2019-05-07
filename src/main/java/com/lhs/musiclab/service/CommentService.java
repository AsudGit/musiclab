package com.lhs.musiclab.service;

import com.lhs.musiclab.pojo.Comment;

import java.util.List;

public interface CommentService {
    public Integer add(Comment comment);

    public List<Comment> list();

    public void delete(Integer id);

    public Integer update(Comment comment);

    public Comment get(Integer id);
}
