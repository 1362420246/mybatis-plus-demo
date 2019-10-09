package com.qbk.mybatisplusdemo.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 实体
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "user")//指定表名
public class User {
    //value与数据库主键列名一致，若实体类属性名与表主键列名一致可省略value
    @TableId(value = "id",type = IdType.AUTO)//指定自增策略
    private Long id;
    //若没有开启驼峰命名，或者表中列名不符合驼峰规则，可通过该注解指定数据库表中的列名，exist标明数据表中有没有对应列
    @TableField(value = "name",exist = true)
    private String name;
    private Integer age;
    private String email;
    /**
     * 使用乐观锁插件，注解实体字段 @Version 必须要!
     */
    @Version
    private Integer version;
}

