package com.lhs.musiclab.service;

import com.lhs.musiclab.pojo.FansList;

import java.util.List;

public interface FansListService {
    public Integer add(FansList fansList);

    public List<FansList> list();

    public void delete(Integer id);

    public Integer update(FansList fansList);

    public FansList get(Integer id);
}
