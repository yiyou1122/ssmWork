package com.springmvc.dao;

import com.springmvc.bean.Video;

public interface VideoDao {
    static final String NAME_SPACE = "VideoMapper.";
    public Video queryById(int id);
}
