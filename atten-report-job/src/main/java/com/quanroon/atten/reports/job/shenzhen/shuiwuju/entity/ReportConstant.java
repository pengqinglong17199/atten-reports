package com.quanroon.atten.reports.job.shenzhen.shuiwuju.entity;

public class ReportConstant {

    /** 建筑工人班组*/
    public static final String WORKER_GROUP = "00";

    /** 管理人员班组*/
    public static final String MANAGER_GROUP = "01";

    /** 认证匹配成功标识*/
    public static final String MATCH_SUCCESS_FLAG = "Y";

    /** 认证匹配失败标识*/
    public static final String MATCH_FAILURE_FLAG = "N";

    /** 人员和参见单位已签合同*/
    public static final String HAS_SIGN_CONTRACT = "1";

    /** 水务局考勤方向:进场*/
    public static final String DIRECTION_IN = "in";

    /** 水务局考勤方向:出场*/
    public static final String DIRECTION_OUT = "out";

    /** 独立化平台考勤方向:进场*/
    public static final String DIRECTION_IN_REPORTAPI = "1";

    /** 通行方式:人脸识别*/
    public static final String PASS_WAY = "1";

    /** 身份证人脸*/
    public static final String MODULE_ID_PHOTO ="2";

    /** 人脸照片*/
    public static final String MODULE_FACE_PHOTO ="3";

    /** 考勤图片*/
    public static final String MODULE_SIGNLOG_PHOTO ="9";

    /** 考勤表名*/
    public static final String TABLE_SIGNLOG = "up_worker_signlog_info";

    /** 劳工表名*/
    public static final String TABLE_WORKER = "up_worker_info";

    /** 工种类型*/
    public static final String JOB_TYPE = "job_type";

    /** 单位类型*/
    public static final String COMPANY_TYPE = "company_type";

    /** 班组类型*/
    public static final String GROUP_TYPE = "group_type";

    /** 人员类型*/
    public static final String WORKER_TYPE = "worker_type";

    /** 民族*/
    public static final String NATION_TYPE = "nation_type";

    /** 深圳水务局上报劳工默认汉族*/
    public static final String NATION_DEFAULT = "汉族";

    /** 水务局其它人员*/
    public static final String OTHER_JOB = "其它人员";

    /** 水务局其它人员code*/
    public static final String OTHER_JOB_CODE = "1064";

    /** 班组类型其它*/
    public static final String OTHER_GROUP_CODE = "040";

    /** 参见单位类型其它*/
    public static final String OTHER_COMPANY_CODE = "015";

    /** 参见单位类型其它*/
    public static final String OTHER_COMPANY_VALUE = "其他";

    /** 员工类型，默认劳务工人*/
    public static final String WORKER_CODE = "04";
}
