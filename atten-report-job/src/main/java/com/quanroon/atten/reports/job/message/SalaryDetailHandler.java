package com.quanroon.atten.reports.job.message;

import com.quanroon.atten.reports.common.RecordType;
import com.quanroon.atten.reports.common.ReportConstant;
import com.quanroon.atten.reports.dao.UpPayrollInfoMapper;
import com.quanroon.atten.reports.entity.ReportMessage;
import com.quanroon.atten.reports.entity.UpPayrollInfo;
import com.quanroon.atten.reports.message.BaseHandler;
import com.quanroon.atten.reports.message.ReportMessageHandler;
import com.quanroon.atten.reports.report.constant.ReportCityCode;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author quanroon.ysq
 * @version 1.0.0
 * @content 月工资单明细 处理器
 * @date 2020/7/24 9:13
 */
@ReportMessageHandler(reportType = ReportConstant.TYPE_PAYROLL_DETAIL)
public class SalaryDetailHandler extends BaseHandler {
    @Autowired private UpPayrollInfoMapper payrollInfoMapper;
    @Override
    protected Integer getProjId(ReportMessage reportMessage) {
        UpPayrollInfo upPayrollInfo = payrollInfoMapper.selectPayrollByDetailId(Integer.valueOf(reportMessage.getDataId()));
        return upPayrollInfo.getProjId();
    }

    @Override
    protected RecordType isReport(ReportCityCode cityCode) {
        return null;
    }
}
