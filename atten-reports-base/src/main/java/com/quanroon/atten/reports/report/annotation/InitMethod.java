package com.quanroon.atten.reports.report.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 请求参数实体初始化时的方法注解
 * @author 彭清龙
 * @date 2020-05-22 13:58:29
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value= ElementType.METHOD)
public @interface InitMethod {
}
