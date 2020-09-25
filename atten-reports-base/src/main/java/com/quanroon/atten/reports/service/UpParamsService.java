package com.quanroon.atten.reports.service;

import com.quanroon.atten.reports.entity.UpParams;

import java.util.Map;

/**
 * 上报项目参数配置service
 * @author 彭清龙
 * @date 2020/7/1 10:47
 */
public interface UpParamsService {

    /**
     * 保存项目参数配置
     * @param upParams
     * @return int
     * @author 彭清龙
     * @date 2020/7/1 10:49
     */
    Map<String,Object> saveProjConfig(UpParams upParams);

    /**
     * 根据项目id查询住建配置，并获取上报城市的code
     * @param upParams
     * @return
     */
    String getReportCityCode(UpParams upParams);

}
