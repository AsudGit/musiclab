package com.lhs.musiclab.service.serviceImpl;

import com.lhs.musiclab.dao.MoodSignMapper;
import com.lhs.musiclab.pojo.MoodSign;
import com.lhs.musiclab.service.MoodSignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MoodSignServiceImpl implements MoodSignService {
    @Autowired
    private MoodSignMapper moodSignMapper;
    @Override
    public Integer add(MoodSign moodSign) {
        return moodSignMapper.add(moodSign);
    }

    @Override
    public List<MoodSign> list() {
        return moodSignMapper.list();
    }

    @Override
    public void delete(Integer id) {
        moodSignMapper.delete(id);
    }

    @Override
    public Integer update(MoodSign moodSign) {
        return moodSignMapper.update(moodSign);
    }

    @Override
    public MoodSign get(Integer id) {
        return moodSignMapper.get(id);
    }
}
