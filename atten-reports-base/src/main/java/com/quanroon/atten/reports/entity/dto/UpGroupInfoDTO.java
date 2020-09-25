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
 * @date 2020/7/1 12:14
 */
@Data
public class UpGroupInfoDTO implements Serializable {
    /**
     * 班组id,主键
     */
    private Integer groupId;
    /**
     * 企业id
     *
     * Table:     up_group_info
     * Column:    company_id
     * Nullable:  true
     */
    private Integer companyId;
    /**
     * 班组名称
     *
     * Table:     up_group_info
     * Column:    group_name
     * Nullable:  true
     */
    private String groupName;

    /**
     * 班组长姓名
     *
     * Table:     up_group_info
     * Column:    group_leader_name
     * Nullable:  true
     */
    private String groupLeaderName;

    /**
     * 班组长联系方式
     *
     * Table:     up_group_info
     * Column:    group_leader_phone
     * Nullable:  true
     */
    private String groupLeaderPhone;

    /**
     * 班组长证件号码
     *
     * Table:     up_group_info
     * Column:    group_leader_card
     * Nullable:  true
     */
    private String groupLeaderCard;

    /**
     * 班组长证件类型
     *
     * Table:     up_group_info
     * Column:    group_leader_type
     * Nullable:  true
     */
    @Length(min = 4, max = 4, message = "请参考证件类型词典")
    private String groupLeaderType;

    /**
     * 紧急联系人
     *
     * Table:     up_group_info
     * Column:    egy_contact
     * Nullable:  true
     */
    private String egyContact;

    /**
     * 紧急联系人电话
     *
     * Table:     up_group_info
     * Column:    egy_contact_phone
     * Nullable:  true
     */
    @Length(min = 11, max = 11,message = "联系电话必须11位")
    private String egyContactPhone;

    /**
     * 班组类型
     *
     * Table:     up_group_info
     * Column:    group_type
     * Nullable:  true
     */
    @Length(min = 4, max = 4, message = "请参考班组类型词典")
    private String groupType;

    /**
     * 进场日期
     *
     * Table:     up_group_info
     * Column:    entry_date
     * Nullable:  true
     */
    @JsonValueValidate(formats = "yyyy-MM-dd HH:mm:ss", message = "时间格式yyyy-MM-dd HH:mm:ss")
    private String entryDate;

    /**
     * 承包合同内容
     *
     * Table:     up_group_info
     * Column:    contract_content
     * Nullable:  true
     */
    private String contractContent;

    /**
     * 承包合同金额
     *
     * Table:     up_group_info
     * Column:    contract_money
     * Nullable:  true
     */
    private BigDecimal contractMoney;

    /**
     * 承包合同附件
     */
    private String contractFile;
    /**
     * 承包合同标题（带后缀）
     */
    private String contractTitle;

}
