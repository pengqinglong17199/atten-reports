/*
 * Copyright (c) 2020, QUANRONG TECHNOLOGY LTD. All rights reserved.
 */
package com.quanroon.atten.reports.job.henan.entity;

import com.quanroon.atten.reports.common.ReportType;
import com.quanroon.atten.reports.report.annotation.City;
import com.quanroon.atten.reports.report.annotation.Entity;
import com.quanroon.atten.reports.report.constant.ReportCityCode;
import com.quanroon.atten.reports.report.entity.ReportResult;
import lombok.Data;

/**
 * @Auther: Elvis
 * @Date: 2020-07-24 13:58
 * @Description:
 */
@Entity(reportType = {ReportType.proj_report,ReportType.company_report,ReportType.group_report, ReportType.worker_report, ReportType.worker_signlog})
@City(ReportCityCode.HENAN)
@Data
public class HeNanResult implements ReportResult {

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
