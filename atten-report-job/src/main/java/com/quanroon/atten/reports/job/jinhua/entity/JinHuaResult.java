/*
 * Copyright (c) 2020, QUANRONG TECHNOLOGY LTD. All rights reserved.
 */
package com.quanroon.atten.reports.job.jinhua.entity;


import com.quanroon.atten.reports.common.ReportType;
import com.quanroon.atten.reports.report.annotation.City;
import com.quanroon.atten.reports.report.annotation.Entity;
import com.quanroon.atten.reports.report.constant.ReportCityCode;
import com.quanroon.atten.reports.report.entity.ReportResult;

/**
 * @Auther: raojun
 * @Date: 2020/7/20 14:13
 * @Description:
 */
@Entity(reportType = {ReportType.group_report, ReportType.worker_report, ReportType.worker_signlog})
@City(ReportCityCode.JINHUA)
public class JinHuaResult implements ReportResult {

    private String code;//0结果成功1结果失败
    private String message;

    @Override
    public boolean isSuccess() {
        return "0".equals(code);
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
