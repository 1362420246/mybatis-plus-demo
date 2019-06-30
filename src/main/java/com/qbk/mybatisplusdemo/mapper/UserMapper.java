package com.qbk.mybatisplusdemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qbk.mybatisplusdemo.entity.User;

/**
 * Mapper 继承该接口后，无需编写 mapper.xml 文件，即可获得CRUD功能
 *  这个 Mapper 支持 id 泛型
 */
public interface UserMapper extends BaseMapper<User> {


}
