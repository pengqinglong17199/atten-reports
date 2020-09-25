package com.quanroon.atten.reports.entity.dto;

import com.quanroon.atten.commons.annotation.JsonValueValidate;
import com.quanroon.atten.commons.enums.ValidateTypeEnum;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * @author quanroon.ysq
 * @version 1.0.0
 * @content
 * @date 2020/6/30 19:54
 */
@Data
public class UpCompanyInDTO implements Serializable {

    //统一社会信用代码
    private String corpCode;

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
     * 劳资专管员身份证号码
     *
     * Table:     up_company_in
     * Column:    labour_card
     * Nullable:  true
     */
    private String labourCard;


    /**
     * 劳资专管员电话
     *
     * Table:     up_company_in
     * Column:    labour_phone
     * Nullable:  true
     */
    private String labourPhone;

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
    @JsonValueValidate(formats = "yyyy-MM-dd HH:mm:ss",message = "时间格式为yyyy-MM-dd HH:mm:ss")
    private String inDate;

    /**
     * 离场时间
     *
     * Table:     up_company_in
     * Column:    out_date
     * Nullable:  true
     */
    @JsonValueValidate(formats = "yyyy-MM-dd HH:mm:ss",message = "时间格式为yyyy-MM-dd HH:mm:ss")
    private String outDate;

    /**
     * 参见类型
     *
     * Table:     up_company_in
     * Column:    build_type
     * Nullable:  true
     */
    @Length(min = 4,max = 4,message = "请参考参见类型词典")
    private String buildType;
}
