package com.quanroon.atten.reports.report.annotation;

import com.quanroon.atten.reports.report.constant.ReportCityCode;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value= ElementType.TYPE)
@Component
public @interface City {
    ReportCityCode value();
}
