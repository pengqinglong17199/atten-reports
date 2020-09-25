package com.quanroon.atten.reports.entity.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 上报参数配置dto
 * @author 彭清龙
 * @date 2020/7/1 10:07
 */
@Data
public class UpParamDTO {

    private Integer projId;         // 项目id
    private String account;         // 账号
    private String password;        // 密码
    private String projectCode;     // 项目编号
    private String key;             // 授权账号
    private String secret;          // 授权密钥
    private String collectKey;      // 采集key
    private String collectSecret;   // 采集密钥
    private String appToken;        // 厂家识别码
    @Length(max = 6,min = 1,message = "省编码格式不对")
    private String upProvince;      // 省
    @Length(max = 6,min = 1,message = "市编码格式不对")
    private String upCity;          // 市
    @Length(max = 6,min = 1,message = "区编码格式不对")
    private String upArea;          // 区
}
