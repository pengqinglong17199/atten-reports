package com.quanroon.atten.reports.entity.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description: 工资专户到账DTO
 * @Author: ysx
 * @Date: 2020/6/30
 */

@Data
public class UpSalaryArriveDTO {

    private Integer salaryArriveId;
    private Integer salaryAccountId;
    private BigDecimal amountReceived;
    private Date amountDate;
    private BigDecimal remainingWages;

}
