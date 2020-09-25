package com.quanroon.atten.reports.utils;

import com.quanroon.atten.commons.utils.StringUtils;
import com.quanroon.atten.reports.entity.UpParams;

/**
 * @author quanroon.ysq
 * @version 1.0.0
 * @content
 * @date 2020/7/22 20:02
 */
public final class ReportUtils {

    /**
     * 
     * 获取城市code   // 优先级 区 > 市 > 省
     * @param upParams
     * @return java.lang.String
     */
    public static String getCode(UpParams upParams){
        if(upParams == null){
            return null;
        }
        String code = null;
        if(StringUtils.isNotEmpty(upParams.getUpArea())&& !"0".equals(upParams.getUpArea())){
            // 返回区code
            code = upParams.getUpArea();
        }else if(StringUtils.isNotEmpty(upParams.getUpCity()) && !"0".equals(upParams.getUpCity())){
            // 返回市code
            code = upParams.getUpCity();
        }else if(StringUtils.isNotEmpty(upParams.getUpProvince())){
            // 返回省code
            code = upParams.getUpProvince();
        }
        return code;
    }
}
