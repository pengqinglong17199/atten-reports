package com.quanroon.atten.reports.service.impl;

import com.quanroon.atten.commons.exception.BusinessException;
import com.quanroon.atten.commons.utils.RedisUtils;
import com.quanroon.atten.commons.utils.StringUtils;
import com.quanroon.atten.reports.common.RecordStatus;
import com.quanroon.atten.reports.common.ReportType;
import com.quanroon.atten.reports.dao.UpRecordMapper;
import com.quanroon.atten.reports.entity.ReportMessage;
import com.quanroon.atten.reports.entity.UpRecord;
import com.quanroon.atten.reports.entity.base.CodeEntity;
import com.quanroon.atten.reports.entity.example.UpRecordExample;
import com.quanroon.atten.reports.exception.RequestCodeException;
import com.quanroon.atten.reports.message.RocketMQTemplate;
import com.quanroon.atten.reports.service.UpRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional(readOnly = true)
public class UpRecordServiceImpl implements UpRecordService {

    private String CACHE_PREFIX = "reportApi:requestCode";
    //指定上报类型，离场类型
    private String[] leaveReportTypes = new String[]{ReportType.company_leave.toString()
            ,ReportType.group_leave.toString()
            ,ReportType.worker_leave.toString()
            ,ReportType.device_unbind.toString()};

    @Autowired private UpRecordMapper upRecordMapper;
    @Autowired private RedisUtils redisUtils;
    @Resource private RocketMQTemplate rocketMQTemplate;

    @Override
    @Transactional(readOnly = false)
    public String initRequestCode(CodeEntity codeEntity, ReportType reportType) {
        // 生成uuid
        String requestCode = UUID.randomUUID().toString().replaceAll("-", "");

        // 封装保存上报记录数据
        UpRecord upRecord = new UpRecord();
        upRecord.setStatus(RecordStatus.WAIT.val());
        upRecord.setTableName(codeEntity.getTableName());
        upRecord.setTableId(codeEntity.getTableId().toString());
        upRecord.setType(reportType.toString());
        upRecord.setRequestCode(requestCode);
        upRecord.setReportDate(new Date());

        upRecordMapper.insert(upRecord);

        // 存入消息队列
        ReportMessage reportMessage = new ReportMessage(requestCode, codeEntity.getTableId().toString(), reportType);
        try {
            //发送消息
            rocketMQTemplate.send(codeEntity, reportType.toString(), reportMessage);
        } catch (Exception e) {
            throw new BusinessException("推送上报记录消息到mq失败");
        }

        // 存入缓存
        redisUtils.set(CACHE_PREFIX + ":" + requestCode, upRecord, 300);

        return requestCode;
    }

    @Override
    @Transactional(readOnly = false)
    public void updateRecord(String requestCode, UpRecord upRecord) throws RequestCodeException {
        // 校验
        if(StringUtils.isEmpty(requestCode)){
            throw new RequestCodeException("requestCode 为空！！！");
        }
        // 更新
        UpRecordExample example = new UpRecordExample();
        example.createCriteria().andRequestCodeEqualTo(requestCode);
        upRecord.setReportDate(new Date());
        if(upRecordMapper.updateByExampleSelective(upRecord, example) != 0){
            //保证存入缓存的数据完整性
            List<UpRecord> upRecords = upRecordMapper.selectByExample(example);
            UpRecord record = upRecords.get(0);
            // 存入缓存
            redisUtils.set(CACHE_PREFIX + ":" + requestCode, record, 300);
            log.info(record.printReportResult());

            //上报失败，对离场类型的状态回滚操作
            if(RecordStatus.FAIL.val().equals(record.getStatus())){
                if(Arrays.asList(leaveReportTypes).contains(record.getType())){
                    UpdateBusinessTableToLeaveStatus(record.getTableName(), Integer.valueOf(record.getTableId()));
                }
            }
        }
    }

    @Override
    public UpRecord getRecord(String requestCode){

        // 取缓存
        UpRecord upRecord = (UpRecord) redisUtils.get(CACHE_PREFIX + ":" + requestCode);

        // 缓存不存在
        if(upRecord == null){
            // 查询
            UpRecordExample example = new UpRecordExample();
            example.createCriteria().andRequestCodeEqualTo(requestCode);
            List<UpRecord> list = upRecordMapper.selectByExample(example);

            // 再次存入缓存
            if(!list.isEmpty()){
                upRecord = list.get(0);
                redisUtils.set(CACHE_PREFIX + ":" + requestCode, upRecord, 300);
            }
        }

        return upRecord;
    }

    @Override
    public int UpdateBusinessTableToLeaveStatus(String tableName, Integer dataId) {
        return upRecordMapper.updateDynamicStatusByTableNameAndId(tableName, dataId);
    }
}
