package com.springmvc.service.impl;

import com.springmvc.bean.Video;
import com.springmvc.dao.VideoDao;
import com.springmvc.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoDao videoDao;

    @Override
    public Video queryById(int id) {
        return videoDao.queryById(id);
    }

}
