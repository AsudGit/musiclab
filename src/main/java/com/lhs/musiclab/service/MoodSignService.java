package com.lhs.musiclab.service;

import com.github.pagehelper.PageInfo;
import com.lhs.musiclab.pojo.MoodSign;

import java.util.List;

public interface MoodSignService {
    public Integer add(MoodSign moodSign);

    public List<MoodSign> list();

    public void delete(Integer id);

    public Integer update(MoodSign moodSign);

    public PageInfo<MoodSign> get(String uid, Integer start, Integer size);
}
