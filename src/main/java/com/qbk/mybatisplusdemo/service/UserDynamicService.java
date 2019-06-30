package com.qbk.mybatisplusdemo.service;

import com.baomidou.dynamic.datasource.annotation.DS;

import java.util.List;
import java.util.Map;

/**
 * 多数据源
 */
public interface UserDynamicService {

    public int insert ();

    public List<Map<String, Object>> selectAll();

    public List<Map<String, Object>> selectByCondition();
}
