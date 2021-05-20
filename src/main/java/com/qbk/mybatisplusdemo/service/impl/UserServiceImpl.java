package com.qbk.mybatisplusdemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qbk.mybatisplusdemo.entity.User;
import com.qbk.mybatisplusdemo.mapper.UserMapper;
import com.qbk.mybatisplusdemo.service.UserService;
import org.springframework.stereotype.Service;

/**
 *   继承ServiceImpl类后 ，即可获得CRUD功能
 */
/**
 * IService 接口方法说明
 * 以下的方法使用介绍:
 *
 * 一. 名称介绍
 * 1. 方法名带有 query 的为对数据的查询操作, 方法名带有 update 的为对数据的修改操作
 * 2. 方法名带有 lambda 的为内部方法入参 column 支持函数式的
 *
 * 二. 支持介绍
 * 1. 方法名带有 query 的支持以 {@link ChainQuery} 内部的方法名结尾进行数据查询操作
 * 2. 方法名带有 update 的支持以 {@link ChainUpdate} 内部的方法名为结尾进行数据修改操作
 *
 * 三. 使用示例,只用不带 lambda 的方法各展示一个例子,其他类推
 * 1. 根据条件获取一条数据: `query().eq("column", value).one()`
 * 2. 根据条件删除一条数据: `update().eq("column", value).remove()`
 *
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
