package com.lhs.musiclab.dao;

import com.lhs.musiclab.pojo.MoodSign;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MoodSignMapper {
    public Integer add(MoodSign moodSign);

    public List<MoodSign> list();

    public void delete(Integer id);

    public Integer update(MoodSign moodSign);

    public MoodSign get(Integer id);
}
