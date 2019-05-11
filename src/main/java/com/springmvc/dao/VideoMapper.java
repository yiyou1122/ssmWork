package com.springmvc.dao;

import com.springmvc.bean.Video;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

public interface VideoMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Video record);

    int insertSelective(Video record);

    Video selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Video record);

    int updateByPrimaryKey(Video record);
}