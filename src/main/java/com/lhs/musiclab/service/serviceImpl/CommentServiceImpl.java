package com.lhs.musiclab.service.serviceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lhs.musiclab.dao.CommentMapper;
import com.lhs.musiclab.pojo.Comment;
import com.lhs.musiclab.pojo.CommentItem;
import com.lhs.musiclab.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@CacheConfig(cacheNames = "comment")
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    @CacheEvict(allEntries = true)
    public Integer add(Comment comment) {
        return commentMapper.add(comment);
    }

    @Override
    public List<CommentItem> list() {
        return commentMapper.list();
    }

    @Override
    @Cacheable
    public List<CommentItem> listByIdLimit(String id, Integer start, Integer size) {
        return commentMapper.listByIdLimit(id,start,size);
    }

    @Override
    public Integer countCommentsByUid(String uid) {
        return commentMapper.countCommentsByUid(uid);
    }

    @Override
    @Cacheable
    public PageInfo<CommentItem> list(Comment comment, Integer start, Integer size) {
        PageHelper.startPage(start, size);
        List<CommentItem> commentList = commentMapper.list(comment);
        PageInfo<CommentItem> pageInfo = new PageInfo<>(commentList);
        return pageInfo;
    }

    @Override
    public Integer countCommentsForBlog(Integer plate) {
        return commentMapper.countCommentsForBlog(plate);
    }

    @Override
    public Integer countCommentsForComment(Integer plate) {
        return commentMapper.countCommentsForComment(plate);
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
    @Cacheable
    public Comment get(Integer id) {
        return commentMapper.get(id);
    }
}
