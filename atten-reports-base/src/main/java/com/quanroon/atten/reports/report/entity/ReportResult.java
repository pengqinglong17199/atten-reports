package com.quanroon.atten.reports.report.entity;

/**
 * 上报响应实体
 * @author 彭清龙
 * @date 2020-05-06 21:18:05
 */
public interface ReportResult extends ReportEntity {

    /**
     * 返回上报是否成功 true:成功 false:不成功
     * @return boolean
     * @author 彭清龙
     * @date 2020/5/26 15:31
     */
    boolean isSuccess();

    /**
     * 返回调用接口失败的错误信息
     * @return java.lang.String
     * @author 彭清龙
     * @date 2020/7/15 11:27
     */
    String getMessage();
}
