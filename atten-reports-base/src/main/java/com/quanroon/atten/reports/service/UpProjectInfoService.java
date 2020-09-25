package com.quanroon.atten.reports.service;

import com.quanroon.atten.reports.entity.UpProjectInfo;
import com.quanroon.atten.reports.entity.dto.UpBengbuProjDTO;
import com.quanroon.atten.reports.entity.dto.UpProjectInfoDTO;
import com.quanroon.atten.reports.entity.example.UpProjectInfoExample;

import java.util.List;
import java.util.Map;

/**
 * 上报项目service
 * @author 彭清龙
 * @date 2020/6/30 17:20
 */
public interface UpProjectInfoService {

    /**
     * 条件查询项目
     * @param example
     * @return java.util.List<com.quanroon.atten.reports.entity.UpPlatformAuth>
     * @author 彭清龙
     * @date 2020/6/30 17:21
     */
    List<UpProjectInfo> selectByExample(UpProjectInfoExample example);

    /**
     * 以主键查询项目
     * @param id
     * @return com.quanroon.atten.reports.entity.UpProjectInfo
     * @author 彭清龙
     * @date 2020/6/30 18:06
     */
    UpProjectInfo selectByPrimaryKey(Integer id);

    /**
     * 保存或修改项目信息
     * @param upProjectInfo
     * @return
     */
    Map<String,Object> saveUpProjectInfo(UpProjectInfoDTO upProjectInfoDTO, UpProjectInfo upProjectInfo);

    /**
     * 更新上报项目信息
     * @param record
     */
    void updateByPrimaryKey(UpProjectInfo record);

    /**
     * 查询蚌埠上报项目信息
     * @auther: 罗森林
     * @param:
     * @return:
     * @date: 2020-08-28 10:13
     */
    UpBengbuProjDTO selectBengbuProjInfo(Integer id);
}
