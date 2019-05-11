package com.springmvc.dao;

import com.springmvc.bean.busiPayInfo;

public interface busiPayInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(busiPayInfo record);

    int insertSelective(busiPayInfo record);

    busiPayInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(busiPayInfo record);

    int updateByPrimaryKey(busiPayInfo record);
}