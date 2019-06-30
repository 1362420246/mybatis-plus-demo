package com.qbk.mybatisplusdemo.web;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qbk.mybatisplusdemo.entity.User;
import com.qbk.mybatisplusdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService ;

    @GetMapping("/get")
    public Object get(){
        //条件构造器
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //只查两个字段
        queryWrapper.select("name","age");
        return userService.list(queryWrapper);
    }

}
