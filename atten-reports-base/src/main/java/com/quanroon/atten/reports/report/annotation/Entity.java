package com.quanroon.atten.reports.report.annotation;

import com.quanroon.atten.reports.common.ReportType;
import com.quanroon.atten.reports.report.constant.DataMode;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 请求实体注解
 * @author 彭清龙
 * @date 2020-05-06 21:11:49
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value= ElementType.TYPE)
@Component
public @interface Entity {

    DataMode dataMode() default DataMode.JSON;

    ReportType[] reportType();
}
