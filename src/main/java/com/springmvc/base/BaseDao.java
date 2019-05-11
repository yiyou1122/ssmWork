package com.springmvc.base;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

public class BaseDao {
    public static SqlMapClientTemplate sqlMap;

    public static SqlMapClientTemplate getSqlMap() {
        return sqlMap;
    }

    public static void setSqlMap(SqlMapClientTemplate sqlMap) {
        BaseDao.sqlMap = sqlMap;
    }
}
