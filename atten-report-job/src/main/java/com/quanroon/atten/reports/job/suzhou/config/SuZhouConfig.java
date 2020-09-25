package com.quanroon.atten.reports.job.suzhou.config;

import com.quanroon.atten.reports.common.ReportType;
import com.quanroon.atten.reports.report.annotation.City;
import com.quanroon.atten.reports.report.constant.*;
import com.quanroon.atten.reports.report.entity.ReportConfig;
import com.quanroon.atten.reports.report.entity.ReportParam;
import org.springframework.beans.factory.annotation.Value;

import java.util.Map;

/**
* 宿州上报常量
*
* @Author: ysx
* @Date: 2020/8/4
*/
@City(ReportCityCode.SUZHOU)
public class SuZhouConfig implements ReportConfig {

    /** 住建局要求建立netty通道所需的数据*/
    public static final String CLIENT_NAME = "&clientname=abc";

    /** 上报成功标识*/
    public static final String SUCCESS_FLAG = "200";

    /** 上报失败标识*/
    public static final String FAILURE_FLAG = "1000";

    /** 上报state参数*/
    public static final String REPORT_STATE = "200";

    /** 上报hash参数*/
    public static final String REPORT_HASH = "1932342423";

    /** 获取token*/
    public static final String TOKEN_GET = "/open-api-service/check-authority";

    /** 上报班组*/
    public static final String GROUP_ADD = "/open-api-service/insert-group";

    /** 修改班组*/
    public static final String GROUP_UPDATE = "/open-api-service/update-group";

    /** 删除班组*/
    public static final String GROUP_DELETE = "/open-api-service/delete-group";

    /** 上报班组负责人*/
    public static final String GROUP_LEADER = "/open-api-service/modify-group-leader";

    /** 上报劳工信息*/
    public static final String WORKER_ADD = "/open-api-service/add-labour";

    /** 删除劳工*/
    public static final String WORKER_DELETE = "/open-api-service/delete-labour";

    /** 上报考勤*/
    public static final String WORKER_SIGNLOG = "/open-api-service/add-trace";

    /** 上报合同*/
    public static final String WORKER_CONTRACT = "/open-api-service/upload-contract";

    @Override
    public String getUrl(ReportType reportType) {
        return null;
    }

    @Override
    public RequestType getRequestType() {
        return null;
    }

    @Override
    public RequestFormat getRequestFormat() {
        return null;
    }

    @Override
    public RequestMode getRequestMode() {
        return null;
    }

    @Override
    public AuthenticationMode getAuth() {
        return null;
    }

    @Override
    public Map<String, String> getSignMap(ReportParam reportParam) {
        return null;
    }

}
