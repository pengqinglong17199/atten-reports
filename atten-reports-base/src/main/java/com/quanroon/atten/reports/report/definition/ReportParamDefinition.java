package com.quanroon.atten.reports.report.definition;

import com.quanroon.atten.reports.report.constant.DataMode;
import com.quanroon.atten.reports.report.entity.ReportParam;
import lombok.Data;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 上报参数定义 定义了上报参数的初始化方法以及获取参数方法
 * @author 彭清龙
 * @date 2020-05-26 14:59:21
 */
@Data
public class ReportParamDefinition  extends BaseDefinition{

    /** 参数序列化格式*/
    private DataMode dataFormat;

    /** 上报实体参数*/
    private Class<? extends ReportParam> param;

    /** 上报实体初始化方法*/
    private Method initMethod;

    /** 必填字段*/
    private List<Field> required;

    /** 非必填字段*/
    private List<Field> notRequired;


}