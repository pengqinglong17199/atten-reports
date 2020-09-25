package com.quanroon.atten.reports.job.message;

import com.quanroon.atten.reports.common.RecordType;
import com.quanroon.atten.reports.common.ReportConstant;
import com.quanroon.atten.reports.dao.UpSalaryInfoMapper;
import com.quanroon.atten.reports.entity.ReportMessage;
import com.quanroon.atten.reports.entity.UpSalaryInfo;
import com.quanroon.atten.reports.message.BaseHandler;
import com.quanroon.atten.reports.message.ReportMessageHandler;
import com.quanroon.atten.reports.report.constant.ReportCityCode;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author quanroon.ysq
 * @version 1.0.0
 * @content 上报项目工资专户 处理器
 * @date 2020/7/24 9:11
 */
@ReportMessageHandler(reportType = ReportConstant.TYPE_ACCOUNT_REPORT)
public class SalaryAccountHandler extends BaseHandler {
    @Autowired private UpSalaryInfoMapper salaryInfoMapper;
    @Override
    protected Integer getProjId(ReportMessage reportMessage) {
        UpSalaryInfo upSalaryInfo = salaryInfoMapper.selectByPrimaryKey(Integer.valueOf(reportMessage.getDataId()));
        return upSalaryInfo.getProjId();
    }

    @Override
    protected RecordType isReport(ReportCityCode cityCode) {
        return null;
    }
}
