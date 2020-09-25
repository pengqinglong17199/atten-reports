package com.quanroon.atten.reports.job.suzhou.entity;

import com.quanroon.atten.reports.common.ReportType;
import com.quanroon.atten.reports.report.annotation.City;
import com.quanroon.atten.reports.report.annotation.Entity;
import com.quanroon.atten.reports.report.constant.ReportCityCode;
import com.quanroon.atten.reports.report.entity.ReportResult;
import lombok.Data;

/**
* 宿州上报结果
*
* @Author: ysx
* @Date: 2020/8/4
*/
@Data
@Entity(reportType = {ReportType.group_report, ReportType.worker_report, ReportType.worker_signlog, ReportType.worker_enter})
@City(ReportCityCode.SUZHOU)
public class SuZhouResult implements ReportResult {

    /** 200结果成功,1000结果失败*/
    private String code;

    /** 上报结果描述*/
    private String message;

    @Override
    public boolean isSuccess() {
        return "200".equals(code);
    }

    @Override
    public String getMessage() {
        return message;
    }

}
