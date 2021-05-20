package com.qbk.mybatisplusdemo;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qbk.mybatisplusdemo.entity.User;
import com.qbk.mybatisplusdemo.mapper.UserMapper;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;
import java.util.Map;

/**
 * 条件构造器 测试 （带分页插件）
 * https://blog.csdn.net/m0_37034294/article/details/82917234
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class QueryWrapperTests {

    @Autowired
    private UserMapper mapper;
    /*
    wapper介绍 ：
    Wrapper ： 条件构造抽象类，最顶端父类，抽象类中提供4个方法西面贴源码展示
    AbstractWrapper ： 用于查询条件封装，生成 sql 的 where 条件
    AbstractLambdaWrapper ： Lambda 语法使用 Wrapper统一处理解析 lambda 获取 column。
    LambdaQueryWrapper ：看名称也能明白就是用于Lambda语法使用的查询Wrapper
    LambdaUpdateWrapper ： Lambda 更新封装Wrapper
    QueryWrapper ： Entity 对象封装操作类，不是用lambda语法
    UpdateWrapper ： Update 条件封装，用于Entity对象更新操作
     */

    /*
     QueryWrapper(LambdaQueryWrapper) 和 UpdateWrapper(LambdaUpdateWrapper) 的父类
    用于生成 sql 的 where 条件, entity 属性也用于生成 sql 的 where 条件
    注意: entity 生成的 where 条件与 使用各个 api 生成的 where 条件没有任何关联行为
     */

    /*警告: 不支持以及不赞成在 RPC 调用中把 Wrapper 进行传输
    wrapper 很重
    传输 wrapper 可以类比为你的 controller 用 map 接收值(开发一时爽,维护火葬场)
    正确的 RPC 调用姿势是写一个 DTO 进行传输,被调用方再根据 DTO 执行相应的操作
    我们拒绝接受任何关于 RPC 传输 Wrapper 报错相关的 issue 甚至 pr
     */

    /*
        条件参数说明
        查询方式：说明
        setSqlSelect：设置 SELECT 查询字段
        where：WHERE 语句，拼接 +?WHERE 条件
        and：AND 语句，拼接 +?AND 字段=值
        andNew：AND 语句，拼接 +?AND (字段=值)
        or：OR 语句，拼接 +?OR 字段=值
        orNew：OR 语句，拼接 +?OR (字段=值)
        eq：等于=
        allEq：基于 map 内容等于=
        ne：不等于<>
        gt：大于>
        ge：大于等于>=
        lt：小于<
        le：小于等于<=
        like：模糊查询 LIKE
        notLike：模糊查询 NOT LIKE
        in：IN 查询
        notIn：NOT IN 查询
        isNull：NULL 值查询
        isNotNull：IS NOT NULL
        groupBy：分组 GROUP BY
        having：HAVING 关键词
        orderBy：排序 ORDER BY
        orderAsc：ASC 排序 ORDER BY
        orderDesc：DESC 排序 ORDER BY
        exists：EXISTS 条件语句
        notExists：NOT EXISTS 条件语句
        between：BETWEEN 条件语句
        notBetween：NOT BETWEEN 条件语句
        addFilter：自由拼接 SQL
        last：拼接在最后，例如：last("LIMIT 1")
     */


    /**
     * <p>
     * 根据根据 entity 条件，删除记录,QueryWrapper实体对象封装操作类（可以为 null）
     * 下方获取到queryWrapper后删除的查询条件为name字段为null的and年龄大于等于12的and email字段不为null的
     * 同理写法条件添加的方式就不做过多介绍了。
     * </p>
     */
    @Test
    public void delete() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .isNull("name")
                .ge("age", 12)
                .isNotNull("email");
        int delete = mapper.delete(queryWrapper);
        System.out.println("delete return count = " + delete);
    }


    /**
     * <p>
     * 根据 entity 条件，查询一条记录,
     * 这里和上方删除构造条件一样，只是seletOne返回的是一条实体记录，当出现多条时会报错
     * </p>
     */
    @Test
    public void selectOne() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", "Jack");

        User user = mapper.selectOne(queryWrapper);
        System.out.println(user);
    }


    /**
     * <p>
     * 根据 Wrapper 条件，查询总记录数
     * </p>
     *
     *  queryWrapper 实体对象
     */
    @Test
    public void selectCount() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", "lqf");

        Integer count = mapper.selectCount(queryWrapper);
        System.out.println(count);
    }


    /**
     * <p>
     * 根据 entity 条件，查询全部记录
     * </p>
     *
     *  queryWrapper 实体对象封装操作类（可以为 null）为null查询全部
     */
    @Test
    public void selectList() {
        List<User> list = mapper.selectList(null);

        System.out.println(list);
    }

    /**
     * <p>
     * 根据 Wrapper 条件，查询全部记录
     * </p>
     *
     *  queryWrapper 实体对象封装操作类（可以为 null）
     */
    @Test
    public void selectMaps() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNotNull("name");
        List<Map<String, Object>> maps = mapper.selectMaps(queryWrapper);
        for (Map<String, Object> map : maps) {
            System.out.println(map);
        }
    }

    /**
     * <p>
     * 根据 entity 条件，查询全部记录（并翻页）
     * </p>

     * 这里需要在项目中加入分页插件
     *     @Bean
     *     public PaginationInterceptor paginationInterceptor() {
     *         return new PaginationInterceptor();
     *     }
     */

    @Test
    public void selectPage() {
        Page<User> page = new Page<>(3, 2);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //排序
        queryWrapper.orderByDesc("age","name");
        //分页
        IPage<User> userIPage = mapper.selectPage(page, queryWrapper);
        long current = userIPage.getCurrent();
        long size = userIPage.getSize();
        long pages = userIPage.getPages();
        long total = userIPage.getTotal();
        // 分页记录列表
        List<User> records = userIPage.getRecords();
        System.out.println(userIPage);
        System.out.println(JSONObject.toJSONString(userIPage));
        System.out.println("当前页数 ------> " +current);
        System.out.println("当前每页显示数 ------> " + size);
        System.out.println("总页数 ------> " + pages);
        System.out.println("总条数 ------> " +total);
        System.out.println(records);

    }

    /**
     * <p>
     * 根据 Wrapper 条件，查询全部记录（并翻页）
     * </p>
     *
     *  page         分页查询条件
     *  queryWrapper 实体对象封装操作类
     */
    @Test
    public void selectMapsPage() {
        IPage page = new Page<>(1, 5);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //排序
        queryWrapper.orderByDesc("age","name");
        //分页
        IPage<Map<String, Object>> mapIPage = mapper.selectMapsPage(page, queryWrapper);
        System.out.println( ToStringBuilder.reflectionToString(mapIPage) );
        System.out.println(JSONObject.toJSONString(mapIPage));
        // 分页记录列表
        List<Map<String, Object>> records = mapIPage.getRecords();
        System.out.println(records);
        System.out.println(JSONObject.toJSONString(records));
    }



    /**
     * <p>
     * 根据 whereEntity 条件，更新记录
     * </p>
     *
     *  entity        实体对象 (set 条件值,不能为 null)
     *  updateWrapper 实体对象封装操作类（可以为 null,里面的 entity 用于生成 where 语句）
     */
    @Test
    public void update() {

        //修改值
        User user = new User();
        user.setName("zhangsan");

        //修改条件s
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        userUpdateWrapper.eq("name", "Tom");

        int update = mapper.update(user, userUpdateWrapper);

        System.out.println(update);
    }


}
