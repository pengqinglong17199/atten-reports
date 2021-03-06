/*
 * Copyright (c) 2020, QUANRONG TECHNOLOGY LTD. All rights reserved.
 */
package com.quanroon.atten.reports.job.henan.config;

import com.quanroon.atten.reports.common.ReportType;
import com.quanroon.atten.reports.report.annotation.City;
import com.quanroon.atten.reports.report.constant.*;
import com.quanroon.atten.reports.report.entity.ReportConfig;
import com.quanroon.atten.reports.report.entity.ReportParam;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: Elvis
 * @date: 2020-07-15 17:29
 * @Description:
 */
@City(ReportCityCode.HENAN)
public class HeNanConfig implements ReportConfig {

    //添加企业
    public static final String ADD_COMPANY_METHOD = "Corp.Upload";

    //添加企业
    public static final String ADD_CONTRACTOR_METHOD = "ProjectSubContractor.Add";

    //添加企业方法
    public static final String ADD_PROJECT_METHOD = "Project.Add";

    //添加班组方法
    public static final String ADD_GROUP_METHOD = "Team.Add";

    //添加劳工方法
    public static final String ADD_WORKER_METHOD = "ProjectWorker.Add";

    //添加考勤方法
    public static final String ADD_WORKER_ATTENDANCE_METHOD = "WorkerAttendance.Add";

    //请求地址
    public static final String UPLOAD_URL = "https://gzzf.hrss.henan.gov.cn/foreign/upload/check";

    //请求版本号
    public static final String VERSION = "1.1";

    //调用成功
    public static final Integer SUCCESS_CODE = 0;

    //调用成功
    public static final String RESULT_SUCCESS_CODE = "0";

    //
    public static final String HENAN_TOPIC = "henan_report";

    public static final String HENAN_GROUP = "henan_group";

    public static final String HENAN_TAG = "henan_tag";

    //请求方式
    public static final String CONTENT_TYPE = "application/x-www-form-urlencoded";

    public static Map<String, String> httpStatusMap = new HashMap<>();
    static {
        httpStatusMap.put("0", "成功");
        httpStatusMap.put("-1", "请求参数错误");
        httpStatusMap.put("-2", "签名校验错误");
        httpStatusMap.put("-3", "无API访问权限");
        httpStatusMap.put("-4", "IP校验错误");
        httpStatusMap.put("-5", "访问超过限制");
    }

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
