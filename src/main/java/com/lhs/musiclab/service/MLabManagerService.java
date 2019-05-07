package com.lhs.musiclab.service;

import com.lhs.musiclab.pojo.MLabManager;

import java.util.List;

public interface MLabManagerService {
    public Integer add(MLabManager mLabManager);

    public List<MLabManager> list();

    public void delete(Integer id);

    public Integer update(MLabManager mLabManager);

    public MLabManager get(Integer id);
}
