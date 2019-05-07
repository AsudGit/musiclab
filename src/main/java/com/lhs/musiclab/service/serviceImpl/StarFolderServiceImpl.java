package com.lhs.musiclab.service.serviceImpl;

import com.lhs.musiclab.dao.StarFolderMapper;
import com.lhs.musiclab.pojo.StarFolder;
import com.lhs.musiclab.service.StarFolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class StarFolderServiceImpl implements StarFolderService {
    @Autowired
    private StarFolderMapper starFolderMapper;
    @Override
    public Integer add(StarFolder starFolder) {
        return starFolderMapper.add(starFolder);
    }

    @Override
    public List<StarFolder> list() {
        return starFolderMapper.list();
    }

    @Override
    public void delete(Integer id) {
        starFolderMapper.delete(id);
    }

    @Override
    public Integer update(StarFolder starFolder) {
        return starFolderMapper.update(starFolder);
    }

    @Override
    public StarFolder get(Integer id) {
        return starFolderMapper.get(id);
    }
}
