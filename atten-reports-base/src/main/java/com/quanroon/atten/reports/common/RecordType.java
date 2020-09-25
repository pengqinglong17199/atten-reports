package com.quanroon.atten.reports.common;

/**
 * 上报类型枚举
 * @author 彭清龙
 * @date 2020/7/14 11:21
 */
public enum RecordType implements EnumsInterface {

    /** 真实上报*/
    REAL_RETPOR("1", "真实上报"),
    /** 虚假上报*/
    SHAM_REPORT("2", "虚假上报");

    private String code;
    private String message;

    RecordType(String code){
        this.code = code;
    }

    RecordType(String code, String message){
        this.code = code;
        this.message = message;
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
        return message;
    }
}
