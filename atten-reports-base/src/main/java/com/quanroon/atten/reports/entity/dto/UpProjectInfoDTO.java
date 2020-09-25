package com.quanroon.atten.reports.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.quanroon.atten.commons.annotation.JsonValueValidate;
import com.quanroon.atten.reports.entity.UpProjectCompany;
import com.quanroon.atten.reports.entity.UpSalaryInfo;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author quanroon.ysq
 * @version 1.0.0
 * @content
 * @date 2020/6/30 20:34
 */
@Data
public class UpProjectInfoDTO implements Serializable {
    /**
     * 用于校验当前项目上报到哪个城市所需的必填字段
     * 基于平台数据才有
     */
    @NotEmpty(message = "待上报城市区域编码不能为空")
    private String reportCityCode;
    /**
     * 项目id(修改时必填,默认新增)
     */
    private Integer projId;
    /**
     * 项目名称
     *
     * Table:     up_project_info
     * Column:    name
     * Nullable:  true
     */
    private String name;

    /**
     * 项目分类
     *
     * Table:     up_project_info
     * Column:    type
     * Nullable:  true
     */
    @Length(max = 4, min = 4, message = "请参考项目分类词典")
    private String type;

    /**
     * 项目地址
     *
     * Table:     up_project_info
     * Column:    address
     * Nullable:  true
     */
    private String address;

    /**
     * 精度
     *
     * Table:     up_project_info
     * Column:    lat
     * Nullable:  true
     */
    private String lat;

    /**
     * 纬度
     *
     * Table:     up_project_info
     * Column:    lgt
     * Nullable:  true
     */
    private String lgt;

    /**
     * 项目状态
     *
     * Table:     up_project_info
     * Column:    status
     * Nullable:  true
     */
    @Length(max = 4, min = 4, message = "请参考项目状态词典")
    private String status;

    /**
     * 项目计划开工时间
     *
     * Table:     up_project_info
     * Column:    start_date
     * Nullable:  true
     */
    @JsonValueValidate(formats = "yyyy-MM-dd HH:mm:ss",message = "时间格式为：yyyy-MM-dd HH:mm:ss")
    private String startDate;

    /**
     * 项目计划竣工时间
     *
     * Table:     up_project_info
     * Column:    end_date
     * Nullable:  true
     */
    @JsonValueValidate(formats = "yyyy-MM-dd HH:mm:ss",message = "时间格式为：yyyy-MM-dd HH:mm:ss")
    private String endDate;

    /**
     * 竣工验收日期
     *
     * Table:     up_project_info
     * Column:    finish_date
     * Nullable:  true
     */
    @JsonValueValidate(formats = "yyyy-MM-dd HH:mm:ss",message = "时间格式为：yyyy-MM-dd HH:mm:ss")
    private String finishDate;

    /**
     * 竣工验收编号
     *
     * Table:     up_project_info
     * Column:    finish_check_no
     * Nullable:  true
     */
    private String finishCheckNo;

    /**
     * 镇街等级
     *
     * Table:     up_project_info
     * Column:    sup_level
     * Nullable:  true
     */
    private String supLevel;

    /**
     * 项目经理
     *
     * Table:     up_project_info
     * Column:    manager_name
     * Nullable:  true
     */
    private String managerName;

    /**
     * 项目经理联系电话
     *
     * Table:     up_project_info
     * Column:    manager_phone
     * Nullable:  true
     */
    private String managerPhone;

    /**
     * 省级编码
     *
     * Table:     up_project_info
     * Column:    province_code
     * Nullable:  true
     */
    private String provinceCode;

    /**
     * 市级编码
     *
     * Table:     up_project_info
     * Column:    city_code
     * Nullable:  true
     */
    private String cityCode;

    /**
     * 项目所在地
     *
     * Table:     up_project_info
     * Column:    area_code
     * Nullable:  true
     */
    private String areaCode;

    /**
     * 项目总工期
     *
     * Table:     up_project_info
     * Column:    period
     * Nullable:  true
     */
    private Integer period;

    /**
     * 资金来源
     *
     * Table:     up_project_info
     * Column:    capital_source
     * Nullable:  true
     */
    private String capitalSource;

    /**
     * 劳资专管员姓名
     *
     * Table:     up_project_info
     * Column:    labour_name
     * Nullable:  true
     */
    private String labourName;

    /**
     * 劳资专管员电话
     *
     * Table:     up_project_info
     * Column:    labour_mobile
     * Nullable:  true
     */
    private String labourMobile;

    /**
     * 劳资专管员身份证号码
     *
     * Table:     up_project_info
     * Column:    labour_card
     * Nullable:  true
     */
    private String labourCard;

    /**
     * 项目保障金预存金额
     *
     * Table:     up_project_info
     * Column:    cash_deposit
     * Nullable:  true
     */
    private BigDecimal cashDeposit;

    /**
     * 项目所属管辖
     *
     * Table:     up_project_info
     * Column:    government
     * Nullable:  true
     */
    private String government;

    /**
     * 行业主管部门
     *
     * Table:     up_project_info
     * Column:    department
     * Nullable:  true
     */
    private String department;

    /**
     * 建设性质
     *
     * Table:     up_project_info
     * Column:    build_type
     * Nullable:  true
     */
    private String buildType;

    /**
     * 投资类型
     *
     * Table:     up_project_info
     * Column:    invest_type
     * Nullable:  true
     */
    private String investType;

    /**
     * 总面积
     *
     * Table:     up_project_info
     * Column:    sum_acreage
     * Nullable:  true
     */
    private Double sumAcreage;

    /**
     * 总投资
     *
     * Table:     up_project_info
     * Column:    sum_invest
     * Nullable:  true
     */
    private Double sumInvest;

    /**
     * 项目规模
     *
     * Table:     up_project_info
     * Column:    proj_scale
     * Nullable:  true
     */
    private Double projScale;

    /**
     * 项目状态变更时间
     *
     * Table:     up_project_info
     * Column:    status_change_date
     * Nullable:  true
     */
    @JsonValueValidate(formats = "yyyy-MM-dd HH:mm:ss",message = "时间格式为：yyyy-MM-dd HH:mm:ss")
    private String statusChangeDate;

    /**
     * 安监备案号
     *
     * Table:     up_project_info
     * Column:    safety_no
     * Nullable:  true
     */
    private String safetyNo;

    /**
     * 项目合同编号
     *
     * Table:     up_project_info
     * Column:    proj_contract_code
     * Nullable:  true
     */
    private String projContractCode;

    /**
     * 项目合同签订日期
     *
     * Table:     up_project_info
     * Column:    proj_contract_sign_date
     * Nullable:  true
     */
    @JsonValueValidate(formats = "yyyy-MM-dd HH:mm:ss",message = "时间格式为：yyyy-MM-dd HH:mm:ss")
    private String projContractSignDate;

    /**
     * 签约合同价
     *
     * Table:     up_project_info
     * Column:    proj_contract_price
     * Nullable:  true
     */
    private String projContractPrice;

    /**
     * 施工许可证集合字段
     */
    @Valid
    private List<UpProjectCertificateDTO> builderLicenses;
    /**
     * 关联项目的社会统一信用代码
     * @auther: 罗森林
     * @param:
     * @return:
     * @date: 2020-09-09 15:53
     */
    @Valid
    private List<UpProjectCompany> projCompanies;

    private UpSalaryInfo upSalaryInfo;
}
