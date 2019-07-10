package com.lhs.musiclab.service.serviceImpl;

import com.lhs.musiclab.dao.StarFolderMapper;
import com.lhs.musiclab.pojo.StarFolder;
import com.lhs.musiclab.service.StarFolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@CacheConfig(cacheNames = "folder")
public class StarFolderServiceImpl implements StarFolderService {
    @Autowired
    private StarFolderMapper starFolderMapper;

    @Override
    @CacheEvict(allEntries = true)
    public Integer add(StarFolder starFolder) {
        return starFolderMapper.add(starFolder);
    }

    @Override
    public List<StarFolder> list() {
        return starFolderMapper.list();
    }

    @Override
    @Cacheable
    public List<StarFolder> get(String uid) {
        return starFolderMapper.get(uid);
    }

    @Override
    public void delete(Integer id) {
        starFolderMapper.delete(id);
    }

    @Override
    public Integer update(StarFolder starFolder) {
        return starFolderMapper.update(starFolder);
    }

}
