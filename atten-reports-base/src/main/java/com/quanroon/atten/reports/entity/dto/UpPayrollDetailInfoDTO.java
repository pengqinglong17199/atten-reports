package com.quanroon.atten.reports.entity.dto;


import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description: 工资单明细DTO
 * @Author: ysx
 * @Date: 2020/6/30
 */

@Data
public class UpPayrollDetailInfoDTO {

    private Integer payrollDetailId;
    private Integer workerId;
    private Integer salaryId;
    private Integer attendanceDays;
    private BigDecimal payableMoney;
    private BigDecimal paidMoney;
    private String checkMark;
    private String isReissue;
    private Date reissueMonth;
    private String payRollFileName;
    private String payRollFilePath;
    private String payRollCertificatePath;

}
