package com.quanroon.atten.reports.job.shenzhen.shuiwuju.entity;

import com.quanroon.atten.reports.common.ReportType;
import com.quanroon.atten.reports.report.annotation.City;
import com.quanroon.atten.reports.report.constant.*;
import com.quanroon.atten.reports.report.entity.ReportConfig;
import com.quanroon.atten.reports.report.entity.ReportParam;

import java.util.Map;

/**
* 深圳水务局配置类
*
* @Author: ysx
* @Date: 2020/8/12
*/
@City(ReportCityCode.SHENZHEN)
public class ShuiWuJuConfig implements ReportConfig {

    /** 接口协议版本*/
    public static final String API_VERSION = "1.0";

    /** 上报成功标识*/
    public static final String SUCCESS_CODE = "00";

    /** 上报失败标识*/
    public static final String FAILURE_CODE = "11";

    /** 上报参建单位*/
    public static final String ADD_COMPANY = "/CWRService/AddCompany";

    /** 上报班组*/
    public static final String ADD_GROUP = "/CWRService/AddTeam";

    /** 上报人员*/
    public static final String ADD_WORKER = "/CWRService/RegisterEmployee";

    /** 上传考勤*/
    public static final String ADD_SIGNLOG = "/CWRService/UploadPassedLog";

    /** 人员离场*/
    public static final String WORKER_LEAVE = "/CWRService/userLeaveProject";

    /** 参建单位离场*/
    public static final String COMPANY_LEAVE = "/CWRService/ProjectRemoveCompany";

    /** 工种名称数据字典*/
    public static final String JOB_NAME = "/CWRService/DictListJobName";

    /** 单位类型数据字典*/
    public static final String COMPANY_TYPE = "/CWRService/GetCompanyType";

    /** 班组类型数据字典*/
    public static final String GROUP_TYPE = "/CWRService/GetTeamType";

    /** 人员类型数据字典*/
    public static final String WORKER_TYPE = "/CWRService/GetWorkerPersonnelType";

    /** 上传图片接口*/
    public static final String UPLOAD_IMAGE = "/CWRService/UploadImage";

    @Override
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
