package com.quanroon.atten.reports.job.shenzhen.common;

import com.quanroon.atten.reports.common.ReportType;
import com.quanroon.atten.reports.report.annotation.City;
import com.quanroon.atten.reports.report.annotation.Entity;
import com.quanroon.atten.reports.report.constant.ReportCityCode;
import com.quanroon.atten.reports.report.entity.ReportResult;
import lombok.Data;

/**
 * 深圳上报结果
 *
 * @Author: ysx
 * @Date: 2020/8/12
 */

@Data
@Entity(reportType = {ReportType.company_enter, ReportType.company_leave, ReportType.group_report, ReportType.worker_enter,
        ReportType.worker_leave, ReportType.worker_signlog})
@City(ReportCityCode.SHENZHEN)
public class ShenZhenResult implements ReportResult {

    /** 00结果成功,其它结果失败*/
    private String code;

    /** 上报结果描述*/
    private String message;

    @Override
    public boolean isSuccess() {
        return "00".equals(code);
    }

    @Override
    public String getMessage() {
        return message;
    }

}
