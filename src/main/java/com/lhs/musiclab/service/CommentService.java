package com.lhs.musiclab.service;

import com.github.pagehelper.PageInfo;
import com.lhs.musiclab.pojo.Comment;
import com.lhs.musiclab.pojo.CommentItem;

import java.util.List;

public interface CommentService {
    public Integer add(Comment comment);

    public List<CommentItem> list();

    public List<CommentItem> listByIdLimit(String id,Integer start,Integer size);

    public PageInfo<CommentItem> list(Comment comment, Integer start, Integer size);

    public Integer countCommentsByUid(String uid);

    public Integer countCommentsForBlog(Integer plate);

    public Integer countCommentsForComment(Integer plate);

    public void delete(Integer id);

    public Integer update(Comment comment);

    public Comment get(Integer id);
}
