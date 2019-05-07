package com.lhs.musiclab.service;

import com.lhs.musiclab.pojo.MLabUserCount;

import java.util.List;

public interface MLabUserCountService {
    public Integer add(MLabUserCount mLabUserCount);

    public List<MLabUserCount> list();

    public void delete(Integer id);

    public Integer update(MLabUserCount mLabUserCount);

    public MLabUserCount get(Integer id);
}
