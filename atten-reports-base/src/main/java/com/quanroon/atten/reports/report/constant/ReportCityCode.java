package com.quanroon.atten.reports.report.constant;

import com.quanroon.atten.reports.common.EnumsInterface;

/**
 * 上报城市枚举
 * @author 彭清龙
 * @date 2020/7/6 17:12
 */
public enum ReportCityCode implements EnumsInterface {

    JINHUA("330700"),// 金华市
    SUZHOU("341300"),//宿州市
    HENAN("410000"),// 河南省
    SHENZHEN("440300"),//深圳
    BENGBU("340300"); //蚌埠市

    private String code;

    ReportCityCode(String code){
        this.code = code;
    }

    public String code(){
        return code;
    }

    @Override
    public String value() {
        return code;
    }

    @Override
    public String message() {
        return null;
    }
}
