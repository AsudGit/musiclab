package com.lhs.musiclab.service;

import com.lhs.musiclab.pojo.MLabUserCount;

import java.util.Date;
import java.util.List;

public interface MLabUserCountService {
    public Integer add(String uid, Date date);

    public List<MLabUserCount> list();

    public void delete(Integer id);

    public Integer update(MLabUserCount mLabUserCount);

    public MLabUserCount get(String uid);

    public void countRecentlyLogin(String uid, Date recentlyLogin);

    public void countComments(String uid);

    public void countFans(String uid);

    public void countBlogs(String uid);
}
