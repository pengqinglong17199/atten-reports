package com.quanroon.atten.reports.message;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(value= ElementType.TYPE)
@Component
@Inherited
public @interface ReportMessageHandler {
    @AliasFor(
            annotation = Component.class
    )
    String value() default "";
    String reportType();
}
