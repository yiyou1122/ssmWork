package com.springmvc.dao.impl;

import com.springmvc.base.BaseDao;
import com.springmvc.bean.Video;
import com.springmvc.dao.VideoDao;

public class VideoDaoImpl extends BaseDao implements VideoDao {

    @Override
    public Video queryById(int id) {
        String str = NAME_SPACE.concat("queryById");
        return (Video) sqlMap.queryForObject(str, id);
    }
}
