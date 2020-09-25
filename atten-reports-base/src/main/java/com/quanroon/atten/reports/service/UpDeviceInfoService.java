package com.quanroon.atten.reports.service;

import com.quanroon.atten.reports.entity.UpDeviceInfo;

import java.util.Map;

/**
 * @author quanroon.ysq
 * @version 1.0.0
 * @content
 * @date 2020/7/1 17:57
 */
public interface UpDeviceInfoService {
    /**
     * 保存或更新（解绑）信息
     * @param upDeviceInfo
     */
    Map<String, Object> saveDeivceInfo(UpDeviceInfo upDeviceInfo);

    /**
     * 解绑考勤机信息
     * @param upDeviceInfo
     * @return
     */
    Map<String, Object> removeDeviceInfo(UpDeviceInfo upDeviceInfo);

    /**
    * @Description: 查询考勤机
    * @Author: ysx
    * @Date: 2020/7/10
    */
    UpDeviceInfo findDeviceInfoById(Integer deciceId);
}
