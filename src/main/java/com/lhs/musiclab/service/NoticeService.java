package com.lhs.musiclab.service;

import com.lhs.musiclab.pojo.Notice;

import java.util.List;

public interface NoticeService {
    public Integer add(Notice notice);

    public List<Notice> list();

    public void delete(Integer id);

    public Integer update(Notice notice);

    public Notice get(Integer id);
}
