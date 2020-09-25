package com.quanroon.atten.reports.job.bengbu.entity;

import com.quanroon.atten.reports.common.ReportType;
import com.quanroon.atten.reports.report.annotation.City;
import com.quanroon.atten.reports.report.annotation.Entity;
import com.quanroon.atten.reports.report.constant.ReportCityCode;
import com.quanroon.atten.reports.report.entity.ReportResult;

/**
 * 蚌埠上报结果
 * @Auther: 罗森林
 * @Date: 2020-08-27 14:00
 * @Description:
 */
@Entity(reportType = {ReportType.group_report, ReportType.worker_report, ReportType.worker_signlog})
@City(ReportCityCode.BENGBU)
public class BengBuResult implements ReportResult {
    //0结果成功1结果失败
    private String code;
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
