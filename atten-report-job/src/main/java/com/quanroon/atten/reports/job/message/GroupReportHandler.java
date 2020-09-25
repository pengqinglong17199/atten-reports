package com.quanroon.atten.reports.job.message;

import com.quanroon.atten.reports.common.RecordType;
import com.quanroon.atten.reports.common.ReportConstant;
import com.quanroon.atten.reports.dao.UpGroupInfoMapper;
import com.quanroon.atten.reports.entity.UpGroupInfo;
import com.quanroon.atten.reports.message.BaseHandler;
import com.quanroon.atten.reports.entity.ReportMessage;
import com.quanroon.atten.reports.message.ReportMessageHandler;
import com.quanroon.atten.reports.report.constant.ReportCityCode;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 班组上报handler
 * @author 彭清龙
 * @date 2020/7/3 9:58
 */
@ReportMessageHandler(reportType = ReportConstant.TYPE_GROUP_REPORT)
public class GroupReportHandler extends BaseHandler {

    @Autowired private UpGroupInfoMapper upGroupInfoMapper;

    @Override
    protected Integer getProjId(ReportMessage reportMessage) {
        UpGroupInfo upGroupInfo = upGroupInfoMapper.selectByPrimaryKey(Integer.valueOf(reportMessage.getDataId()));
        return upGroupInfo.getProjId();
    }

    @Override
    protected RecordType isReport(ReportCityCode cityCode) {
        return null;
    }
}
