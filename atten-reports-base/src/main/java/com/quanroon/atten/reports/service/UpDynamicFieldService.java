package com.quanroon.atten.reports.service;

import com.quanroon.atten.commons.jwt.IJWTInfo;
import com.quanroon.atten.reports.entity.UpDynamicField;
import com.quanroon.atten.reports.entity.dto.CityParamsDTO;
import com.quanroon.atten.reports.entity.example.UpDynamicFieldExample;
import com.quanroon.atten.reports.entity.vo.CityParamVO;

import java.util.List;

/**
 * 动态字段service
 * @author 彭清龙
 * @date 2020/7/6 14:04
 */
public interface UpDynamicFieldService {

    /**
     * 条件查询项目
     * @param example
     * @return java.util.List<com.quanroon.atten.reports.entity.UpPlatformAuth>
     * @author 彭清龙
     * @date 2020/6/30 17:21
     */
    List<UpDynamicField> selectByExample(UpDynamicFieldExample example);

    /**
     * 获取上报方法的必填字段（平台级别）
     * @param cityParamsDto
     * @param ijwtInfo
     * @return
     */
    List<CityParamVO> getDynamicFieldList(CityParamsDTO cityParamsDto, IJWTInfo ijwtInfo);
}
