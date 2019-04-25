package com.yqyan.demo.sharding;

import com.yqyan.demo.sharding.utils.OrderUtils;
import io.shardingsphere.api.HintManager;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author yanyaqiang
 * @Date 2019/4/23 10:18
 **/
public class TestHintDataSourceBasedSpring extends TestShardingDataSourceBasedSpring{

    @Test
    public void hintSelect(){
        try (HintManager hintManager = HintManager.getInstance();
             Connection conn = ds.getConnection();
             PreparedStatement stmt = conn.prepareStatement(OrderUtils.SELECT_ALL_ORDERS_SQL)){

            hintManager.addDatabaseShardingValue("t_order", 1);
            hintManager.addTableShardingValue("t_order", 2);

            try(ResultSet rs = stmt.executeQuery()){
                System.out.println(OrderUtils.extractOrder(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
