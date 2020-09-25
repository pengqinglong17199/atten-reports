package com.quanroon.atten.reports.report.resolver;

import com.quanroon.atten.reports.report.constant.ReportCityCode;

/**
 * 上报bean解析器
 * @author 彭清龙
 * @date 2020/7/7 10:17
 */
public interface ReportResolver {

    /**
     * 解析上报bean
     * @param cityCode, obj
     * @return void
     * @author 彭清龙
     * @date 2020/7/7 11:31
     */
    void resolver(ReportCityCode cityCode, Object obj) throws Exception;
}
