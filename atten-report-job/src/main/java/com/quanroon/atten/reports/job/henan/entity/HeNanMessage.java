/*
 * Copyright (c) 2020, QUANRONG TECHNOLOGY LTD. All rights reserved.
 */
package com.quanroon.atten.reports.job.henan.entity;

import lombok.Data;

/**
 * @Auther: Elvis
 * @Date: 2020-07-22 11:43
 * @Description:
 */
@Data
public class HeNanMessage {

    /**
     * 数据ID
     */
    private String DataId;

    /**
     * 上报类型
     */
    private String reportType;

    /**
     * 上报查询代码
     */
    private String requestCode;

    /**
     * openapi的查询代码
     */
    private String recordCode;

    public HeNanMessage(){}

    public HeNanMessage(String dataId, String reportType, String requestCode, String recordCode) {
        DataId = dataId;
        this.reportType = reportType;
        this.requestCode = requestCode;
        this.recordCode = recordCode;
    }
}
