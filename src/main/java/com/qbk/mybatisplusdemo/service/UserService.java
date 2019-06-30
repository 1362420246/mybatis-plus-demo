package com.qbk.mybatisplusdemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qbk.mybatisplusdemo.entity.User;

/**
 *   继承IService接口后 ，即可获得CRUD功能
 *   可通过 IService 接口查看支持方法
 */
public interface UserService extends IService<User> {
}
