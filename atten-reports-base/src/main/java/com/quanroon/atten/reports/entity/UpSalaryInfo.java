package com.quanroon.atten.reports.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import com.quanroon.atten.reports.common.ReportConstant;
import com.quanroon.atten.reports.entity.base.CodeEntity;
import com.quanroon.atten.reports.entity.base.EntityInterface;
import com.quanroon.atten.reports.entity.base.TableEntity;
import lombok.Data;

/**
 * Table: up_salary_info
 */
@Data
@TableEntity(ReportConstant.UP_SALARY_INFO)
public class UpSalaryInfo implements Serializable, EntityInterface<UpSalaryInfo> {
    /**
     * 主码
     *
     * Table:     up_salary_info
     * Column:    id
     * Nullable:  false
     */
    private Integer id;

    /**
     * 项目id
     *
     * Table:     up_salary_info
     * Column:    proj_id
     * Nullable:  true
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
     *
     * Table:     up_salary_info
     * Column:    account_name
     * Nullable:  true
     */
    private String accountName;

    /**
     * 工资专用账户开户行
     *
     * Table:     up_salary_info
     * Column:    bank_name
     * Nullable:  true
     */
    private String bankName;

    /**
     * 工资专用账户开户行银行代码
     *
     * Table:     up_salary_info
     * Column:    bank_code
     * Nullable:  true
     */
    private String bankCode;

    /**
     * 工资专用账户账号
     *
     * Table:     up_salary_info
     * Column:    bank_account
     * Nullable:  true
     */
    private String bankAccount;

    /**
     * 账户余额
     *
     * Table:     up_salary_info
     * Column:    balance
     * Nullable:  true
     */
    private BigDecimal balance;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table up_salary_info
     *
     * @mbggenerated Mon Jun 29 15:17:49 CST 2020
     */
    private static final long serialVersionUID = 1L;

    @Override
    public CodeEntity initCodeEntity(Integer projId) {
        return CodeEntity.builder()
                .tableId(id)
                .projId(this.projId).build();
    }
}