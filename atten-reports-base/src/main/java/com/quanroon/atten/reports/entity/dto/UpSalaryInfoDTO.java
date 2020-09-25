package com.quanroon.atten.reports.entity.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
* @Description: 工资专户DTO
* @Author: ysx
* @Date: 2020/6/30
*/

@Data
public class UpSalaryInfoDTO {

    private Integer salaryAccountId;
    private String accountName;
    private String bankName;
    private String bankCode;
    private String bankAccount;
    private BigDecimal balance;

}
