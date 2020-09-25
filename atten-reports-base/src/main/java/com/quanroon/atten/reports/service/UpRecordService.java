package com.quanroon.atten.reports.service;

import com.quanroon.atten.reports.common.ReportType;
import com.quanroon.atten.reports.entity.UpRecord;
import com.quanroon.atten.reports.entity.base.CodeEntity;
import com.quanroon.atten.reports.exception.RequestCodeException;

/**
 * 上报记录service
 * @author 彭清龙
 * @date 2020/6/30 19:47
 */
public interface UpRecordService {

    /**
     * 生成上报唯一编码并插入上报记录
     * @param codeEntity, reportType
     * @return java.lang.String
     * @author quanroon.ysq
     * @date 2020/8/6 08:31
     */
    String initRequestCode(CodeEntity codeEntity, ReportType reportType);

    /**
     * 通过上报唯一编码更新上报记录
     * @param requestCode, upRecord
     * @return java.lang.Integer
     * @author 彭清龙
     * @date 2020/6/30 19:41
     */
    void updateRecord(String requestCode, UpRecord upRecord) throws RequestCodeException;

    /**
     * 通过唯一编码获取上报记录
     * @param requestCode
     * @return com.quanroon.atten.reports.entity.UpRecord
     * @author 彭清龙
     * @date 2020/6/30 19:43
     */
    UpRecord getRecord(String requestCode);

    /**
     * 根据消费的消息，对离场动作做事物回滚操作
     * @param tableName
     * @param dataId
     * @author quanroon.ysq
     * @return
     */
    int UpdateBusinessTableToLeaveStatus(String tableName, Integer dataId);
}
