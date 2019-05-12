package com.lhs.musiclab.dao;

import com.lhs.musiclab.pojo.Notice;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
public interface NoticeMapper {
    public Integer add(Notice notice);

    public List<Notice> list();

    public void delete(Integer id);

    public Integer update(Notice notice);

    public Notice get(Integer id);
}
