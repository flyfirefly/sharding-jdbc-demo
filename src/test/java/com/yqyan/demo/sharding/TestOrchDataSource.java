package com.yqyan.demo.sharding;

import com.yqyan.demo.sharding.ds.OrchDataSource;
import org.junit.Test;

import javax.sql.DataSource;

/**
 * @Author yanyaqiang
 * @Date 2019/4/25 16:58
 **/
public class TestOrchDataSource extends TestShardingDataSource {

    @Override
    @Test
    public void delete() {
        super.delete();
    }

    @Override
    @Test
    public void insert() {
        super.insert();
    }

    @Override
    @Test
    public void select() {
        super.select();
    }

    @Override
    @Test
    public void update() {
        super.update();
    }

    @Override
    protected DataSource getDataSource() {
        return OrchDataSource.getDataSource();
    }
}
