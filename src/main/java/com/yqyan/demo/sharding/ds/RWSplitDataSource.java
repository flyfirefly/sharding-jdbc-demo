package com.yqyan.demo.sharding.ds;

import io.shardingsphere.api.config.MasterSlaveRuleConfiguration;
import io.shardingsphere.shardingjdbc.api.MasterSlaveDataSourceFactory;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static com.yqyan.demo.sharding.ds.DataSourceUtils.newDataSource;

/**
 * 读写分离Sharding数据源
 * @Author yanyaqiang
 * @Date 2019/4/15 17:17
 **/
public class RWSplitDataSource {

    public static DataSource getDataSource(){
        return DataSourceHolder.dataSource;
    }


    private static class DataSourceHolder{
        private static DataSource dataSource;

        //主从库名
        private static final String DS_MASTER = "ds_master";
        private static final String DS_SLAVE0 = "ds_slave0";
        private static final String DS_SLAVE1 = "ds_slave1";

        //初始化datasource
        static {
            Map<String, DataSource> dataSourceMap = new HashMap<>();
            dataSourceMap.put(DS_MASTER, newDataSource(DS_MASTER));
            dataSourceMap.put(DS_SLAVE0, newDataSource(DS_SLAVE0));
            dataSourceMap.put(DS_SLAVE1, newDataSource(DS_SLAVE1));

            //配置读写分离规则
            MasterSlaveRuleConfiguration config = new MasterSlaveRuleConfiguration("ds_master_slave", DS_MASTER,
                    Arrays.asList(DS_SLAVE0, DS_SLAVE1));

            try {
                dataSource = MasterSlaveDataSourceFactory.createDataSource(dataSourceMap, config, new HashMap<>(), new Properties());
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }
}
