package com.quanroon.atten.reports.entity.dto;

import com.quanroon.atten.commons.annotation.JsonValueValidate;
import com.quanroon.atten.commons.enums.ValidateTypeEnum;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author quanroon.ysq
 * @version 1.0.0
 * @content
 * @date 2020/7/1 16:59
 */
@Data
public class UpWorkerInDTO implements Serializable {

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
    @JsonValueValidate(formats = "yyyy-MM-dd", message = "时间格式为yyyy-MM-dd HH:mm:ss")
    private String entryDate;

    /**
     * 离场日期
     *
     * Table:     up_worker_in
     * Column:    leave_date
     * Nullable:  true
     */
    @JsonValueValidate(formats = "yyyy-MM-dd hh:mm:ss", message = "时间格式为yyyy-MM-dd hh:mm:ss")
    private String leaveDate;

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
    @Length(min = 11, max = 11,message = "联系电话必须11位")
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
    @JsonValueValidate(formats = "yyyy-MM-dd", message = "时间格式为yyyy-MM-dd")
    private String contractSignDate;

    /**
     * 合同开始时间
     *
     * Table:     up_worker_in
     * Column:    contract_start_date
     * Nullable:  true
     */
    @JsonValueValidate(formats = "yyyy-MM-dd", message = "时间格式为yyyy-MM-dd")
    private String contractStartDate;

    /**
     * 合同结束时间
     *
     * Table:     up_worker_in
     * Column:    contract_end_date
     * Nullable:  true
     */
    @JsonValueValidate(formats = "yyyy-MM-dd", message = "时间格式为yyyy-MM-dd")
    private String contractEndDate;

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
     * 劳务合同附件(base64编码，带标签头）
     */
    private String contractFile;
    /**
     * 劳务合同名称（带后缀）
     */
    private String contractTitle;
    /**
     *公司保险凭证照片(base64编码，带标签头）
     */
    private String insurancePicture;
    /**
     *证书照片(base64编码，带标签头）
     */
    private String certificatePicture;
    /**
     *银行卡照片(base64编码，带标签头）
     */
    private String bankPicture;
}
