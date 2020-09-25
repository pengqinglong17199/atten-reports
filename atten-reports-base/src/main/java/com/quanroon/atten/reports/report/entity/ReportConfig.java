package com.quanroon.atten.reports.report.entity;

import com.quanroon.atten.reports.common.ReportType;
import com.quanroon.atten.reports.entity.UpParams;
import com.quanroon.atten.reports.report.constant.AuthenticationMode;
import com.quanroon.atten.reports.report.constant.RequestFormat;
import com.quanroon.atten.reports.report.constant.RequestMode;
import com.quanroon.atten.reports.report.constant.RequestType;

import java.util.Map;

/**
 * 上报配置
 * @author 彭清龙
 * @date 2020-05-06 21:18:31
 */
public interface ReportConfig {

    /**
     * 获得调用的url
     * @return java.lang.String
     * @author 彭清龙
     * @date 2020/5/26 15:32
     */
    String getUrl(ReportType reportType);

    /**
     * 获得http请求方式 post和get
     * @return java.lang.String
     * @author 彭清龙
     * @date 2020/5/26 16:04
     */
    RequestType getRequestType();

    /**
     * 获得请求的数据封装格式
     * @return com.quanroon.atten.reports.report.constant.RequestFormat
     * @author 彭清龙
     * @date 2020/7/14 16:32
     */
    RequestFormat getRequestFormat();

    /**
     * 获得请求方式
     * @return com.quanroon.atten.reports.report.constant.RequestMode
     * @author 彭清龙
     * @date 2020/7/14 14:54
     */
    RequestMode getRequestMode();

    /**
     * 获取鉴权规则
     * @return java.lang.String
     * @author 彭清龙
     * @date 2020/5/26 15:41
     */
    AuthenticationMode getAuth();

    /***
     * 获取实际密钥Map
     * @return java.lang.String
     * @author 彭清龙
     * @date 2020/5/26 15:37
     */
    Map<String, String> getSignMap(ReportParam reportParam);


}
