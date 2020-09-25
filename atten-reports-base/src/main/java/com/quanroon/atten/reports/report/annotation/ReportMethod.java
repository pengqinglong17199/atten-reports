package com.quanroon.atten.reports.report.annotation;

import com.quanroon.atten.reports.common.ReportType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 上报功能注解
 * @author 彭清龙
 * @date 2020-05-06 21:11:40
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value= ElementType.METHOD)
public @interface ReportMethod {

    ReportType[] value();
}
