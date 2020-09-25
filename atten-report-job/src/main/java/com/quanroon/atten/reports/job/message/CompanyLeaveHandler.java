package com.quanroon.atten.reports.job.message;

import com.quanroon.atten.reports.common.RecordType;
import com.quanroon.atten.reports.common.ReportConstant;
import com.quanroon.atten.reports.dao.UpCompanyInMapper;
import com.quanroon.atten.reports.entity.UpCompanyIn;
import com.quanroon.atten.reports.message.BaseHandler;
import com.quanroon.atten.reports.entity.ReportMessage;
import com.quanroon.atten.reports.message.ReportMessageHandler;
import com.quanroon.atten.reports.report.constant.ReportCityCode;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 参建单位离场handler
 * @author 彭清龙
 * @date 2020/7/3 9:55
 */
@ReportMessageHandler(reportType = ReportConstant.TYPE_COMPANY_LEAVE)
public class CompanyLeaveHandler extends BaseHandler {

    @Autowired
    private UpCompanyInMapper upCompanyInMapper;

    @Override
    protected Integer getProjId(ReportMessage reportMessage) {
        UpCompanyIn upCompanyIn = upCompanyInMapper.selectByPrimaryKey(Integer.valueOf(reportMessage.getDataId()));
        return upCompanyIn.getProjId();
    }

    @Override
    protected RecordType isReport(ReportCityCode cityCode) {
        return null;
    }
}
