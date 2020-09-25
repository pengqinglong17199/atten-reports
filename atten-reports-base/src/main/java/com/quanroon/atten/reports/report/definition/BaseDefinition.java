package com.quanroon.atten.reports.report.definition;

import com.quanroon.atten.reports.common.ReportType;
import com.quanroon.atten.reports.report.constant.ReportCityCode;
import lombok.Data;

@Data
public class BaseDefinition {

    /** 上报城市code*/
    protected ReportCityCode cityCode;

    /** 本定义属于哪个上报功能*/
    protected ReportType reportType;

    /** 定义所属上报功能数组*/
    protected ReportType[] reportTypeArr;

    public String getKey(){
        return cityCode.code() + reportType;
    }
}
