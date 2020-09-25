package com.quanroon.atten.reports.job.message;

import com.quanroon.atten.commons.exception.BusinessException;
import com.quanroon.atten.reports.common.RecordStatus;
import com.quanroon.atten.reports.common.RecordType;
import com.quanroon.atten.reports.common.ReportConstant;
import com.quanroon.atten.reports.dao.UpProjectAuthMapper;
import com.quanroon.atten.reports.entity.ReportMessage;
import com.quanroon.atten.reports.entity.UpProjectAuth;
import com.quanroon.atten.reports.entity.UpRecord;
import com.quanroon.atten.reports.entity.example.UpProjectAuthExample;
import com.quanroon.atten.reports.exception.RequestCodeException;
import com.quanroon.atten.reports.message.BaseHandler;
import com.quanroon.atten.reports.message.ReportMessageHandler;
import com.quanroon.atten.reports.report.constant.ReportCityCode;
import com.quanroon.atten.reports.report.excepotion.NotParameterException;
import com.quanroon.atten.reports.report.excepotion.NotReportCityException;
import com.quanroon.atten.reports.service.UpRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import sun.rmi.runtime.Log;

import java.util.List;
import java.util.Objects;


/**
 * 上报项目handler
 * @author 彭清龙
 * @date 2020/7/3 8:50
 */
@ReportMessageHandler(reportType = ReportConstant.TYPE_PROJ_REPORT)
public class ProjReportHandler extends BaseHandler {

    @Autowired private UpProjectAuthMapper upProjectAuthMapper;
    @Autowired private UpRecordService upRecordServiceImpl;


    protected UpRecord resolver(ReportMessage reportMessage) throws RequestCodeException, NotReportCityException, NotParameterException,InstantiationException, IllegalAccessException{
        //上报项目业务
        UpRecord upRecord = super.resolver(reportMessage);

        // 查询项目密钥
        UpProjectAuthExample example = new UpProjectAuthExample();
        example.createCriteria().andProjIdEqualTo(Integer.valueOf(reportMessage.getDataId()));
        List<UpProjectAuth> authList = upProjectAuthMapper.selectByExample(example);
        if (authList.isEmpty()) {
            throw new BusinessException("项目上报，项目id="+reportMessage.getDataId()+",项目未生成auth密钥！！！");
        }
        //失败直接返回
        if(RecordStatus.FAIL.val().equals(upRecord.getStatus())){
            return upRecord;
        }
        /**
         * 该状态由上报项目业务决定
         */
        //upRecord.setIsRealReport(RecordType.SHAM_REPORT.code());
        //upRecord.setStatus(RecordStatus.SUCCESS.val());
        //requestCode所需的业务值
        upRecord.setTableId(authList.get(0).getAuthKey());

        upRecordServiceImpl.updateRecord(reportMessage.getRequestCode(), upRecord);
        return upRecord;
    }

    @Override
    protected Integer getProjId(ReportMessage reportMessage) {
        return Integer.valueOf(reportMessage.getDataId());
    }

    @Override
    protected RecordType isReport(ReportCityCode cityCode) {
        return null;
    }
}
