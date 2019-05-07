package com.lhs.musiclab.dao;

import com.lhs.musiclab.pojo.StarFolder;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface StarFolderMapper {
    public Integer add(StarFolder starFolder);

    public List<StarFolder> list();

    public void delete(Integer id);

    public Integer update(StarFolder starFolder);

    public StarFolder get(Integer id);
}
