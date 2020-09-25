package com.quanroon.atten.reports.entity.base;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author quanroon.ysq
 * @version 1.0.0
 * @content 标记实体类，用于消息产生属性
 * @date 2020/8/6 8:52
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value= ElementType.TYPE)
public @interface TableEntity {
    String value() default "";
}
