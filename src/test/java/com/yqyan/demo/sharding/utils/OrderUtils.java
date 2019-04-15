package com.yqyan.demo.sharding.utils;

import com.yqyan.demo.sharding.model.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author yanyaqiang
 * @Date 2019/4/15 17:57
 **/
public class OrderUtils {

    private static AtomicLong curOrderId = new AtomicLong(1l);
    private static AtomicLong curUserId = new AtomicLong(1l);
    private static Random random = new Random();

    public static final String DELETE_ALL_ORDERS_SQL = "delete from t_order";
    public static final String SELECT_ALL_ORDERS_SQL = "select * from t_order order by user_id, order_id";
    public static final String UPDATE_ORDER_SQL =  "update t_order set status = 1 where order_id = 3";
    public static final String INSERT_ORDER_PATTERN_SQL =  "insert into t_order (order_id, user_id, cost, status) values (%d, %d, %f, %d)";


    public static Order extractOrder(ResultSet rs){
        try {
            Long orderId = rs.getLong(1);
            Long userId = rs.getLong(2);
            Double cost = rs.getDouble(3);
            Integer status = rs.getInt(4);
            Timestamp createdAt = rs.getTimestamp(5);
            Timestamp updatedAt = rs.getTimestamp(6);

            return new Order(orderId, userId, cost, status, createdAt, updatedAt);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Order newOrder(){
        Order order = new Order();
        order.setOrderId(nextOrderId());
        order.setUserId(nextUserId());
        order.setCost(random.nextDouble() * 100);
        order.setStatus(0);
        return order;
    }

    public static Long nextOrderId(){
        return curOrderId.getAndIncrement();
    }

    public static synchronized Long nextUserId(){
        return curOrderId.get() % 2 == 0 ? curUserId.getAndIncrement() : curUserId.get();
    }
}
