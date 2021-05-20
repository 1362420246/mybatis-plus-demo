package com.qbk.mybatisplusdemo.service.impl;

import com.qbk.mybatisplusdemo.TransactionManage.TransactionSupport;
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
    @TransactionSupport(value = {"masterTrManager", "slave1TrManager"})
    public void insertTx(){
        int insert = userDynamicService.insert3();
        int insert2 = userDynamicService.insert4();
        int a = 10/0;
        System.out.println(insert);
        System.out.println(insert2);
    }
}
