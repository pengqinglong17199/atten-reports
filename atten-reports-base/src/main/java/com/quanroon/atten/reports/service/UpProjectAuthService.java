package com.quanroon.atten.reports.service;

import com.quanroon.atten.reports.entity.UpProjectAuth;
import com.quanroon.atten.reports.entity.example.UpPlatformAuthExample;
import com.quanroon.atten.reports.entity.example.UpProjectAuthExample;

import java.util.List;

/**
 * 项目鉴权service
 * @author 彭清龙
 * @date 2020/6/30 17:39
 */
public interface UpProjectAuthService {

    /**
     * 条件查询项目鉴权
     * @param example
     * @return java.util.List<com.quanroon.atten.reports.entity.UpProjectAuth>
     * @author 彭清龙
     * @date 2020/6/30 17:45
     */
    List<UpProjectAuth> selectByExample(UpProjectAuthExample example);

    /**
     * 初始化项目鉴权
     * @param projId
     * @return void
     * @author 彭清龙
     * @date 2020/7/1 11:51
     */
    void initAuth(Integer projId);
}
