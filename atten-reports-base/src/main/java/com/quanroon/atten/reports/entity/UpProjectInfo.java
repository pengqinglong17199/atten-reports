package com.quanroon.atten.reports.entity;

import com.quanroon.atten.commons.utils.DateUtils;
import com.quanroon.atten.commons.utils.StringUtils;
import com.quanroon.atten.reports.common.ReportConstant;
import com.quanroon.atten.reports.entity.base.CodeEntity;
import com.quanroon.atten.reports.entity.base.EntityInterface;
import com.quanroon.atten.reports.entity.base.TableEntity;
import com.quanroon.atten.reports.entity.dto.UpProjectInfoDTO;
import com.quanroon.atten.reports.entity.dto.UpWorkerInDTO;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Table: up_project_info
 */
@Data
@TableEntity(ReportConstant.UP_PROJECT_INFO)
public class UpProjectInfo implements Serializable, EntityInterface<UpProjectInfo> {

    public static final String CACHE_PREFIX = ":proj";
    /**
     * 主码
     *
     * Table:     up_project_info
     * Column:    id
     * Nullable:  false
     */
    private Integer id;

    /**
     * 平台id
     *
     * Table:     up_project_info
     * Column:    platform_id
     * Nullable:  true
     */
    private Integer platformId;

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
    private Double lat;

    /**
     * 纬度
     *
     * Table:     up_project_info
     * Column:    lgt
     * Nullable:  true
     */
    private Double lgt;

    /**
     * 项目状态
     *
     * Table:     up_project_info
     * Column:    status
     * Nullable:  true
     */
    private String status;

    /**
     * 项目计划开工时间
     *
     * Table:     up_project_info
     * Column:    start_date
     * Nullable:  true
     */
    private Date startDate;

    /**
     * 项目计划竣工时间
     *
     * Table:     up_project_info
     * Column:    end_date
     * Nullable:  true
     */
    private Date endDate;

    /**
     * 竣工验收日期
     *
     * Table:     up_project_info
     * Column:    finish_date
     * Nullable:  true
     */
    private Date finishDate;

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
    private Date statusChangeDate;

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
    private Date projContractSignDate;

    /**
     * 签约合同价
     *
     * Table:     up_project_info
     * Column:    proj_contract_price
     * Nullable:  true
     */
    private String projContractPrice;

    /**
     * 入库时间
     *
     * Table:     up_project_info
     * Column:    create_date
     * Nullable:  true
     */
    private Date createDate;

    /**
     * 更新时间
     *
     * Table:     up_project_info
     * Column:    update_date
     * Nullable:  true
     */
    private Date updateDate;

    /**
     * 上报成功后返回的唯一编码
     *
     * Table:     up_project_info
     * Column:    report_code
     * Nullable:  true
     */
    private String reportCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table up_project_info
     *
     * @mbggenerated Tue Jun 30 17:17:38 CST 2020
     */
    private static final long serialVersionUID = 1L;

    /**
     * 部分DTO转换Entity时间格式转换
     * @param projectInfoDTO
     */
    public void copyDateFormat(UpProjectInfoDTO projectInfoDTO) {
        if(StringUtils.isNoneEmpty(projectInfoDTO.getStartDate())){
            this.startDate = DateUtils.parseDate(projectInfoDTO.getStartDate());
        }
        if(StringUtils.isNoneEmpty(projectInfoDTO.getEndDate())){
            this.endDate = DateUtils.parseDate(projectInfoDTO.getEndDate());
        }
        if(StringUtils.isNoneEmpty(projectInfoDTO.getFinishDate())){
            this.finishDate = DateUtils.parseDate(projectInfoDTO.getFinishDate());
        }
        if(StringUtils.isNoneEmpty(projectInfoDTO.getStatusChangeDate())){
            this.statusChangeDate = DateUtils.parseDate(projectInfoDTO.getStatusChangeDate());
        }
        if(StringUtils.isNoneEmpty(projectInfoDTO.getProjContractSignDate())){
            this.projContractSignDate = DateUtils.parseDate(projectInfoDTO.getProjContractSignDate());
        }
    }

    @Override
    public CodeEntity initCodeEntity(Integer projId) {

        return CodeEntity.builder()
                .tableId(id)
                .projId(id).build();
    }
}