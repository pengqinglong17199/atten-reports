package com.quanroon.atten.reports.message;

import com.quanroon.atten.commons.utils.StringUtils;
import com.quanroon.atten.reports.common.EnumsInterface;
import com.quanroon.atten.reports.common.RecordStatus;
import com.quanroon.atten.reports.common.RecordType;
import com.quanroon.atten.reports.dao.UpParamsMapper;
import com.quanroon.atten.reports.entity.ReportMessage;
import com.quanroon.atten.reports.entity.UpParams;
import com.quanroon.atten.reports.entity.UpRecord;
import com.quanroon.atten.reports.entity.example.UpParamsExample;
import com.quanroon.atten.reports.exception.RequestCodeException;
import com.quanroon.atten.reports.report.ReportDelegate;
import com.quanroon.atten.reports.report.constant.ReportCityCode;
import com.quanroon.atten.reports.report.entity.ReportResult;
import com.quanroon.atten.reports.report.excepotion.NotParameterException;
import com.quanroon.atten.reports.report.excepotion.NotReportCityException;
import com.quanroon.atten.reports.report.excepotion.ReportBeanNotFoundException;
import com.quanroon.atten.reports.service.UpRecordService;
import com.quanroon.atten.reports.utils.ReportUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;


/**
 * 消费者基类
 * @author 彭清龙
 * @date 2020/7/1 17:10
 */
@Slf4j
@Transactional(readOnly = true)
public abstract class BaseHandler {

    @Autowired private UpRecordService upRecordServiceImpl;
    @Autowired private UpParamsMapper upParamsMapper;
    @Autowired private RocketMQTemplate rocketMQTemplate;

    /**
     * 终端消费者处理器
     * @param reportMessage
     * @author quanroon.ysq
     * @date 2020/08/05 14:18
     * @return
     */
    @Transactional(readOnly = false)
    protected UpRecord resolver(ReportMessage reportMessage) throws RequestCodeException, NotReportCityException, NotParameterException,InstantiationException, IllegalAccessException {
        //获取封装的上报结果
        UpRecord upRecord = null;
        try {
            //防止消息重复消费
            UpRecord record = upRecordServiceImpl.getRecord(reportMessage.getRequestCode());
            if(Objects.nonNull(record) && !RecordStatus.WAIT.value().equals(record.getStatus())){
                log.warn("==> 本条消息已消费过，请勿重复消费,record={}",record.printReportResult());
                return record;
            }
            //消费者是否开启虚假标识，true则进行虚假上报,默认false
            if(rocketMQTemplate.isShamConsumer()){
                return updateRecordByShamReport(reportMessage);
            }
            // 根据子类具体情况获取项目id
            Integer projId = getProjId(reportMessage);
            // 查询上报参数配置
            UpParamsExample example = new UpParamsExample();
            example.createCriteria().andProjIdEqualTo(projId);
            List<UpParams> list = upParamsMapper.selectByExample(example);
            UpParams upParams = list.get(0);

            // 获得上报城市
            ReportCityCode reportCityCode = getCityCode(projId, upParams);

            ReportDelegate delegate = ReportDelegate.getDelegate();
            // 由委托类处理上报功能，分发到具体城市的上报服务中
            ReportResult result = delegate.report(reportCityCode, reportMessage.getReportType(), reportMessage);
            upRecord = updateRecordByRealReport(reportMessage, result);
        } catch (ReportBeanNotFoundException e) {
            log.info(e.getMessage());
            //上报服务bean未注入，默认虚假上报
            upRecord = updateRecordByShamReport(reportMessage);
        } catch (NotParameterException e) {
            log.error("参数校验异常, 异常如下： " + e);
            upRecord = updateRecordByExceptReport(reportMessage, RecordType.REAL_RETPOR, RecordStatus.FAIL,e.getMessage());
        } catch (Exception e) {
            log.error("上报未知异常, 异常如下： ", e);
            upRecord = updateRecordByExceptReport(reportMessage, RecordType.REAL_RETPOR, RecordStatus.FAIL,"上报异常, 请联系对接技术");
        }

        return upRecord;

    }

