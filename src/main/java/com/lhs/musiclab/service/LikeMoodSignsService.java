package com.lhs.musiclab.service;

import com.lhs.musiclab.pojo.LikeMoodSigns;

import java.util.List;

public interface LikeMoodSignsService {
    public Integer add(LikeMoodSigns likeMoodSigns);

    public List<LikeMoodSigns> list();

    public void delete(Integer id);

    public Integer update(LikeMoodSigns likeMoodSigns);

    public LikeMoodSigns get(Integer id);
}
