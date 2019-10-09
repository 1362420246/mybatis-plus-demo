package com.qbk.mybatisplusdemo.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.qbk.mybatisplusdemo.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 多数据源 @DS 注解
 */
public interface UserDynamicMapper {

    @Insert("INSERT INTO user (id,name,age) values (#{id},#{name},#{age})")
    boolean addUser(@Param("id") long id,@Param("name") String name, @Param("age") Integer age);

    @Update("UPDATE user set name=#{name}, age=#{age} where id =#{id}")
    boolean updateUser(@Param("id") Integer id, @Param("name") String name, @Param("age") Integer age);

    @Delete("DELETE from user where id =#{id}")
    boolean deleteUser(@Param("id") Integer id);

    @Select("SELECT * FROM user")
//    @DS("slave_1")
    List<User> selectAll();
}
