package com.lhs.musiclab.service.serviceImpl;

import com.lhs.musiclab.dao.MLabManagerMapper;
import com.lhs.musiclab.pojo.MLabManager;
import com.lhs.musiclab.service.MLabManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MLabManagerServiceImpl implements MLabManagerService {
    @Autowired
    private MLabManagerMapper mLabManagerMapper;
    @Override
    public Integer add(MLabManager mLabManager) {
        return mLabManagerMapper.add(mLabManager);
    }

    @Override
    public List<MLabManager> list() {
        return mLabManagerMapper.list();
    }

    @Override
    public void delete(Integer id) {
        mLabManagerMapper.delete(id);
    }

    @Override
    public Integer update(MLabManager mLabManager) {
        return mLabManagerMapper.update(mLabManager);
    }

    @Override
    public MLabManager get(Integer id) {
        return mLabManagerMapper.get(id);
    }
}
