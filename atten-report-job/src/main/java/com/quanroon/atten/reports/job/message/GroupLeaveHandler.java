package com.quanroon.atten.reports.job.message;

import com.quanroon.atten.reports.common.RecordType;
import com.quanroon.atten.reports.common.ReportConstant;
import com.quanroon.atten.reports.dao.UpGroupInfoMapper;
import com.quanroon.atten.reports.entity.ReportMessage;
import com.quanroon.atten.reports.entity.UpGroupInfo;
import com.quanroon.atten.reports.message.BaseHandler;
import com.quanroon.atten.reports.message.ReportMessageHandler;
import com.quanroon.atten.reports.report.constant.ReportCityCode;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 班组离场handler
 * @author 彭清龙
 * @date 2020/7/3 9:57
 */
@ReportMessageHandler(reportType = ReportConstant.TYPE_GROUP_LEAVE)
public class GroupLeaveHandler extends BaseHandler {

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
