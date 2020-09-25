package com.quanroon.atten.reports.entity.vo;

import com.quanroon.atten.reports.entity.UpDynamicField;
import lombok.Data;

/**
 * 动态字段必填vo
 * @author 彭清龙
 * @date 2020/7/6 14:12
 */
@Data
public class CityParamVO {

    private String paramsName;
    private Boolean isRequired = true;
    private Boolean isModify;

    public CityParamVO(UpDynamicField field) {
        this.paramsName = field.getFieldName();
        this.isModify = "1".equals(field.getIsModify());
    }
}
