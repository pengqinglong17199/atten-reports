package com.quanroon.atten.reports.entity.dto;

import com.quanroon.atten.reports.common.ReportConstant;
import com.quanroon.atten.reports.entity.base.CodeEntity;
import com.quanroon.atten.reports.entity.base.EntityInterface;
import com.quanroon.atten.reports.entity.base.TableEntity;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Table: up_salary_info
 */
@Data
public class UpBengbuSalaryInfoDTO  {
    /**
     * 主码
     */
    private Integer id;

    /**
     * 项目id
     */
    private Integer projId;

    /**
     * 企业id
     *
     * Table:     up_salary_info
     * Column:    company_id
     * Nullable:  true
     */
    private Integer companyId;

    /**
     * 工资专用账户名称
     */
    private String accountName = "";

    /**
     * 工资专用账户开户行
     */
    private String bankName = "";

    /**
     * 工资专用账户开户行银行代码
     */
    private String bankCode = "";

    /**
     * 工资专用账户账号
     */
    private String bankAccount="";

    /**
     * 账户余额
     */
    private BigDecimal balance;


}