package com.quanroon.atten.reports.service;

import com.quanroon.atten.reports.entity.UpPlatformAuth;
import com.quanroon.atten.reports.entity.example.UpPlatformAuthExample;
import com.quanroon.atten.reports.entity.example.UpPlatmeAuthExample;

import java.util.List;

/**
 * 平台用户service
 * @author 彭清龙
 * @date 2020/6/29 16:44
 */
public interface UpPlatformAuthService {

    /**
     * 条件查询平台用户
     * @param example
     * @return java.util.List<com.quanroon.atten.reports.entity.UpPlatmeAuth>
     * @author 彭清龙
     * @date 2020/6/29 16:50
     */
    List<UpPlatformAuth> selectByExample(UpPlatformAuthExample example);


}
