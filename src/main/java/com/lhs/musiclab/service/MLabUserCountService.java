package com.lhs.musiclab.service;

import com.lhs.musiclab.pojo.MLabUserCount;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface MLabUserCountService {
    public Integer add(String uid, Date date);

    public List<MLabUserCount> list();

    public void delete(Integer id);

    public Integer update(MLabUserCount mLabUserCount);

    public MLabUserCount get(String uid);

    public void updateRecentlyLoginForRedis(String uid, Date recentlyLogin);

    public void updateForRedis(String uid, MLabUserCount mLabUserCount);

    public Map getTotalForRedis(Integer plate);

    public void setTotalForRedis(Integer plate, Map total);
}
