package com.quanroon.atten.reports.job.message;

import com.quanroon.atten.reports.common.RecordType;
import com.quanroon.atten.reports.common.ReportConstant;
import com.quanroon.atten.reports.dao.UpWorkerInMapper;
import com.quanroon.atten.reports.entity.UpWorkerIn;
import com.quanroon.atten.reports.message.BaseHandler;
import com.quanroon.atten.reports.entity.ReportMessage;
import com.quanroon.atten.reports.message.ReportMessageHandler;
import com.quanroon.atten.reports.report.constant.ReportCityCode;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 劳工离场handler
 * @author 彭清龙
 * @date 2020/7/3 9:59
 */
@ReportMessageHandler(reportType = ReportConstant.TYPE_WORKER_LEAVE)
public class WorkerLeaveHandler extends BaseHandler {

    @Autowired private UpWorkerInMapper upWorkerInMapper;

    @Override
    protected Integer getProjId(ReportMessage reportMessage) {
        UpWorkerIn upWorkerIn = upWorkerInMapper.selectByPrimaryKey(Integer.valueOf(reportMessage.getDataId()));
        return upWorkerIn.getProjId();
    }

    @Override
    protected RecordType isReport(ReportCityCode cityCode) {
        return null;
    }
}
