package com.quanroon.atten.reports.entity.dto;

import com.quanroon.atten.commons.annotation.JsonValueValidate;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author quanroon.ysq
 * @version 1.0.0
 * @content
 * @date 2020/7/1 14:23
 */
@Data
public class UpProjectCertificateDTO implements Serializable {
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
    @JsonValueValidate(formats = "yyyy-MM-dd",message = "时间格式为：yyyy-MM-dd")
    private String builderLicenseDate;
}
