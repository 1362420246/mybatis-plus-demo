package com.qbk.mybatisplusdemo;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * 多数据源时 排除 原生Druid的快速配置类。
 * 为什么要排除DruidDataSourceAutoConfigure ？
 * DruidDataSourceAutoConfigure会注入一个DataSourceWrapper，其会在原生的spring.datasource下找url,username,password等。
 * 而我们动态数据源的配置路径是变化的。
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
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor().setCountSqlParser(new JsqlParserCountOptimize());
    }

    /**
     * 乐观锁插件
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }

    @Bean
    RestTemplate restTemplate(){
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(15000);
        requestFactory.setReadTimeout(25000);
        return new RestTemplate(requestFactory);
    }
}
