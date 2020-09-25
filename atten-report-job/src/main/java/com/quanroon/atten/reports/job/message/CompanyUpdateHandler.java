package com.quanroon.atten.reports.job.message;

import com.quanroon.atten.reports.common.RecordType;
import com.quanroon.atten.reports.common.ReportConstant;
import com.quanroon.atten.reports.dao.UpCompanyInfoMapper;
import com.quanroon.atten.reports.entity.UpCompanyInfo;
import com.quanroon.atten.reports.message.BaseHandler;
import com.quanroon.atten.reports.entity.ReportMessage;
import com.quanroon.atten.reports.message.ReportMessageHandler;
import com.quanroon.atten.reports.report.constant.ReportCityCode;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 参建单位更新handler
 * @author 彭清龙
 * @date 2020/7/3 9:56
 */
@ReportMessageHandler(reportType = ReportConstant.TYPE_COMPANY_UPDATE)
public class CompanyUpdateHandler extends BaseHandler {

    @Autowired
    private UpCompanyInfoMapper upCompanyInfoMapper;

    @Override
    protected Integer getProjId(ReportMessage reportMessage) {
        UpCompanyInfo upCompanyInfo = upCompanyInfoMapper.selectByPrimaryKey(Integer.valueOf(reportMessage.getDataId()));
        return upCompanyInfo.getProjId();
    }

    @Override
    protected RecordType isReport(ReportCityCode cityCode) {
        return null;
    }
}