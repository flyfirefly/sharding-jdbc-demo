package com.yqyan.demo.sharding.ds;

import io.shardingsphere.api.config.ShardingRuleConfiguration;
import io.shardingsphere.api.config.TableRuleConfiguration;
import io.shardingsphere.api.config.strategy.InlineShardingStrategyConfiguration;
import io.shardingsphere.orchestration.config.OrchestrationConfiguration;
import io.shardingsphere.orchestration.reg.api.RegistryCenterConfiguration;
import io.shardingsphere.shardingjdbc.orchestration.api.OrchestrationShardingDataSourceFactory;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import static com.yqyan.demo.sharding.ds.DataSourceUtils.newDataSource;

/**
 *
 * 数据治理数据源，从注册中心创建数据源
 * @Author yanyaqiang
 * @Date 2019/4/25 16:24
 **/
public class OrchDataSource {

    public static DataSource getDataSource(){
        return DataSourceHolder.dataSource;
    }

    private static class DataSourceHolder {

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

            // 配置注册中心
            RegistryCenterConfiguration regConfig = new RegistryCenterConfiguration();
            regConfig.setServerLists("localhost:2181");
            regConfig.setNamespace("sharding-sphere-orchestration");

            // 配置数据治理
            OrchestrationConfiguration orchConfig = new OrchestrationConfiguration("orchestration-sharding-data-source", regConfig, false);

            // 获取数据源对象
            try {
                dataSource = OrchestrationShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig, new ConcurrentHashMap(), new Properties(), orchConfig);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }



}
