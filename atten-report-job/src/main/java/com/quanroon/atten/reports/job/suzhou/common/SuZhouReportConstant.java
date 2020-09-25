package com.quanroon.atten.reports.job.suzhou.common;


/**
* 宿州上报结果描述常量
*
* @Author: ysx
* @Date: 2020/8/4
*/
public class SuZhouReportConstant {

    /** 班组上报成功*/
    public static final String GROUP_ADD_SUCCESS = "班组上报成功";

    /** 班组修改成功*/
    public static final String GROUP_UPDATE_SUCCESS = "班组修改成功";

    /** 班组上报服务异常*/
    public static final String GROUP_REPORT_ERROR = "宿州班组上报服务异常";

    /** 班组离场标识*/
    public static final String GROUP_DELETE_FLAG = "1";

    /** 班组离场成功*/
    public static final String GROUP_DELETE_SUCCESS = "班组离场成功";

    /** 班组离场服务异常*/
    public static final String GROUP_DELETE_ERROR = "宿州班组离场服务异常";

    /** 班组长标识*/
    public static final String GROUP_LEADER_FLAG = "1";

    /** 劳工上报成功*/
    public static final String WORKER_REPORT_SUCCESS = "劳工上报成功";

    /** 劳工上报服务异常*/
    public static final String WORKER_REPORT_ERROR = "宿州劳工上报服务异常";

    /** 劳工离场标识*/
    public static final String WORKER_ENTER_FLAG = "1";

    /** 劳工离场成功*/
    public static final String WORKER_DELETE_SUCCESS = "劳工删除(离场)成功";

    /** 劳工离场服务异常*/
    public static final String WORKER_DELETE_ERROR = "宿州劳工删除(离场)服务异常";

    /** 考勤上报成功标识*/
    public static final String SIGNLOG_SUCCESS_FLAG = "0";

    /** 考勤上报失败标识*/
    public static final String SIGNLOG_FAILURE_FLAG = "1";

    /** 考勤上报服务异常*/
    public static final String SIGNLOG_REPORT_ERROR = "宿州考勤上报服务异常";

    /** 住建局设备进场方向*/
    public static final String DEVICE_DIRECTION_IN = "0";

    /** 住建局设备出场方向*/
    public static final String DEVICE_DIRECTION_OUT = "1";

    /** 上报独立化平台进场方向*/
    public static final String REPORT_DEVICE_DIRECTION_IN = "JC01";

    /** 上报独立化平台性别女*/
    public static final String REPORT_SEX_FEMALE = "XB00";

    /** 住建局性别女*/
    public static final String SEX_FEMALE = "0";

    /** 住建局性别男*/
    public static final String SEX_MALE = "1";

    /** 独立化平台班组类型:技术型编码*/
    public static final String GROUP_TYPE_ID_TECHNOLOGY = "BZ01";

    /** 独立化平台班组类型:普工型编码*/
    public static final String GROUP_TYPE_ID_COMMON = "BZ02";

    /** 独立化平台班组类型:技术型*/
    public static final String GROUP_TYPE_TECHNOLOGY = "技术型";

    /** 独立化平台班组类型:技术型*/
    public static final String GROUP_TYPE_COMMON = "普工型";

    /** 独立化平台班组类型:技术型*/
    public static final String GROUP_TYPE_OTHER = "其他";

    /** 独立化平台名族字典类型*/
    public static final String NATION_TYPE = "nation_type";

    /** 宿州上报劳工默认汉族*/
    public static final String NATION_DEFAULT = "汉族";

}
