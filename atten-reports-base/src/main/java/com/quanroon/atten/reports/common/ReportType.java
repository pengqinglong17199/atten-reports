package com.quanroon.atten.reports.common;

/**
 * 上报类型枚举
 * @author 彭清龙
 * @date 2020/7/7 14:46
 */
public enum ReportType implements EnumsInterface{

    /** 上报项目*/
    proj_report("[项目上报]"),
    /** 修改项目*/
    proj_update("[项目修改]"),
    /** 上报参建单位*/
    company_report("[参建单位上报]"),
    /** 修改参建单位*/
    company_update("[参建单位修改]"),
    /** 参建单位入场*/
    company_enter("[参建单位入场]"),
    /** 参建单位离场*/
    company_leave("[参建单位离场]"),
    /** 上报班组*/
    group_report("[班组信息上报]"),
    /** 上报班组修改*/
    group_update("[班组信息修改]"),
    /** 班组离场*/
    group_leave("[班组离场]"),
    /** 上报劳工*/
    worker_report("[劳工信息上报]"),
    /** 上报劳工修改*/
    worker_update("[劳工信息修改]"),
    /** 劳工入场*/
    worker_enter("[劳工入场]"),
    /** 劳工离场*/
    worker_leave("[劳工离场]"),
    /** 上报考勤机*/
    device_report("[考勤机上报]"),
    /** 解绑考勤机*/
    device_unbind("[考勤机解绑]"),
    /** 上报附件*/
    file_report("[附件上报]"),
    /** 上报工资专户新增*/
    account_report("[工资专户上报]"),
    /** 上报工资专户更新*/
    account_update("[工资专户修改]"),
    /** 上报工资专户到账*/
    salary_arrive("[工资专户到账上报]"),
    /** 上报工资专户到账更新*/
    salary_update("[工资专户到账修改]"),
    /** 上报工资单*/
    payroll_report("[工资单上报]"),
    /** 上报工资单更新*/
    payroll_update("[工资单修改]"),
    /** 上报工资单明细*/
    payroll_detail("[工资单明细上报]"),
    /** 上报工资单明细更新*/
    payroll_detail_update("[工资单明细修改]"),
    /** 上报考勤*/
    worker_signlog("考勤上报"),
    /** 上报劳工合同信息 */
    worker_contract("合同上报");

    private String message;
    ReportType(String message){
        this.message = message;
    }
    /**
    * @Description: 获取枚举值
    * @Author: quanroon.yaosq
    * @Date: 2020/9/7 11:55
    * @Param: [name] 枚举名称
    * @Return: java.lang.String
    */
    public String getMessage(String... name){
        if(name.length > 0){
            for(ReportType type : ReportType.values()){
                if(type.name().equals(name))
                    return  type.message;
            }
        }
        return this.message;
    }

    @Override
    public String value() {
        return this.message;
    }

    @Override
    public String message() {
        return this.message;
    }
}
