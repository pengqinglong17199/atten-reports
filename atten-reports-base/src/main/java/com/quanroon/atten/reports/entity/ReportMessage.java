package com.quanroon.atten.reports.entity;

import com.quanroon.atten.reports.common.ReportType;
import lombok.Data;

/**
 * 上报消息实体
 * @author 彭清龙
 * @date 2020/7/1 16:38
 */
@Data
public class ReportMessage {

    private String requestCode; // 上报唯一编码
    private String dataId;     // 上报业务id
    private ReportType reportType;  // 上报类型

    public ReportMessage(String requestCode, String dataId, ReportType reportType) {
        this.requestCode = requestCode;
        this.dataId = dataId;
        this.reportType = reportType;
    }

    public ReportMessage(){}

}
