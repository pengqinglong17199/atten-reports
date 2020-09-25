package com.quanroon.atten.reports.common;

/**
 * 上报常量
 * @author 彭清龙
 * @date 2020/7/1 11:00
 */
public interface ReportConstant {

    /**虚假上报标识*/
    Integer UNREALITY_REPORT = -999;
    /** 缓存项目前缀*/
    String CHACH_PREFIX = "report";

    //+++++++++++++++++++++++++++++++++++上报类型常量++++++++++++++++++++++++++++++++
    /** 上报项目*/
    public static final String TYPE_PROJ_REPORT = "proj_report";
    /** 修改项目*/
    public static final String TYPE_PROJ_UPDATE = "proj_update";
    /** 上报配置*/
    public static final String TYPE_PROJ_CONFIG = "proj_config";
    /** 上报参建单位*/
    public static final String TYPE_COMPANY_REPORT = "company_report";
    /** 修改参建单位*/
    public static final String TYPE_COMPANY_UPDATE = "company_update";
    /** 参建单位入场*/
    public static final String TYPE_COMPANY_ENTER = "company_enter";
    /** 参建单位离场*/
    public static final String TYPE_COMPANY_LEAVE = "company_leave";
    /** 上报班组*/
    public static final String TYPE_GROUP_REPORT = "group_report";
    /** 上报班组修改*/
    public static final String TYPE_GROUP_UPDATE = "group_update";
    /** 班组离场*/
    public static final String TYPE_GROUP_LEAVE = "group_leave";
    /** 上报劳工*/
    public static final String TYPE_WORKER_REPORT = "worker_report";
    /** 上报劳工修改*/
    public static final String TYPE_WORKER_UPDATE = "worker_update";
    /** 劳工入场*/
    public static final String TYPE_WORKER_ENTER = "worker_enter";
    /** 劳工离场*/
    public static final String TYPE_WORKER_LEAVE = "worker_leave";
    /** 上报考勤机*/
    public static final String TYPE_DEVICE_REPORT = "device_report";
    /** 解绑考勤机*/
    public static final String TYPE_DEVICE_UNBIND = "device_unbind";
    /** 上报附件*/
    public static final String TYPE_FILE_REPORT = "file_report";
    /** 上报工资专户*/
    public static final String TYPE_ACCOUNT_REPORT = "account_report";
    /** 上报工资专户到账*/
    public static final String TYPE_SALARY_ARRIVE = "salary_arrive";
    /** 上报工资单*/
    public static final String TYPE_PAYROLL_REPORT = "payroll_report";
    /** 上报工资单明细*/
    public static final String TYPE_PAYROLL_DETAIL = "payroll_detail";
    /** 上报考勤*/
    public static final String TYPE_WORKER_SIGNLOG = "worker_signlog";


    //+++++++++++++++++++++++++++++++++++上报表名常量++++++++++++++++++++++++++++++++

    /** 企业进场*/
    public static final String UP_COMPANY_IN = "up_company_in";
    /** 企业信息*/
    public static final String UP_COMPANY_INFO = "up_company_info";
    /** 设备信息*/
    public static final String UP_DEVICE_INFO = "up_device_info";
    /** 字典信息*/
    public static final String UP_DICT_INFO = "up_dict_info";
    /** 附件信息*/
    public static final String UP_FILE = "up_file";
    /** 班组信息*/
    public static final String UP_GROUP_INFO = "up_group_info";
    /** 参数信息*/
    public static final String UP_PARAMS = "up_params";
    /** 工资单明细*/
    public static final String UP_PAYROLL_DETAIL_INFO = "up_payroll_detail_info";
    /** 工资单*/
    public static final String UP_PAYROLL_INFO = "up_payroll_info";
    /** 企业鉴权*/
    public static final String UP_PLATFORM_AUTH = "up_platform_auth";
    /** 项目鉴权*/
    public static final String UP_PROJECT_AUTH = "up_project_auth";
    /** 施工许可证*/
    public static final String UP_PROJECT_CERTIFICATE = "up_project_certificate";
    /** 项目信息*/
    public static final String UP_PROJECT_INFO = "up_project_info";
    /** 上报记录*/
    public static final String UP_RECORD = "up_record";
    /** 工资专户到账*/
    public static final String UP_SALARY_ARRIVE = "up_salary_arrive";
    /** 工资专户*/
    public static final String UP_SALARY_INFO = "up_salary_info";
    /** 劳工资质表*/
    public static final String UP_WORKER_EXP = "up_worker_exp";
    /** 劳工进退场*/
    public static final String UP_WORKER_IN = "up_worker_in";
    /** 劳工信息*/
    public static final String UP_WORKER_INFO = "up_worker_info";
    /** 考勤信息*/
    public static final String UP_WORKER_SIGNLOG_INFO = "up_worker_signlog_info";


    //+++++++++++++++++++++++++++++++++++附件类型常量++++++++++++++++++++++++++++++++
    /** 承包合同附件模块*/
    public static final String MODULE_GROUP_CONTRACT = "1";
    /** 身份证头像模块*/
    public static final String MODULE_IDCARD_HEAD = "2";
    /** 人员照片模块*/
    public static final String MODULE_WORKER_IMAGE = "3";
    /** 身份证正面照片模块*/
    public static final String MODULE_IDCARD_FRONT = "4";
    /** 身份证反面照片模块*/
    public static final String MODULE_IDCARD_BACK = "5";
    /** 合同文件模块*/
    public static final String MODULE_WORKER_CONTRACT = "6";
    /** ⼯伤保险凭证模块*/
    public static final String MODULE_WORKER_INSURANCE = "7";
    /** 证书模块*/
    public static final String MODULE_WORKER_CERTIFICATE = "8";
    /** 考勤图片模块*/
    public static final String MODULE_SIGNLOG_IMAGE = "9";
    /** 工资单附件模块*/
    public static final String MODULE_PAYROLL_File = "10";
    /** 工资单清册凭证模块*/
    public static final String MODULE_PAYROLL_CERTIFICATE = "11";
    /** 银行卡照片模块*/
    public static final String MODULE_WORKER_BANK = "12";


}
