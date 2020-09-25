package com.quanroon.atten.reports.report.definition;

import com.quanroon.atten.reports.report.entity.ReportService;
import lombok.Data;

import java.lang.reflect.Method;

@Data
public class ReportCommonDefinition extends BaseDefinition{

    /** 上报功能class*/
    private Class<? extends ReportService> reportClass;

    /** 上报方法*/
    private Method method;

}
