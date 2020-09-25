package com.quanroon.atten.reports.entity.dto;

import io.swagger.models.auth.In;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 罗森林
 * @Auther:
 * @Date: 2020-09-02 14:23
 * @Description:
 */
@Data
public class UpBengbuPayRollDTO implements Serializable {
    /** 工资单详情id */
    private Integer id;
    /** 工资单表id */
    private Integer payId;
    /** 项目id */
    private Integer projId;
    /**所属单位编码(统一社会信息码) */
    private String corpCode;
    /**证件号码 */
    private String cardNo;
    /**姓名 */
    private String name;
    /**业务年月(yyyyMM) */
    private Date businessDate;
    /**约定工资 */
    private String salaryMoney;
    /**出勤天数 */
    private String attendanceDays;
    /**应发工资 */
    private String payableMoney;
    /**实发工资 */
    private String paidMoney;
    /**工资卡卡号 */
    private String salaryBankNo;
    /**工资卡开户名 */
    private String accountName;
    /**项目编码 */
    private String projNo;
    /**班组编码 */
    private String groupNo;
    /**开户银行 */
    private String salaryBank;
}
