package com.quanroon.atten.reports.entity;

import java.io.Serializable;
import java.util.Date;

import com.quanroon.atten.commons.utils.DateUtils;
import com.quanroon.atten.commons.utils.StringUtils;
import com.quanroon.atten.reports.common.ReportConstant;
import com.quanroon.atten.reports.entity.base.CodeEntity;
import com.quanroon.atten.reports.entity.base.EntityInterface;
import com.quanroon.atten.reports.entity.base.TableEntity;
import com.quanroon.atten.reports.entity.dto.UpCompanyInDTO;
import com.quanroon.atten.reports.entity.dto.UpCompanyInfoDTO;
import lombok.Data;

/**
 * Table: up_company_in
 */
@Data
@TableEntity(ReportConstant.UP_COMPANY_IN)
public class UpCompanyIn implements Serializable, EntityInterface<UpCompanyIn> {
    /**
     * 主键id
     */
    private Integer id;
    /**
     * 企业id
     *
     * Table:     up_company_in
     * Column:    company_id
     * Nullable:  false
     */
    private Integer companyId;

    /**
     * 项目id
     *
     * Table:     up_company_in
     * Column:    proj_id
     * Nullable:  true
     */
    private Integer projId;

    /**
     * 项目负责人
     *
     * Table:     up_company_in
     * Column:    proj_leader_name
     * Nullable:  true
     */
    private String projLeaderName;

    /**
     * 项目负责人电话
     *
     * Table:     up_company_in
     * Column:    proj_leader_phone
     * Nullable:  true
     */
    private String projLeaderPhone;

    /**
     * 劳资专管员
     *
     * Table:     up_company_in
     * Column:    labour_name
     * Nullable:  true
     */
    private String labourName;

    /**
     * 劳资专管员电话
     *
     * Table:     up_company_in
     * Column:    labour_phone
     * Nullable:  true
     */
    private String labourPhone;

    /**
     * 劳资专管员身份证号码
     *
     * Table:     up_company_in
     * Column:    labour_card
     * Nullable:  true
     */
    private String labourCard;


    /**
     * 单位联系人
     *
     * Table:     up_company_in
     * Column:    contacts_name
     * Nullable:  true
     */
    private String contactsName;

    /**
     * 单位联系人电话
     *
     * Table:     up_company_in
     * Column:    contacts_phone
     * Nullable:  true
     */
    private String contactsPhone;

    /**
     * 保证金
     *
     * Table:     up_company_in
     * Column:    desposit
     * Nullable:  true
     */
    private Double desposit;

    /**
     * 是否纳入监管,1:纳入2:不纳入
     *
     * Table:     up_company_in
     * Column:    supervision_status
     * Nullable:  true
     */
    private String supervisionStatus;

    /**
     * 进场时间
     *
     * Table:     up_company_in
     * Column:    in_date
     * Nullable:  true
     */
    private Date inDate;

    /**
     * 出场时间
     *
     * Table:     up_company_in
     * Column:    out_date
     * Nullable:  true
     */
    private Date outDate;

    /**
     * 上报住建局后返回的唯一编码
     *
     * Table:     up_company_in
     * Column:    report_code
     * Nullable:  true
     */
    private String reportCode;

    /**
     * 参见类型
     *
     * Table:     up_company_in
     * Column:    build_type
     * Nullable:  true
     */
    private String buildType;

    /**
     * 进离场状态
     */
    private String status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table up_company_in
     *
     * @mbggenerated Mon Jun 29 14:58:45 CST 2020
     */
    private static final long serialVersionUID = 1L;

    /**
     * 部分DTO转换Entity时间格式转换
     * @param companyInDTO
     */
    public void copyDateFormat(UpCompanyInDTO companyInDTO) {
        if (StringUtils.isNoneEmpty(companyInDTO.getInDate())) {
            this.inDate = DateUtils.parseDate(companyInDTO.getInDate());
        }
        if (StringUtils.isNoneEmpty(companyInDTO.getOutDate())) {
            this.outDate = DateUtils.parseDate(companyInDTO.getOutDate());
        }

    }

    @Override
    public CodeEntity initCodeEntity(Integer projId) {
        return CodeEntity.builder()
                .tableId(id)
                .projId(this.projId).build();
    }
}