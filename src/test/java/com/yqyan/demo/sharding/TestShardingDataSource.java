package com.yqyan.demo.sharding;

import com.yqyan.demo.sharding.ds.ShardingDataSource;
import com.yqyan.demo.sharding.model.Order;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.stream.IntStream;

import static com.yqyan.demo.sharding.utils.OrderUtils.*;

/**
 * @Author yanyaqiang
 * @Date 2019/4/15 14:56
 **/
public class TestShardingDataSource extends SqlExecutor{

    @Test
    public void delete(){
        execute( stmt -> stmt.execute(DELETE_ALL_ORDERS_SQL));
    }

    @Test
    public void insert() {
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
        select();
    }

    @Override
    protected DataSource getDataSource() {
        return ShardingDataSource.getDataSource();
    }

    private void insertOrder(Order order){
        String sql = String.format(INSERT_ORDER_PATTERN_SQL, order.getUserId(),
                order.getCost(), order.getStatus());
        execute(stmt -> stmt.execute(sql));
    }

}
