package com.quanroon.atten.reports.job.message;

import com.quanroon.atten.reports.common.RecordType;
import com.quanroon.atten.reports.common.ReportConstant;
import com.quanroon.atten.reports.message.BaseHandler;
import com.quanroon.atten.reports.entity.ReportMessage;
import com.quanroon.atten.reports.message.ReportMessageHandler;
import com.quanroon.atten.reports.report.constant.ReportCityCode;

/**
 * 项目更新handler
 * @author 彭清龙
 * @date 2020/7/3 9:58
 */
@ReportMessageHandler(reportType = ReportConstant.TYPE_PROJ_UPDATE)
public class ProjUpdateHandler extends BaseHandler {

    @Override
    protected Integer getProjId(ReportMessage reportMessage) {
        return Integer.valueOf(reportMessage.getDataId());
    }

    @Override
    protected RecordType isReport(ReportCityCode cityCode) {
        return null;
    }
}
