/*
 * Copyright (c) 2020, QUANRONG TECHNOLOGY LTD. All rights reserved.
 */
package com.quanroon.atten.reports.job.henan.service;

import com.quanroon.atten.reports.entity.ReportMessage;
import com.quanroon.atten.reports.report.entity.ReportResult;

/**
 * @Auther: Elvis
 * @Date: 2020-07-15 16:47
 * @Description: 河南上报
 */
public interface HeNanService {

    /**
     * 上报项目
     * @param reportMessage
     * @return 河南上报
     */
    ReportResult reportProject(ReportMessage reportMessage);

    /**
     * 上报企业
     * @param reportMessage
     * @return
     */
    ReportResult reportCompany(ReportMessage reportMessage);

    /**
     * 上报参建单位
     * @param reportMessage
     * @return
     */
    ReportResult reportContractor(ReportMessage reportMessage);

    /**
     * 上报班组
     * @param reportMessage
     * @return
     */
    ReportResult reportTeam(ReportMessage reportMessage);

    /**
     * 上报劳工
     * @param reportMessage
     * @return
     */
    ReportResult reportProjectWorker(ReportMessage reportMessage);

    /**
     * 上报考勤
     * @param reportMessage
     * @return
     */
    ReportResult reportWorkerAttendance(ReportMessage reportMessage);

/*    *//**
     * 处理查询上报结果
     * @param heNanMessage
     * @return
     *//*
    String dealWithFindResult(HeNanMessage heNanMessage);*/
}
