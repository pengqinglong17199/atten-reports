package com.quanroon.atten.reports.entity;

import io.swagger.models.auth.In;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 罗森林
 * @Auther:
 * @Date: 2020-09-09 15:51
 * @Description:
 */
@Data
public class UpProjectCompany  implements Serializable {
    private Integer id;
    /** 项目id */
    private Integer projId;
    /**  统一信用代码 */
    private String creditCode;
    /** 单位类型 */
    private String companyType;
}
