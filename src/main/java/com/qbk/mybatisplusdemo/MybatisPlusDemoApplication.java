package com.qbk.mybatisplusdemo;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * druid多数据源时 排除 原生Druid的快速配置类。
 * 为什么要排除DruidDataSourceAutoConfigure ？
 * DruidDataSourceAutoConfigure会注入一个DataSourceWrapper，其会在原生的spring.datasource下找url,username,password等。
 * 而我们动态数据源的配置路径是变化的。
 *
 * 注：dynamic-datasource-spring-boot-starter 高版本修复了 druid多数据源时 需要排除 bug
 */
//@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
@SpringBootApplication()
/**
 * 在 Spring Boot 启动类中添加 @MapperScan 注解，扫描 Mapper 文件夹：
 */
@MapperScan("com.qbk.mybatisplusdemo.mapper")
public class MybatisPlusDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisPlusDemoApplication.class, args);
    }

    /**
     * 雪花算法
     */
    @Bean
    public IdentifierGenerator identifierGenerator() {
        //基于zookeeper，snowflake的分布式统一ID
        //return new ImadcnIdentifierGenerator();
        return new DefaultIdentifierGenerator();
    }

    /**
     * MybatisPlusInterceptor
     * 该插件是核心插件,目前代理了 Executor#query 和 Executor#update 和 StatementHandler#prepare 方法
     *
     * MybatisPlusInterceptor 目前已有的功能:
     * 自动分页: PaginationInnerInterceptor
     * 多租户: TenantLineInnerInterceptor
     * 动态表名: DynamicTableNameInnerInterceptor
     * 乐观锁: OptimisticLockerInnerInterceptor
     * sql性能规范: IllegalSQLInnerInterceptor
     * 防止全表更新与删除: BlockAttackInnerInterceptor
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        //分页插件
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        //乐观锁插件
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        return interceptor;
    }

    @Bean
    RestTemplate restTemplate(){
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(15000);
        requestFactory.setReadTimeout(25000);
        return new RestTemplate(requestFactory);
    }
}
