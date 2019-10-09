package com.qbk.mybatisplusdemo;

import com.qbk.mybatisplusdemo.entity.User;
import com.qbk.mybatisplusdemo.mapper.UserMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * mapper crud  和 service crud 测试
 * 官网：https://mp.baomidou.com/guide/crud-interface.html
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisPlusDemoApplicationTests {

    @Autowired
    private RestTemplate restTemplate ;

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        //预期是 5条数据
        Assert.assertEquals(5, userList.size());
        userList.forEach(System.out::println);
    }

    @Test
    public void testInset() {
        User user = User.builder().name("qbk").age(29).email("11@qq.com").build();
        int insert = userMapper.insert(user);
        Assert.assertEquals(1,insert);
    }

    @Test
    public void testSelectApi() {
        //headers
        HttpHeaders requestHeaders = new HttpHeaders();
        //requestHeaders.add(Authorization, token);

        //HttpEntity
        HttpEntity<Map> requestEntity = new HttpEntity<Map>(null, requestHeaders);

        //参数
        Map<String,Object> params = new HashMap<>();
        String result = restTemplate.exchange("http://localhost:8111/get", HttpMethod.GET,requestEntity,String.class,params).getBody();
        System.out.println(result);
    }
}
