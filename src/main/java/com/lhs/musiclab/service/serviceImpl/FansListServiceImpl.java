package com.lhs.musiclab.service.serviceImpl;

import com.lhs.musiclab.dao.FansListMapper;
import com.lhs.musiclab.pojo.FansList;
import com.lhs.musiclab.service.FansListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class FansListServiceImpl implements FansListService {
    @Autowired
    private FansListMapper fansListMapper;

    @Override
    public Integer add(FansList fansList) {
        return fansListMapper.add(fansList);
    }

    @Override
    public List<FansList> list() {
        return fansListMapper.list();
    }

    @Override
    public void delete(Integer id) {
        fansListMapper.delete(id);
    }

    @Override
    public Integer update(FansList fansList) {
        return fansListMapper.update(fansList);
    }

    @Override
    public FansList get(Integer id) {
        return fansListMapper.get(id);
    }
}
