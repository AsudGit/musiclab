package com.lhs.musiclab.service;

import com.lhs.musiclab.pojo.LikeComments;

import java.util.List;

public interface LikeCommentsService {
    public Integer add(LikeComments likeComments);

    public List<LikeComments> list();

    public void delete(Integer id);

    public Integer update(LikeComments likeComments);

    public LikeComments get(Integer id);
}
