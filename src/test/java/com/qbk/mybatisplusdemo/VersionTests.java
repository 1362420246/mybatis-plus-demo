package com.qbk.mybatisplusdemo;

import com.qbk.mybatisplusdemo.entity.User;
import com.qbk.mybatisplusdemo.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 测试乐观锁插件
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class VersionTests {

    @Autowired
    private UserService userService ;

    @Test
    public void fun(){
        long id = 1L;
        int version = 0;

        User u = new User();
        u.setId(id);
        u.setVersion(version);
        u.setAge(22);

        if(userService.updateById(u)){
            System.out.println("Update successfully");
        }else{
            System.out.println("Update failed due to modified by others");
        }
    }

}
