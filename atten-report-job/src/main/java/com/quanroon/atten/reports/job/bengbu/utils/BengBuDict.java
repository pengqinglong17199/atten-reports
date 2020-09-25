package com.quanroon.atten.reports.job.bengbu.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 罗森林
 * @Auther:
 * @Date: 2020-08-27 17:15
 * @Description:
 */
public class BengBuDict {

    /** 参见单位类型其它*/
    public static final String OTHER_COMPANY_CODE = "012";
    /** 行业类型与学历其它 */
    public static final String OTHER_INDUSTRY_CODE = "99";
    /** 工资计薪方式字典默认按月发放 */
    public static final String SALARY_CODE = "1";
    /** 计薪方式字典*/
    public static final String SALARY_WAY = "salary_way";
    /** 行业类型*/
    public static final String COMPANY_TYPE = "company_type";
    /** 开户银行 */
    public static final String DEPOSIT_BANK = "deposit_bank";
    /** 默认开户银行 */
    public static final String DEFAULT_DEPOSIT_BANK = "95566";
    /** 默认工种 */
    public static final String DEFAULT_WORKER_TYPE = "1000";
    /** 工种 */
    public static final String WORKER_TYPE = "worker_type";
    /** 参建类型*/
    public static final String COMPANY_BUILD_TYPE = "company_build_type";
    /** 进出场类型*/
    public static final String DIRECTION_TYPE = "direction_type";
    /** 进出场默认类型  */
    public static final String DIREC_DEFAULT  = "01";
    /** 学历类型 */
    public static final String EDUCATION = "education";
    /** 上报平台 默认-1 */
    public static final String REPORT_TYPE = "-1";
    /** 返回值 */
    public static final String MESSAGE = "success";
    /** 总承包单位 */
    public static final String GENERAL = "009";
    /** 单位类型 */
    public static final Map<String,String> companyMap = new HashMap<>();
    /** 行业类型 */
    public static final Map<String,String> industryMap = new HashMap<>();
    /** 工种编码 */
    public static final Map<String,String> workerTypeMap = new HashMap<>();
    /** 开户银行 */
    public static final Map<String,String> depositBankMap = new HashMap<>();
    /** 工资类型 */
    public static final Map<String,String> salaryTypeMap = new HashMap<>();
    static{
        //(001 固定月薪;002 月薪;003 计日工资;004 计时工资;005 计量工资)
        salaryTypeMap.put("固定月薪","001");
        salaryTypeMap.put("月薪","002");
        salaryTypeMap.put("计日工资","003");
        salaryTypeMap.put("计时工资","004");
        salaryTypeMap.put("计量工资","005");

        //95566 中国银行;95588 中国工商银行;95533 中国建设银行;95599 中国农业银行;95559 交通银行;96588 徽商银行
        depositBankMap.put("中国银行","95566");
        depositBankMap.put("中国工商银行","95588");
        depositBankMap.put("中国建设银行","95533");
        depositBankMap.put("中国农业银行","95599");
        depositBankMap.put("交通银行","95559");
        depositBankMap.put("徽商银行","96588");
    }


}
