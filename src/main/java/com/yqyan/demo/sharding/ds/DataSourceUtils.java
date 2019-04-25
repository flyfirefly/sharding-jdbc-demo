package com.yqyan.demo.sharding.ds;

import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;

/**
 * @Author yanyaqiang
 * @Date 2019/4/15 17:20
 **/
public class DataSourceUtils {
    // mysql 配置信息
    private static final String MYSQL_JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static final String LOCAL_JDBC_URL = "jdbc:mysql://localhost:3306/";

    public static DataSource newDataSource(String dbname){
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(MYSQL_JDBC_DRIVER);
        ds.setUrl(LOCAL_JDBC_URL + dbname);
        ds.setUsername(USERNAME);
        ds.setPassword(PASSWORD);
        return ds;
    }
}
