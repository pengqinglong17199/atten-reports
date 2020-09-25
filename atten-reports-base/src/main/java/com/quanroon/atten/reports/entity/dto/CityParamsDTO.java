package com.quanroon.atten.reports.entity.dto;

import lombok.Data;

/**
 * 动态字段dto
 * @author 彭清龙
 * @date 2020/7/1 19:19
 */
@Data
public class CityParamsDTO {

    private String cityCode;    // 上报城市编码
    private String methodName;  // 上报功能字典
}
