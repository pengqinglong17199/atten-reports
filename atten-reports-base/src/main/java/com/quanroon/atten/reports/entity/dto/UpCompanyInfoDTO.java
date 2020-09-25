package com.quanroon.atten.reports.entity.dto;

import com.quanroon.atten.commons.annotation.JsonValueValidate;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author quanroon.ysq
 * @version 1.0.0
 * @content 企业（参见单位）信息
 * @date 2020/6/30 18:55
 */
@Data
public class UpCompanyInfoDTO implements Serializable {
    //统一社会信用代码
    private String corpCode;
    //企业名称
    private String name;
    //企业类型
    @Length(min = 4,max = 4, message = "请参考企业类型词典")
    private String type;
    //企业邮箱
    private String email;
    //省级编码
    private String provinceCode;
    //市级编码
    private String cityCode;
    //区级编码
    private String areaCode;
    //企业营业地址
    private String address;
    //邮政编码
    private String zipCode;
    //法人代表
    private String legalName;
    //法人电话
    private String legalPhone;
    //法人电话
    private String legalCard;
    //企业注册日期
    @JsonValueValidate(formats = "yyyy-MM-dd", message = "时间格式yyyy-MM-dd")
    private String registerDate;
    //注册资金
    private BigDecimal registerAmount;
}
