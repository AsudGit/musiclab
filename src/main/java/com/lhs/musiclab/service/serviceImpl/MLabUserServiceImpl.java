package com.lhs.musiclab.service.serviceImpl;

import com.lhs.musiclab.dao.MLabUserMapper;
import com.lhs.musiclab.pojo.MLabUser;
import com.lhs.musiclab.service.MLabUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = "mlabUser")
public class MLabUserServiceImpl implements MLabUserService {
    @Autowired
    private MLabUserMapper mLabUserMapper;

    @Override
    @CacheEvict(allEntries = true)
    public Integer add(MLabUser mLabUser) {
        return mLabUserMapper.add(mLabUser);
    }

    @Override
    @Cacheable(value = "mlabUser",keyGenerator = "myKeyGenerator")
    public List<MLabUser> list() {
        return mLabUserMapper.list();
    }

    @Override
    @CacheEvict(allEntries = true)
    public void delete(String id) {
        mLabUserMapper.delete(id);
    }

    @Override
    public Integer update(MLabUser mLabUser) {
        return mLabUserMapper.update(mLabUser);
    }

    @Override
    public List<MLabUser> get(MLabUser mLabUser) {
        return mLabUserMapper.get(mLabUser);
    }

    @Override
    public MLabUser match(MLabUser mLabUser) {
        return mLabUserMapper.match(mLabUser);
    }

    @Override
    public List<MLabUser> matchOr(MLabUser mLabUser) {
        return mLabUserMapper.matchOr(mLabUser);
    }
}
