package com.lhs.musiclab.service;

import com.lhs.musiclab.pojo.MLabUser;

import java.util.List;

public interface MLabUserService {
    public Integer add(MLabUser mLabUser);

    public List<MLabUser> list();

    public void delete(String id);

    public Integer update(MLabUser mLabUser);

    public List<MLabUser> get(MLabUser mLabUser);

    public MLabUser match(MLabUser mLabUser);

    public List<MLabUser> matchOr(MLabUser mLabUser);
}
