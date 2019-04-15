package com.yqyan.demo.sharding;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @Author yanyaqiang
 * @Date 2019/4/15 17:50
 **/
public abstract class SqlExecutor {

    protected void execute(StatementExecutor stmtExecutor){
        DataSource ds = getDataSource();
        try (Connection conn = ds.getConnection()){
            Statement stmt = conn.createStatement();
            stmtExecutor.execute(stmt);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FunctionalInterface
    interface StatementExecutor {
        void execute(Statement stmt) throws SQLException;
    }

    protected abstract DataSource getDataSource();
}
