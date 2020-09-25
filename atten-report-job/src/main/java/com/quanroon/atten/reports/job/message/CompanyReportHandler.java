package com.quanroon.atten.reports.job.message;

import com.quanroon.atten.reports.common.RecordType;
import com.quanroon.atten.reports.common.ReportConstant;
import com.quanroon.atten.reports.message.BaseHandler;
import com.quanroon.atten.reports.entity.ReportMessage;
import com.quanroon.atten.reports.message.ReportMessageHandler;
import com.quanroon.atten.reports.report.constant.ReportCityCode;

/**
 * 参建单位上报handler
 * @author 彭清龙
 * @date 2020/7/3 9:56
 */
@ReportMessageHandler(reportType = ReportConstant.TYPE_COMPANY_REPORT)
public class CompanyReportHandler extends BaseHandler {

    @Override
    protected Integer getProjId(ReportMessage reportMessage) {
        return null;
    }

    @Override
    protected RecordType isReport(ReportCityCode cityCode) {
        return null;
    }
}
