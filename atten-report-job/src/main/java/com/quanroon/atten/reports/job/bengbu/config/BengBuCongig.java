package com.quanroon.atten.reports.job.bengbu.config;

import com.quanroon.atten.reports.common.ReportType;
import com.quanroon.atten.reports.report.annotation.City;
import com.quanroon.atten.reports.report.constant.*;
import com.quanroon.atten.reports.report.entity.ReportConfig;
import com.quanroon.atten.reports.report.entity.ReportParam;

import java.util.Map;

/**
 * @author 罗森林
 * @Auther:
 * @Date: 2020-08-27 13:55
 * @Description:
 */
@City(ReportCityCode.BENGBU)
public class BengBuCongig implements ReportConfig {
    /** 调用成功 */
    public static final String RESULT_SUCCESS_CODE = "0";
    /** 调用失败 */
    public static final String RESULT_FAIL_CODE = "1";
    /** 项目进场 */
    public static final String PROJ_ENTER = "1";
    /** 项目上传 */
    public static final String UPLOAD_PROJ_ADD = "/api/service/ab02";
    /** 项目修改 */
    public static final String UPLOAD_PROJ_EDIT = "/api/edit/service/ab02";
    /** 单位上传 */
    public static final String UPLOAD_COMPANY_ADD = "/api/service/ab03";
    /** 班组上传 */
    public static final String UPLOAD_GROUP_ADD = "/api/service/ab04";
    /** 工资发放信息上传 */
    public static final String UPLOAD_SALARY_ADD = "/api/service/ac02";
    /** 考勤上传 */
    public static final String UPLOAD_SIGN_ADD = "/api/service/ac20";
    /** 合同上传 */
    public static final String UPLOAD_CONTRACT_ADD = "/api/service/acb1";
    /** 花名册上传 */
    public static final String UPLOAD_WORKER_ADD = "/api/service/acb2";
    /** 请求类型 Query */
    public static final String REQUEST_QUERY = "query";
    /** 请求类型 body */
    public static final String REQUEST_BODY = "body";

    public String getUrl(ReportType reportType) {
        return null;
    }

    @Override
    public RequestType getRequestType() {
        return null;
    }

    @Override
    public RequestFormat getRequestFormat() {
        return null;
    }

    @Override
    public RequestMode getRequestMode() {
        return null;
    }

    @Override
    public AuthenticationMode getAuth() {
        return null;
    }

    @Override
    public Map<String, String> getSignMap(ReportParam reportParam) {
        return null;
    }

}
