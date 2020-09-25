package com.quanroon.atten.reports.entity.dto;

import lombok.Data;

/**
 * 平台密钥dto
 * @author 彭清龙
 * @date 2020/6/29 17:02
 */
@Data
public class PlatformAuthDTO {

    private String appId;
    private String appKey;
    private String version;
}
