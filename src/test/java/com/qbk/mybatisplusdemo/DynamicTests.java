package com.qbk.mybatisplusdemo;

import com.qbk.mybatisplusdemo.mapper.UserDynamicMapper;
import com.qbk.mybatisplusdemo.service.UserDynamicService;
import com.qbk.mybatisplusdemo.service.UserTxService;
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

    @Autowired
    private UserTxService userTxService;

    /**
     * 测试数据源切换
     */
    @Test
    public void dynamicTest(){
        List<Map<String, Object>> maps = userDynamicService.selectAll();
        List<Map<String, Object>> maps2 = userDynamicService.selectByCondition();
        System.out.println(maps);
        System.out.println(maps2);
        int inset = userDynamicService.insert();
        System.out.println(inset);
    }

    /**
     * 多数据源 切换数据源 不支持spring原生事务
     *
     * 同一个方法内部切换数据源，那么该方法上的事务就会导致 @DS 失效
     *
     * 如果同一个事务内切换数据源，则切换数据源就失效
     */
    @Test
    public void dynamicTransactionalTest(){
        userTxService.insertSpringTx();
    }

    /**
     * 多数据源 切换数据源 只能在切换后添加事务 才支持spring事务
     */
    @Test
    public void dynamicTransactionalTest2(){
        int insert = userDynamicService.insert();
        int insert2 = userDynamicService.insert2();
        System.out.println(insert);
        System.out.println(insert2);
    }

    /**
     * 多数据源 切换数据源 使用 @DSTransactional 控制事务
     */
    @Test
    public void dynamicTransactionalTest3(){
        userTxService.insertTx();
    }

}
