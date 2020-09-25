package com.quanroon.atten.reports.job.message;

import com.quanroon.atten.reports.common.RecordType;
import com.quanroon.atten.reports.common.ReportConstant;
import com.quanroon.atten.reports.dao.UpDeviceInfoMapper;
import com.quanroon.atten.reports.entity.UpDeviceInfo;
import com.quanroon.atten.reports.message.BaseHandler;
import com.quanroon.atten.reports.entity.ReportMessage;
import com.quanroon.atten.reports.message.ReportMessageHandler;
import com.quanroon.atten.reports.report.constant.ReportCityCode;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 上报考勤机handler
 * @author 彭清龙
 * @date 2020/7/3 9:56
 */
@ReportMessageHandler(reportType = ReportConstant.TYPE_DEVICE_REPORT)
public class DeviceReportHandler extends BaseHandler {

    @Autowired
    private UpDeviceInfoMapper upDeviceInfoMapper;

    @Override
    protected Integer getProjId(ReportMessage reportMessage) {
        UpDeviceInfo upDeviceInfo = upDeviceInfoMapper.selectByPrimaryKey(Integer.valueOf(reportMessage.getDataId()));
        return upDeviceInfo.getProjId();
    }

    @Override
    protected RecordType isReport(ReportCityCode cityCode) {
        return null;
    }
}
