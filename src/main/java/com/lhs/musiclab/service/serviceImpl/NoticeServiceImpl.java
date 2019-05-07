package com.lhs.musiclab.service.serviceImpl;

import com.lhs.musiclab.dao.NoticeMapper;
import com.lhs.musiclab.pojo.Notice;
import com.lhs.musiclab.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    private NoticeMapper noticeMapper;
    @Override
    public Integer add(Notice notice) {
        return noticeMapper.add(notice);
    }

    @Override
    public List<Notice> list() {
        return noticeMapper.list();
    }

    @Override
    public void delete(Integer id) {
        noticeMapper.delete(id);
    }

    @Override
    public Integer update(Notice notice) {
        return noticeMapper.update(notice);
    }

    @Override
    public Notice get(Integer id) {
        return noticeMapper.get(id);
    }
}
