package com.quanroon.atten.reports.report.annotation;

import com.quanroon.atten.reports.report.ScannerBean;
import com.quanroon.atten.reports.report.factory.BusinessFactory;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author quanroon.ysq
 * @version 1.0.0
 * @content 开启上报服务
 * @date 2020/7/22 11:03
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
@Import({
        BusinessFactory.class,
        ScannerBean.class})
public @interface EnableReport {
}
