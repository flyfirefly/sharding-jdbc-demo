package com.yqyan.demo.sharding;

import com.yqyan.demo.sharding.ds.CustomRWSplitDataSource;
import com.yqyan.demo.sharding.model.Order;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.stream.IntStream;

import static com.yqyan.demo.sharding.utils.OrderUtils.*;

/**
 * @Author yanyaqiang
 * @Date 2019/4/15 17:44
 **/
public class TestRWSplitDataSource extends SqlExecutor{

    @Test
    public void delete(){
        execute(stmt -> stmt.executeUpdate(DELETE_ALL_ORDERS_SQL));
    }

    /**
     * sharding-jdbc 不支持主库数据同步到从库，在数据库配置
     */
    @Test
    public void insert(){
        IntStream.range(1, 5).forEach(i -> insertOrder(newOrder()));
    }

    @Test
    public void select(){
        execute(stmt -> {
            ResultSet rs = stmt.executeQuery(SELECT_ALL_ORDERS_SQL);
            while (rs.next()){
                System.out.println(extractOrder(rs));
            }
        });
    }

    @Test
    public void update(){
        execute(stmt -> stmt.execute(UPDATE_ORDER_SQL));
    }

    private void insertOrder(Order order){
        String sqlPattern = "insert into t_order (order_id, user_id, cost, status) values (%d, %d, %f, %d)";
        String sql = String.format(sqlPattern, order.getOrderId(), order.getUserId(),
                order.getCost(), order.getStatus());
        execute(stmt -> stmt.execute(sql));
    }

    @Override
    protected DataSource getDataSource() {
        return CustomRWSplitDataSource.getDataSource();
    }
}
