package com.quanroon.atten.reports.report.definition;

import com.quanroon.atten.reports.report.constant.DataMode;
import com.quanroon.atten.reports.report.entity.ReportResult;
import lombok.Data;

import java.lang.reflect.Method;

/**
 * 上报返回结果定义 定义了返回结果基本属性
 * @author 彭清龙
 * @date 2020-05-26 14:59:21
 */
@Data
public class ReportResultDefinition extends BaseDefinition{

    /** 参数序列化格式*/
    private DataMode dataFormat;

    /** 上报实体参数*/
    private Class<? extends ReportResult> param;

    /** 上报实体初始化方法*/
    private Method initMethod;

}