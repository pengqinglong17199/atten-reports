package com.quanroon.atten.reports.entity;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.quanroon.atten.commons.annotation.JsonValueValidate;
import com.quanroon.atten.commons.utils.DateUtils;
import com.quanroon.atten.commons.utils.StringUtils;
import com.quanroon.atten.reports.common.ReportConstant;
import com.quanroon.atten.reports.entity.base.CodeEntity;
import com.quanroon.atten.reports.entity.base.EntityInterface;
import com.quanroon.atten.reports.entity.base.TableEntity;
import com.quanroon.atten.reports.entity.dto.UpWorkerInDTO;
import lombok.Data;

/**
 * Table: up_worker_in
 */
@Data
@TableEntity(ReportConstant.UP_WORKER_IN)
public class UpWorkerIn implements Serializable, EntityInterface<UpWorkerIn> {

    /**
     * 主键id
     *
     * Table:     up_worker_in
     * Column:    company_id
     * Nullable:  true
     */
    private Integer id;

    /**
     * 项目id
     *
     * Table:     up_worker_in
     * Column:    proj_id
     * Nullable:  true
     */
    private Integer projId;

    /**
     * 劳工id
     *
     * Table:     up_worker_in
     * Column:    worker_id
     * Nullable:  true
     */
    private Integer workerId;

    /**
     * 班组id
     *
     * Table:     up_worker_in
     * Column:    group_id
     * Nullable:  true
     */
    private Integer groupId;

    /**
     * 进场日期
     *
     * Table:     up_worker_in
     * Column:    entry_date
     * Nullable:  true
     */
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date entryDate;

    /**
     * 离场日期
     *
     * Table:     up_worker_in
     * Column:    leave_date
     * Nullable:  true
     */
    private Date leaveDate;

    /**
     * 紧急联系人
     *
     * Table:     up_worker_in
     * Column:    egy_contact
     * Nullable:  true
     */
    private String egyContact;

    /**
     * 紧急联系人电话
     *
     * Table:     up_worker_in
     * Column:    egy_contact_phone
     * Nullable:  true
     */
    private String egyContactPhone;

    /**
     * 是否为班组长
     *
     * Table:     up_worker_in
     * Column:    is_foreman
     * Nullable:  true
     */
    private String isForeman;

    /**
     * 合同编号
     *
     * Table:     up_worker_in
     * Column:    contract_number
     * Nullable:  true
     */
    private String contractNumber;

    /**
     * 合同签订日期
     *
     * Table:     up_worker_in
     * Column:    contract_sign_date
     * Nullable:  true
     */
    private Date contractSignDate;

    /**
     * 合同开始时间
     *
     * Table:     up_worker_in
     * Column:    contract_start_date
     * Nullable:  true
     */
    private Date contractStartDate;

    /**
     * 合同结束时间
     *
     * Table:     up_worker_in
     * Column:    contract_end_date
     * Nullable:  true
     */
    private Date contractEndDate;

    /**
     * 合同期限类型
     *
     * Table:     up_worker_in
     * Column:    contract_limit_type
     * Nullable:  true
     */
    private String contractLimitType;

    /**
     * 计薪方式
     *
     * Table:     up_worker_in
     * Column:    salary_way
     * Nullable:  true
     */
    private String salaryWay;

    /**
     * 每月发薪日
     *
     * Table:     up_worker_in
     * Column:    pay_day
     * Nullable:  true
     */
    private Integer payDay;

    /**
     * 薪资
     *
     * Table:     up_worker_in
     * Column:    salary_money
     * Nullable:  true
     */
    private BigDecimal salaryMoney;

    /**
     * 工资卡开户银行
     *
     * Table:     up_worker_in
     * Column:    salary_bank
     * Nullable:  true
     */
    private String salaryBank;

    /**
     * 工资卡账号
     *
     * Table:     up_worker_in
     * Column:    salary_bank_no
     * Nullable:  true
     */
    private String salaryBankNo;

    /**
     * 工资卡所属银行支行名称
     *
     * Table:     up_worker_in
     * Column:    salary_bank_branch
     * Nullable:  true
     */
    private String salaryBankBranch;

    /**
     * 人员类型
     *
     * Table:     up_worker_in
     * Column:    member_type
     * Nullable:  true
     */
    private String memberType;

    /**
     * 人员角色
     *
     * Table:     up_worker_in
     * Column:    member_role
     * Nullable:  true
     */
    private String memberRole;

    /**
     * 工种
     *
     * Table:     up_worker_in
     * Column:    work_type
     * Nullable:  true
     */
    private String workType;

    /**
     * 是否参加城乡居民医疗保险
     *
     * Table:     up_worker_in
     * Column:    is_medical_insurance
     * Nullable:  true
     */
    private String isMedicalInsurance;

    /**
     * 是否参加城乡居民养老保险
     *
     * Table:     up_worker_in
     * Column:    is_endowment_insurance
     * Nullable:  true
     */
    private String isEndowmentInsurance;

    /**
     * 是否购买工伤保险
     *
     * Table:     up_worker_in
     * Column:    is_employ_insurance
     * Nullable:  true
     */
    private String isEmployInsurance;

    /**
     * 是否购买意外伤害保险
     *
     * Table:     up_worker_in
     * Column:    is_accident_insurance
     * Nullable:  true
     */
    private String isAccidentInsurance;

    /**
     * 上报住建局后返回的唯一编码
     *
     * Table:     up_worker_in
     * Column:    report_code
     * Nullable:  true
     */
    private String reportCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table up_worker_in
     *
     * @mbggenerated Mon Jun 29 15:20:16 CST 2020
     */
    private static final long serialVersionUID = 1L;

    /**
     * 状态 0：进场 1：离场
     */
    private String status;

    /**
     * 部分DTO转换Entity时间格式转换
     * @param workerInDTO
     */
    public void copyDateFormat(UpWorkerInDTO workerInDTO) {
        if(StringUtils.isNoneEmpty(workerInDTO.getEntryDate())){
            this.entryDate = DateUtils.parseDate(workerInDTO.getEntryDate());
        }
        if(StringUtils.isNoneEmpty(workerInDTO.getLeaveDate())){
            this.leaveDate = DateUtils.parseDate(workerInDTO.getLeaveDate());
        }
        if(StringUtils.isNoneEmpty(workerInDTO.getContractSignDate())){
            this.contractSignDate = DateUtils.parseDate(workerInDTO.getContractSignDate());
        }
        if(StringUtils.isNoneEmpty(workerInDTO.getContractStartDate())){
            this.contractStartDate = DateUtils.parseDate(workerInDTO.getContractStartDate());
        }
        if(StringUtils.isNoneEmpty(workerInDTO.getContractEndDate())){
            this.contractEndDate = DateUtils.parseDate(workerInDTO.getContractEndDate());
        }
    }

    @Override
    public CodeEntity initCodeEntity(Integer projId) {
        return CodeEntity.builder()
                .tableId(id)
                .projId(this.projId).build();
    }
}