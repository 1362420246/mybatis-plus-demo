package com.qbk.mybatisplusdemo;

import com.qbk.mybatisplusdemo.entity.User;
import com.qbk.mybatisplusdemo.mapper.UserDynamicMapper;
import com.qbk.mybatisplusdemo.service.UserDynamicService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

/**
 * 多数据源测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DynamicTests {

    @Autowired
    private UserDynamicService userDynamicService ;

    @Autowired
    private UserDynamicMapper userDynamicMapper ;

    @Test
    public void dynamicTest(){
        List<Map<String, Object>> maps = userDynamicService.selectAll();
        List<Map<String, Object>> maps2 = userDynamicService.selectByCondition();
        System.out.println(maps);
        System.out.println(maps2);
        int inset = userDynamicService.insert();
        System.out.println(inset);
    }

    @Test
    public void dynamicMybatisTest(){
        boolean kk = userDynamicMapper.addUser(20, "kk", 29);
        System.out.println(kk);
        List<User> users = userDynamicMapper.selectAll();
        System.out.println(users);
    }


}
