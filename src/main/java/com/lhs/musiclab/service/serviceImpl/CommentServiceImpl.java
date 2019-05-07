package com.lhs.musiclab.service.serviceImpl;

import com.lhs.musiclab.dao.CommentMapper;
import com.lhs.musiclab.pojo.Comment;
import com.lhs.musiclab.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Override
    public Integer add(Comment comment) {
        return commentMapper.add(comment);
    }

    @Override
    public List<Comment> list() {
        return commentMapper.list();
    }

    @Override
    public void delete(Integer id) {
        commentMapper.delete(id);
    }

    @Override
    public Integer update(Comment comment) {
        return commentMapper.update(comment);
    }

    @Override
    public Comment get(Integer id) {
        return commentMapper.get(id);
    }
}
