package com.quanroon.atten.reports.service;

import com.quanroon.atten.reports.entity.UpCompanyInfo;

import java.util.Map;

/**
 * @author quanroon.ysq
 * @version 1.0.0
 * @content 企业（参见单位）服务层
 * @date 2020/6/29 20:01
 */
public interface UpCompanyService {

    /**
     * 保存企业（参见单位）信息
     * @param upCompanyInfo
     */
    Map<String, Object> saveCompanyInfo(UpCompanyInfo upCompanyInfo);

    /**
     * 根据统一社会信用代码,或企业名称查询，是否已存在信息
     * @param upCompanyInfo
     * @return
     */
    UpCompanyInfo getCompanyByCodeName(UpCompanyInfo upCompanyInfo);


}
