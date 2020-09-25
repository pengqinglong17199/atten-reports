package com.quanroon.atten.reports.api.server.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字段长度注解
 * @author 彭清龙
 * @date 2019-12-25 下午 15:14
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value=ElementType.FIELD)
public @interface Length {

    int value();
    int sort();
}
