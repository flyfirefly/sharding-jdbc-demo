# sharding-jdbc-demo

使用 [sharding-jdbc](https://shardingsphere.apache.org/document/legacy/3.x/document/cn/overview/) 实现数据库分库分表演示功能。

配置方式：

* [x] Java 代码配置
* [x] SpringBoot 集成配置

功能列表：

* [x] 数据分片
* [x] 读写分离
* [ ] 强制路由
* [ ] 数据治理
* [ ] 分布式事务

# 遇到的问题

* 读写分离不支持主库和从库的数据同步。需修改数据库配置文件，添加主从库同步配置。
