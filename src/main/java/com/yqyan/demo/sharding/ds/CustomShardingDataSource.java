package com.yqyan.demo.sharding.ds;

import io.shardingsphere.api.config.ShardingRuleConfiguration;
import io.shardingsphere.api.config.TableRuleConfiguration;
import io.shardingsphere.api.config.strategy.InlineShardingStrategyConfiguration;
import io.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import static com.yqyan.demo.sharding.ds.DataSourceUtils.newDataSource;

/**
 * 数据分片Sharding数据源
 * @Author yanyaqiang
 * @Date 2019/4/15 14:14
 **/
public class CustomShardingDataSource {

    public static DataSource getDataSource(){
        return DataSourceHolder.dataSource;
    }

    private static class DataSourceHolder{
        private static DataSource dataSource;

        //数据分库名
        private static final String DS0 = "ds0";
        private static final String DS1 = "ds1";

        // 初始化ShardingDataSource
        static {
            Map<String, DataSource> dataSourceMap = new HashMap<>();
            dataSourceMap.put(DS0, newDataSource(DS0));
            dataSourceMap.put(DS1, newDataSource(DS1));

            //配置表路由规则
            TableRuleConfiguration tableRuleConfig = new TableRuleConfiguration();
            tableRuleConfig.setLogicTable("t_order");
            tableRuleConfig.setActualDataNodes("ds${0..1}.t_order${0..1}");

            //配置分库规则，按 user_id 取模分库
            tableRuleConfig.setDatabaseShardingStrategyConfig(
                    new InlineShardingStrategyConfiguration("user_id", "ds${user_id % 2}"));
            //配置分表规则，按 order_id 取模分表
            tableRuleConfig.setTableShardingStrategyConfig(
                    new InlineShardingStrategyConfiguration("order_id", "t_order${order_id % 2}"));

            ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
            shardingRuleConfig.getTableRuleConfigs().add(tableRuleConfig);

            try {
                dataSource = ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig, new ConcurrentHashMap<>(), new Properties());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

}
