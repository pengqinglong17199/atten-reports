package com.quanroon.atten.reports.job.message;

import com.quanroon.atten.reports.common.RecordType;
import com.quanroon.atten.reports.common.ReportConstant;
import com.quanroon.atten.reports.dao.UpWorkerSignlogInfoMapper;
import com.quanroon.atten.reports.entity.ReportMessage;
import com.quanroon.atten.reports.entity.UpWorkerSignlogInfo;
import com.quanroon.atten.reports.message.BaseHandler;
import com.quanroon.atten.reports.message.ReportMessageHandler;
import com.quanroon.atten.reports.report.constant.ReportCityCode;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 劳工更新handler
 * @author 彭清龙
 * @date 2020/7/3 10:00
 */
@ReportMessageHandler(reportType = ReportConstant.TYPE_WORKER_SIGNLOG)
public class WorkerSignLogHandler extends BaseHandler {

    @Autowired
    private UpWorkerSignlogInfoMapper upWorkerSignlogInfoMapper;

    @Override
    protected Integer getProjId(ReportMessage reportMessage) {
        UpWorkerSignlogInfo upWorkerSignlogInfo = upWorkerSignlogInfoMapper.selectByPrimaryKey(Integer.valueOf(reportMessage.getDataId()));
        return upWorkerSignlogInfo.getProjId();
    }

    @Override
    protected RecordType isReport(ReportCityCode cityCode) {
        return null;
    }
}
