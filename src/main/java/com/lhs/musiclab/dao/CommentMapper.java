package com.lhs.musiclab.dao;

import com.lhs.musiclab.pojo.Comment;
import com.lhs.musiclab.pojo.CommentItem;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
public interface CommentMapper {
    public Integer add(Comment comment);

    public List<CommentItem> list();

    public List<CommentItem> listByIdLimit(String id,Integer start,Integer size);

    public List<CommentItem> list(String id);

    public Integer countCommentsByUid(String uid);

    public Integer countCommentsForBlog(Integer plate);

    public Integer countCommentsForComment(Integer plate);

    public void delete(Integer id);

    public Integer update(Comment comment);

    public Comment get(Integer id);
}
