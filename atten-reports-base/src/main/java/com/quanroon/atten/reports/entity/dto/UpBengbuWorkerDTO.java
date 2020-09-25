package com.quanroon.atten.reports.entity.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 罗森林
 * @Auther:
 * @Date: 2020-08-28 09:35
 * @Description:
 */
@Data
public class UpBengbuWorkerDTO implements Serializable {
    /** 项目id */
    private Integer projId;
    /** 行政区划代码*/
    private String areaCode;
    /**施工许可证号*/
    private String builderLicenseNo;
    /**项目名称*/
    private String name;
    /**施工单位编码(统一社会信用代码)*/
    private String constructNo;
    /** 建设单位编码(统一社会信用代码)*/
    private String buildNo;
    /**项目备注名称*/
    private String remarkName;
    /**建设地址.*/
    private String address;
    /**开工日期*/
    private Date startDate;
    /**竣工日期*/
    private Date endDate;
    /**建设规模*/
    private String projScale;
    /**工程状态*/
    private String engStatus;
    /**项目状态*/
    private String status;
    /**项目类型*/
    private String type;

}
