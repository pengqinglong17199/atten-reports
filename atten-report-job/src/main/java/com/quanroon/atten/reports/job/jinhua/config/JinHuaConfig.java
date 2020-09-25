/*
 * Copyright (c) 2020, QUANRONG TECHNOLOGY LTD. All rights reserved.
 */
package com.quanroon.atten.reports.job.jinhua.config;

import com.quanroon.atten.reports.common.ReportType;
import com.quanroon.atten.reports.report.annotation.City;
import com.quanroon.atten.reports.report.constant.*;
import com.quanroon.atten.reports.report.entity.ReportConfig;
import com.quanroon.atten.reports.report.entity.ReportParam;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: raojun
 * @Date: 2020/6/29 10:34
 * @Description:
 */
@City(ReportCityCode.JINHUA)
public class JinHuaConfig implements ReportConfig {
    //新增
    public static final String UPLOAD_ADD = "ADD";

    //更新
    public static final String UPLOAD_UPDATE = "UPDATE";

    //添加班组方法
    public static final String ADD_GROUP_METHOD = "Team.Add";

    //更新班组方法
    public static final String UPDATE_GROUP_METHOD = "Team.Update";

    //查询班组
    public static final String QUERY_GROUP_METHOD = "Team.Query";

    public static final String QUERY_RESULT = "AsyncHandleResult.Query";

    //添加劳工方法
    public static final String ADD_WORKER_METHOD = "ProjectWorker.Add";

    //添加考勤方法
    public static final String ADD_WORKER_ATTENDANCE_METHOD = "WorkerAttendance.Add";

    //请求版本号
    public static final String VERSION = "1.0";

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
