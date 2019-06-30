package com.qbk.mybatisplusdemo.entity;

import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

/**
 * 实体
 */
@Data
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String email;
    /**
     * 使用乐观锁插件，注解实体字段 @Version 必须要!
     */
    @Version
    private Integer version;
}
