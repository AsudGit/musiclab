package com.lhs.musiclab.dao;

import com.lhs.musiclab.pojo.LikeComments;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface LikeCommentsMapper {
    public Integer add(LikeComments likeComments);

    public List<LikeComments> list();

    public void delete(Integer id);

    public Integer update(LikeComments likeComments);

    public LikeComments get(Integer id);
}
