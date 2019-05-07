package com.lhs.musiclab.service;

import com.lhs.musiclab.pojo.MoodSign;

import java.util.List;

public interface MoodSignService {
    public Integer add(MoodSign moodSign);

    public List<MoodSign> list();

    public void delete(Integer id);

    public Integer update(MoodSign moodSign);

    public MoodSign get(Integer id);
}
