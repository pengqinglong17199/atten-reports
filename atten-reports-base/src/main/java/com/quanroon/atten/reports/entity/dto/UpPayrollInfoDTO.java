package com.quanroon.atten.reports.entity.dto;


import lombok.Data;

import java.util.Date;

/**
 * @Description: 工资单DTO
 * @Author: ysx
 * @Date: 2020/6/30
 */

@Data
public class UpPayrollInfoDTO {

    private Integer payrollId;
    private Integer groupId;
    private Date grantSalaryDate;
    private String grantStatus;
    private String confirmMark;

}
