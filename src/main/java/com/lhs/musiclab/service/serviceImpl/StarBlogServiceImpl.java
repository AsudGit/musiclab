package com.lhs.musiclab.service.serviceImpl;

import com.lhs.musiclab.dao.StarBlogMapper;
import com.lhs.musiclab.pojo.StarBlog;
import com.lhs.musiclab.pojo.StarBlog_status;
import com.lhs.musiclab.service.StarBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StarBlogServiceImpl implements StarBlogService {
    @Autowired
    private StarBlogMapper starBlogMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public Integer add(StarBlog starBlog) {
        return starBlogMapper.add(starBlog);
    }

    @Override
    public void setForRedis(StarBlog_status starBlog_status) {
        String key = "star:blog:uid=" + starBlog_status.getStarBlog().getUid() +
                ",bid=" + starBlog_status.getStarBlog().getBid() +
                ",folder=" + starBlog_status.getStarBlog().getFolder();
        redisTemplate.opsForValue().set(key,starBlog_status);
    }

    @Override
    public List<StarBlog> list() {
        return starBlogMapper.list();
    }

    @Override
    public void delete(String sbid) {
        starBlogMapper.delete(sbid);
    }

    @Override
    public Integer update(StarBlog starBlog) {
        return starBlogMapper.update(starBlog);
    }

    @Override
    public StarBlog get(Integer id) {
        return starBlogMapper.get(id);
    }

    public StarBlog_status get(String uid,String bid,Integer folder){
        StarBlog_status starBlog_status = new StarBlog_status();
        Object o = redisTemplate.opsForValue().get("star:blog:uid=" + uid +
                ",bid=" + bid +
                ",folder=" + folder);
        if (o!=null){
            starBlog_status = (StarBlog_status) o;
        }else {
            StarBlog starBlog = new StarBlog();
            starBlog.setFolder(folder);
            starBlog.setBid(bid);
            starBlog.setUid(uid);
            StarBlog_status starBlog_status1 = starBlogMapper.get(starBlog);
            if (starBlog_status1!=null){
                starBlog_status1.setStatus(3);
                starBlog_status = starBlog_status1;
            }else {
                starBlog_status.setStarBlog(starBlog);
                starBlog_status.setStatus(0);
            }
            setForRedis(starBlog_status);
        }
        return starBlog_status;
    }

    @Override
    public List<StarBlog_status> get(String uid,String bid) {
        List<StarBlog_status> list = new ArrayList<>();
        Set keys = redisTemplate.keys("star:blog:uid=" + uid + ",bid=" + bid + ",*");
        if (keys.size()!=0||keys!=null){
            Iterator iterator = keys.iterator();
            while (iterator.hasNext()){
                list.add((StarBlog_status) redisTemplate.opsForValue().get(iterator.next()));
            }
        }else {
            Map<String, Object> map = new HashMap<>();
            map.put("uid", uid);
            map.put("bid", bid);
            list = starBlogMapper.get(map);
            if (list!=null&&list.size()>0) {
                for (StarBlog_status s : list) {
                    s.setStatus(3);
                    setForRedis(s);
                }
            }
        }
        return list;
    }

    @Override
    public List<StarBlog_status> get(String uid,Integer folder) {
        List<StarBlog_status> list = new ArrayList<>();
        Set keys = redisTemplate.keys("star:blog:uid=" + uid + "*"+ "folder="+folder);
        if (keys.size()!=0||keys!=null){
            Iterator iterator = keys.iterator();
            while (iterator.hasNext()){
                list.add((StarBlog_status) redisTemplate.opsForValue().get(iterator.next()));
            }
        }else {
            Map<String, Object> map = new HashMap<>();
            map.put("uid", uid);
            map.put("folder", folder);
            list = starBlogMapper.get(map);
            if (list!=null&&list.size()>0) {
                for (StarBlog_status s : list) {
                    s.setStatus(3);
                    setForRedis(s);
                }
            }
        }
        return list;
    }
}