    /**
     * 获得对应上报城市枚举
     * @param projId, upParams
     * @return com.quanroon.atten.reports.report.constant.ReportCityCode
     * @author 彭清龙
     * @date 2020/7/14 14:18
     */
    private ReportCityCode getCityCode(Integer projId, UpParams upParams) throws NotReportCityException {
        // 获取上报城市code
        String code = ReportUtils.getCode(upParams);
        if(StringUtils.isEmpty(code)){
            throw new NotReportCityException("projId = " + projId + ",参数配置中没有上报城市,请检查上报城市code");
        }
        // 抓取对应城市
        ReportCityCode reportCityCode = (ReportCityCode)EnumsInterface.getEnums(ReportCityCode.class, code);

        return reportCityCode;
    }

    /**
     * 根据业务消息 返回项目id
     * @param reportMessage
     * @return java.lang.Integer
     * @author 彭清龙
     * @date 2020/7/15 8:52
     */
    protected abstract Integer getProjId(ReportMessage reportMessage);

    /**
     * 根据城市返回是否有上报功能
     * @param cityCode
     * @return com.quanroon.atten.reports.common.RecordType
     * @author 彭清龙
     * @date 2020/7/15 8:52
     */
    protected abstract RecordType isReport(ReportCityCode cityCode);

    /**
     * 正常流程处理上报结果
     * @param reportMessage
     * @return void
     * @author 彭清龙
     * @date 2020/7/15 15:54
     */
    protected UpRecord updateRecordByRealReport(ReportMessage reportMessage, ReportResult result) throws RequestCodeException {
        String requestCode = reportMessage.getRequestCode();

        UpRecord upRecord = new UpRecord(reportMessage.getReportType().name());
        upRecord.setIsRealReport(RecordType.REAL_RETPOR.code());
        upRecord.setStatus((result.isSuccess() ? RecordStatus.SUCCESS : RecordStatus.FAIL).val());
        upRecord.setType(reportMessage.getReportType().name());
        //携带本次requestCode所需的业务值
        upRecord.setTableId(reportMessage.getDataId());
        //如果成功，则统一返回上报成功，如果失败，则给出住建局的提示信息
        upRecord.setResult((RecordStatus.SUCCESS.val().equals(upRecord.getStatus()) ?
                EnumsInterface.getReportEnumsMessage(RecordStatus.class, upRecord.getStatus(),
                        reportMessage.getReportType()).toString() :reportMessage.getReportType().getMessage()+result.getMessage()));

        upRecordServiceImpl.updateRecord(requestCode, upRecord);

        return upRecord;
    }
    /**
     * 虚假流程处理上报结果
     * @param reportMessage
     * @return void
     * @author quanroon.ysq
     * @date 2020/7/15 15:54
     */
    protected UpRecord updateRecordByShamReport(ReportMessage reportMessage) throws RequestCodeException {
        String requestCode = reportMessage.getRequestCode();

        UpRecord upRecord = new UpRecord(reportMessage.getReportType().name());
        upRecord.setIsRealReport(RecordType.SHAM_REPORT.code());
        upRecord.setStatus(RecordStatus.SUCCESS.val());
        //携带本次requestCode所需的业务值
        upRecord.setTableId(reportMessage.getDataId());
        upRecord.setResult(EnumsInterface.getReportEnumsMessage(RecordStatus.class, RecordStatus.SUCCESS.val(), reportMessage.getReportType()).toString());

        upRecordServiceImpl.updateRecord(requestCode, upRecord);
        return upRecord;
    }
    /**
     * 异常流程处理上报结果
     * @param reportMessage, recordType, recordStatus
     * @param result 响应结果
     * @return void
     * @author quanroon.ysq
     * @date 2020/7/15 15:54
     */
    protected UpRecord updateRecordByExceptReport(ReportMessage reportMessage, RecordType recordType, RecordStatus recordStatus, String result) throws RequestCodeException {
        String requestCode = reportMessage.getRequestCode();

        UpRecord upRecord = new UpRecord(reportMessage.getReportType().name());
        upRecord.setIsRealReport(recordType.code());
        upRecord.setStatus(recordStatus.val());

        upRecord.setTableId(reportMessage.getDataId());
        upRecord.setResult(reportMessage.getReportType().getMessage() + result);

        upRecordServiceImpl.updateRecord(requestCode, upRecord);

        return upRecord;
    }
}
