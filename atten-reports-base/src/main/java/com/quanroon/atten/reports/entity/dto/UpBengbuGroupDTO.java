package com.quanroon.atten.reports.entity.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 罗森林
 * @Auther:
 * @Date: 2020-09-01 11:12
 * @Description:
 */
@Data
public class UpBengbuGroupDTO implements Serializable {
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
     * 出场日期
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
    private String contractMoney;

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
     * 关联项目编码
     * @auther: 罗森林
     * @param:
     * @return:
     * @date: 2020-09-01 10:57
     */
    private String projNo;
    /**
     * 行政区划代码
     * @auther: 罗森林
     * @param:
     * @return:
     * @date: 2020-09-01 11:03
     */
    private String cityCode;
    /**
     * 社会统一信用代码
     * @auther: 罗森林
     * @param:
     * @return:
     * @date: 2020-09-01 11:06
     */
    private String corpCode;
    /**
     * 工种类型
     * @auther: 罗森林
     * @param:
     * @return:
     * @date: 2020-09-01 11:16
     */
    private String workType;
    /**
     * 工资类型
     * @auther: 罗森林
     * @param:
     * @return:
     * @date: 2020-09-01 11:16
     */
    private String salaryType;
}
