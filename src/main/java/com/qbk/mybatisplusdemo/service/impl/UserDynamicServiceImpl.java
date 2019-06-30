package com.qbk.mybatisplusdemo.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.qbk.mybatisplusdemo.service.UserDynamicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 多数据源
 *
 * 使用 @DS 切换数据源。
 * @DS 可以注解在方法上和类上，同时存在方法注解优先于类上注解。
 * 注解在service实现或mapper接口方法上，但强烈不建议同时在service和mapper注解。 (可能会有问题)
 *
 * 没有@DS ：	默认数据源
 * @DS("dsName") ：	dsName可以为组名也可以为具体某个库的名称
 */
@Service
@DS("slave")
public class UserDynamicServiceImpl implements UserDynamicService {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    /*
    简介
    dynamic-datasource-spring-boot-starter 是一个基于springboot的快速集成多数据源的启动器。

    # 优势
    网上关于动态数据源的切换的文档有很多，核心只有两种。
        1.构建多套环境，优势是方便控制也容易集成一些简单的分布式事务，缺点是非动态同时代码量较多,配置难度大。
        2.基于spring提供原生的 AbstractRoutingDataSource ，参考一些文档自己实现切换。

    如果你的数据源较少，场景不复杂，选择以上任意一种都可以。如果你需要更多特性，请尝试本动态数据源。
    1.数据源分组，适用于多种场景 纯粹多库 读写分离 一主多从 混合模式。
    2.简单集成Druid数据源监控多数据源，简单集成Mybatis-Plus简化单表，简单集成P6sy格式化sql，简单集成Jndi数据源。
    3.简化Druid和HikariCp配置，提供全局参数配置。
    4.提供自定义数据源来源(默认使用yml或properties配置)。
    5.项目启动后能动态增减数据源。
    6.使用spel动态参数解析数据源，如从session，header和参数中获取数据源。（多租户架构神器）
    7.多层数据源嵌套切换。（一个业务ServiceA调用ServiceB，ServiceB调用ServiceC，每个Service都是不同的数据源）
    8.使用正则匹配或spel表达式来切换数据源（实验性功能）。

    # 劣势
    不能使用多数据源事务（同一个数据源下能使用事务），网上其他方案也都不能提供。
    如果你需要使用到分布式事务，那么你的架构应该到了微服务化的时候了。
    如果呼声强烈，项目达到800 star，作者考虑集成分布式事务。
    PS: 如果您只是几个数据库但是有强烈的需求分布式事务，建议还是使用传统方式自己构建多套环境集成atomic这类，网上百度很多。

    # 约定
    本框架只做 切换数据源 这件核心的事情，并不限制你的具体操作，切换了数据源可以做任何CRUD。
    配置文件所有以下划线 _ 分割的数据源 首部 即为组的名称，相同组名称的数据源会放在一个组下。
    切换数据源即可是组名，也可是具体数据源名称，切换时默认采用负载均衡机制切换。
    默认的数据源名称为 master ，你可以通过spring.datasource.dynamic.primary修改。
    方法上的注解优先于类上注解。

    # 建议
    强烈建议在 主从模式 下遵循普遍的规则，以便他人能更轻易理解你的代码。
    主数据库 建议 只执行 INSERT UPDATE DELETE 操作。
    从数据库 建议 只执行 SELECT 操作。

    # 使用方法
    1.引入dynamic-datasource-spring-boot-starter。
    2.配置数据源。
     */

    @Override
    public int insert() {
        //TODO 需要在数据库自行配置主从数据同步
        return jdbcTemplate.update("insert into user (id,name,age) values(?,?,?)",
                99,"qbk",29);
    }

    @Override
    public List<Map<String, Object>> selectAll() {
        return jdbcTemplate.queryForList("select * from user");
    }

    @Override
    @DS("slave_1")
    public List<Map<String, Object>> selectByCondition() {
        return jdbcTemplate.queryForList("select * from user where age >10");
    }
}
