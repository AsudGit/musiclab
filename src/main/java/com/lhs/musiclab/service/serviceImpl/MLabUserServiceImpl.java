package com.lhs.musiclab.service.serviceImpl;

import com.lhs.musiclab.mapper.MLabUserMapper;
import com.lhs.musiclab.pojo.MLabUser;
import com.lhs.musiclab.service.MLabUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MLabUserServiceImpl implements MLabUserService {
    @Autowired
    private MLabUserMapper mLabUserMapper;

    @Override
    public Integer add(MLabUser mLabUser) {
        return mLabUserMapper.add(mLabUser);
    }

    @Override
    public List<MLabUser> list() {
        return mLabUserMapper.list();
    }

    @Override
    public void delete(Integer id) {
        mLabUserMapper.delete(id);
    }

    @Override
    public Integer update(MLabUser mLabUser) {
        return mLabUserMapper.update(mLabUser);
    }

    @Override
    public MLabUser get(Integer id) {
        return mLabUserMapper.get(id);
    }
}
