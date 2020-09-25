package com.quanroon.atten.reports.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * Table: up_project_certificate
 */
@Data
public class UpProjectCertificate implements Serializable {
    /**
     * 项目表id
     *
     * Table:     up_project_certificate
     * Column:    proj_id
     * Nullable:  false
     */
    private Integer projId;

    /**
     * 施工许可证
     *
     * Table:     up_project_certificate
     * Column:    builder_license_no
     * Nullable:  true
     */
    private String builderLicenseNo;

    /**
     * 施工许可证发证机关
     *
     * Table:     up_project_certificate
     * Column:    builder_license_organ
     * Nullable:  true
     */
    private String builderLicenseOrgan;

    /**
     * 发证日期
     *
     * Table:     up_project_certificate
     * Column:    builder_license_date
     * Nullable:  true
     */
    private Date builderLicenseDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table up_project_certificate
     *
     * @mbggenerated Mon Jun 29 15:05:42 CST 2020
     */
    private static final long serialVersionUID = 1L;
}