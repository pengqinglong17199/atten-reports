package com.quanroon.atten.reports.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.quanroon.atten.commons.utils.DateUtils;
import com.quanroon.atten.commons.utils.StringUtils;
import com.quanroon.atten.reports.common.ReportConstant;
import com.quanroon.atten.reports.entity.base.CodeEntity;
import com.quanroon.atten.reports.entity.base.EntityInterface;
import com.quanroon.atten.reports.entity.base.TableEntity;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Table: up_worker_in
 */
@Data
public class UpBengbuWorkerInDTO implements Serializable{

    private static final long serialVersionUID = 1L;
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 项目id
     */
    private Integer projId;

    /**
     * 劳工id
     */
    private Integer workerId;

    /**
     * 证件号码
     */
    private String cardNo;

    /**
     * 工人姓名
     */
    private String name;

    /**
     * 班组id
     */
    private Integer groupId;

    /**
     * 参建单位id
     *
     */
    private Integer companyId;

    /**
     * 进场日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date entryDate;

    /**
     * 离场日期
     */
    private Date leaveDate;

    /**
     * 合同开始时间
     */
    private Date contractStartDate;

    /**
     * 合同结束时间
     */
    private Date contractEndDate;

    /**
     * 上报住建局后返回的唯一编码
     */
    private String reportCode;
    /**
     * 状态 1：进场 0：离场
     */
    private String status;
    /**
     * 社会统一信用代码
     */
    private String corpCode;
    /**
     * 性别
     */
    private String sex;
    /**
     * 手机号码
     */
    private String mobile;
    /**
     * 现居住地址
     */
    private String currentAddress;
    /**
     * 民族
     */
    private String nation;
    /**
     * 文化程度
     */
    private String educationDegree;
    /**
     * 头像
     */
    private String headImage;
    /**
     * 项目编码
     */
    private  String projNo;
    /**
     * 班组编码
     */
    private String groupNo;
    /**
     * 工资卡号
     */
    private String salaryBankNo;

    /** 工资银行 */
    private String salaryBank;

    /** 约定工资形式 */
    private String salaryWay;
    /** 约定工资 */
    private String salaryMoney;
    /** 工种 */
    private String workType;
}