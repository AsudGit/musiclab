package com.lhs.musiclab.service.serviceImpl;

import com.lhs.musiclab.dao.MLabUserCountMapper;
import com.lhs.musiclab.pojo.MLabUserCount;
import com.lhs.musiclab.service.MLabUserCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MLabUserCountServiceImpl implements MLabUserCountService {
    @Autowired
    private MLabUserCountMapper mLabUserCountMapper;
    @Override
    public Integer add(MLabUserCount mLabUserCount) {
        return mLabUserCountMapper.add(mLabUserCount);
    }

    @Override
    public List<MLabUserCount> list() {
        return mLabUserCountMapper.list();
    }

    @Override
    public void delete(Integer id) {
        mLabUserCountMapper.delete(id);
    }

    @Override
    public Integer update(MLabUserCount mLabUserCount) {
        return mLabUserCountMapper.update(mLabUserCount);
    }

    @Override
    public MLabUserCount get(Integer id) {
        return mLabUserCountMapper.get(id);
    }
}
