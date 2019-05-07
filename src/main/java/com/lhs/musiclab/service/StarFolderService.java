package com.lhs.musiclab.service;

import com.lhs.musiclab.pojo.StarFolder;

import java.util.List;

public interface StarFolderService {
    public Integer add(StarFolder starFolder);

    public List<StarFolder> list();

    public void delete(Integer id);

    public Integer update(StarFolder starFolder);

    public StarFolder get(Integer id);
}
