package com.lhs.musiclab.service.serviceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lhs.musiclab.dao.MoodSignMapper;
import com.lhs.musiclab.pojo.MoodSign;
import com.lhs.musiclab.service.MoodSignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@CacheConfig(cacheNames = "sign")
public class MoodSignServiceImpl implements MoodSignService {
    @Autowired
    private MoodSignMapper moodSignMapper;

    @Override
    @CacheEvict(allEntries = true)
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
    @Cacheable
    public PageInfo<MoodSign> listByUid(String uid,Integer start,Integer size) {
        PageHelper.startPage(start, size);
        List<MoodSign> moodSigns = moodSignMapper.listByUid(uid);
        PageInfo<MoodSign> pageInfo = new PageInfo<>(moodSigns);
        return pageInfo;
    }

    @Override
    @Cacheable
    public MoodSign getByUid(String uid) {
        return moodSignMapper.getByUid(uid);
    }
}
