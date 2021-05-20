package com.qbk.mybatisplusdemo.TransactionManage;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;

//@Configuration
//@EnableTransactionManagement
public class TransactionManagement implements TransactionManagementConfigurer {

    @Autowired
    private DataSource dataSource;

    /**
     * 主数据源 事务管理
     */
    @Bean(name = "masterTrManager")
    public PlatformTransactionManager masterTransactionManager(){
        DynamicRoutingDataSource dynamicRoutingDataSource = (DynamicRoutingDataSource)dataSource;
        DataSource dataSource1 = dynamicRoutingDataSource.getDataSource("master");
        return new DataSourceTransactionManager(dataSource1);
    }

    /**
     * 事务管理
     */
    @Bean(name="slave1TrManager")
    public PlatformTransactionManager slave1TransactionManager(){
        DynamicRoutingDataSource dynamicRoutingDataSource = (DynamicRoutingDataSource)dataSource;
        DataSource dataSource1 = dynamicRoutingDataSource.getDataSource("slave_1");
        return new DataSourceTransactionManager(dataSource1);
    }

    /**
     * 返回默认事务管理器
     */
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return masterTransactionManager();
    }
}
