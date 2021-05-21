package com.qbk.mybatisplusdemo.service.impl;

import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import com.qbk.mybatisplusdemo.service.UserDynamicService;
import com.qbk.mybatisplusdemo.service.UserTxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserTxServiceImpl implements UserTxService {

    @Autowired
    private UserDynamicService userDynamicService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertSpringTx(){
        int insert = userDynamicService.insert3();
        System.out.println(insert);

        int insert2 = userDynamicService.insert4();
        System.out.println(insert2);
    }

    @Override
    @DSTransactional
    public void insertTx(){
        int insert = userDynamicService.insert3();
        System.out.println(insert);

        //int a = 10/0;

        int insert2 = userDynamicService.insert4();
        System.out.println(insert2);
    }
}
