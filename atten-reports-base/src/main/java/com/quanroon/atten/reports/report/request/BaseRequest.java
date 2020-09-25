package com.quanroon.atten.reports.report.request;

import com.quanroon.atten.reports.report.definition.ReportParamDefinition;
import com.quanroon.atten.reports.report.entity.ReportParam;

/**
 * 上报请求基类
 * @author 彭清龙
 * @date 2020/7/14 16:17
 */
public interface BaseRequest {

    /**
     * 调用
     * @return java.lang.String
     * @author 彭清龙
     * @date 2020/7/14 16:18
     */
    String call(ReportParam reportParam, ReportParamDefinition paramDefinition);
}
