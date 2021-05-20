package com.qbk.mybatisplusdemo.service;

import java.util.List;
import java.util.Map;

/**
 * 多数据源
 */
public interface UserDynamicService {

    int insert ();

    int insert2 ();

    int insert3 ();

    int insert4 ();

    List<Map<String, Object>> selectAll();

    List<Map<String, Object>> selectByCondition();
}
