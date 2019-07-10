package com.lhs.musiclab.service.serviceImpl;

import com.lhs.musiclab.dao.MLabUserCountMapper;
import com.lhs.musiclab.pojo.MLabUserCount;
import com.lhs.musiclab.service.MLabUserCountService;
import com.lhs.musiclab.utils.MyDateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class MLabUserCountServiceImpl implements MLabUserCountService {
    @Autowired
    private MLabUserCountMapper mLabUserCountMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    @Async
    public Integer add(String uid, Date date) {
        return mLabUserCountMapper.add(uid,date);
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
    public MLabUserCount get(String uid) {
        MLabUserCount mLabUserCount = (MLabUserCount) redisTemplate.opsForValue().get("count:mlabuser:" + uid);
        if (mLabUserCount==null){
            mLabUserCount=mLabUserCountMapper.get(uid);
            redisTemplate.opsForValue().set("count:mlabuser:"+uid,mLabUserCount);
        }
        Date date = (Date) redisTemplate.opsForValue().get("recent:" + uid);
        if (date!=null) {
            mLabUserCount.setRecentlyLogin(date);
        }
        return mLabUserCount;
    }

    @Async
    @Override
    public void updateRecentlyLoginForRedis(String uid,Date date){
        MLabUserCount mLabUserCount = (MLabUserCount) redisTemplate.opsForValue().get("count:mlabuser:" + uid);
        if (mLabUserCount==null){
            mLabUserCount = mLabUserCountMapper.get(uid);
        }
        redisTemplate.opsForValue().set("recent:" + uid,mLabUserCount.getRecentlyLogin());
        if (!MyDateUtils.sameDate(mLabUserCount.getRecentlyLogin(),date)){
            mLabUserCount.setRecentlyLogin(date);
        }
        redisTemplate.opsForValue().set("count:mlabuser:" + uid,mLabUserCount);
    }

    @Override
    public void updateForRedis(String uid,MLabUserCount mLabUserCount){
        redisTemplate.opsForValue().set("count:mlabuser:"+uid,mLabUserCount);
    }

    @Override
    public Map getTotalForRedis(Integer plate) {
        return (Map) redisTemplate.opsForValue().get("total:"+plate);
    }

    @Override
    public void setTotalForRedis(Integer plate, Map total) {
        redisTemplate.opsForValue().set("total:"+plate,total);
    }
}
