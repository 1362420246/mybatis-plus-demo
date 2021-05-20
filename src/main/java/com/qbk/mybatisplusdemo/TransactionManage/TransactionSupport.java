package com.qbk.mybatisplusdemo.TransactionManage;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 多数据源事务支持
 */
@Documented
@Target({METHOD, TYPE})
@Retention(RUNTIME)
public @interface TransactionSupport {
    String[] value() default {};
}
