package com.quanroon.atten.reports.entity;

import com.quanroon.atten.commons.utils.DateUtils;
import com.quanroon.atten.commons.utils.StringUtils;
import com.quanroon.atten.reports.common.ReportConstant;
import com.quanroon.atten.reports.entity.base.CodeEntity;
import com.quanroon.atten.reports.entity.base.EntityInterface;
import com.quanroon.atten.reports.entity.base.TableEntity;
import com.quanroon.atten.reports.entity.dto.UpGroupInfoDTO;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Table: up_group_info
 */
@Data
@TableEntity(ReportConstant.UP_GROUP_INFO)
public class UpGroupInfo implements Serializable, EntityInterface<UpGroupInfo> {
    /**
     * 主码
     *
     * Table:     up_group_info
     * Column:    id
     * Nullable:  false
     */
    private Integer id;

    /**
     * 企业id
     *
     * Table:     up_group_info
     * Column:    company_id
     * Nullable:  true
     */
    private Integer companyId;

    /**
     * 项目id
     *
     * Table:     up_group_info
     * Column:    proj_id
     * Nullable:  true
     */
    private Integer projId;

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
    private String egyContactPhone;

    /**
     * 班组类型
     *
     * Table:     up_group_info
     * Column:    group_type
     * Nullable:  true
     */
    private String groupType;

    /**
     * 进场日期
     *
     * Table:     up_group_info
     * Column:    entry_date
     * Nullable:  true
     */
    private Date entryDate;

    /**
     * 出厂日期
     *
     * Table:     up_group_info
     * Column:    leave_date
     * Nullable:  true
     */
    private Date leaveDate;

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
     * 创建日期
     *
     * Table:     up_group_info
     * Column:    create_date
     * Nullable:  true
     */
    private Date createDate;

    /**
     * 修改时间
     *
     * Table:     up_group_info
     * Column:    update_date
     * Nullable:  true
     */
    private Date updateDate;

    /**
     * 上报住建局后返回的唯一编码
     *
     * Table:     up_group_info
     * Column:    report_code
     * Nullable:  true
     */
    private String reportCode;
    /**
     * 状态 0:进场 1:离场
     */
    private String status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table up_group_info
     *
     * @mbggenerated Mon Jun 29 15:19:03 CST 2020
     */
    private static final long serialVersionUID = 1L;

    /**
     * 部分DTO转换Entity时间格式转换
     * @param groupInfoDTO
     */
    public void copyDateFormat(UpGroupInfoDTO groupInfoDTO) {
        if(StringUtils.isNoneEmpty(groupInfoDTO.getEntryDate())){
            this.entryDate = DateUtils.parseDate(groupInfoDTO.getEntryDate());
        }
    }

    @Override
    public CodeEntity initCodeEntity(Integer projId) {
        return CodeEntity.builder()
                .tableId(id)
                .projId(this.projId).build();
    }
}