package com.lhs.musiclab.service;

import com.lhs.musiclab.pojo.MLabUser;

import java.util.List;

public interface MLabUserService {
    public Integer add(MLabUser mLabUser);

    public List<MLabUser> list();

    public void delete(Integer id);

    public Integer update(MLabUser mLabUser);

    public MLabUser get(Integer id);
}
