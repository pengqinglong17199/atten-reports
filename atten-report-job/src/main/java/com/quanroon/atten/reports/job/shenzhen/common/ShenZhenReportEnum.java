package com.quanroon.atten.reports.job.shenzhen.common;

import com.quanroon.atten.reports.common.EnumsInterface;

/**
* 深圳上报平台枚举
* 
* @Author: ysx
* @Date: 2020/8/17
*/
public enum ShenZhenReportEnum implements EnumsInterface {

    //水务局
    SHUIWUJU("1");

    private String code;

    ShenZhenReportEnum(String code){
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
