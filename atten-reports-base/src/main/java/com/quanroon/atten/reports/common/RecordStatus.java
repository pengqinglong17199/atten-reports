package com.quanroon.atten.reports.common;

/**
 * 上报状态
 * @author 彭清龙
 * @date 2020/7/15 14:10
 */
public enum RecordStatus implements EnumsInterface {

    /** 上报中*/
    WAIT("1", "上报中"),
    /** 上报成功*/
    SUCCESS("2", "上报成功"),
    /** 上报失败*/
    FAIL("3","上报失败");

    private String val;
    private String message;

    RecordStatus(String val){
        this.val = val;
    }

    RecordStatus(String val, String message){
        this.val = val;
        this.message = message;
    }

    public String val(){
        return val;
    }

    @Override
    public String message(){
        return message;
    }

    @Override
    public String value() {
        return val;
    }
}
