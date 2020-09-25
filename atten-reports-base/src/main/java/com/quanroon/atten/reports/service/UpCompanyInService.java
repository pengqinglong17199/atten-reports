package com.quanroon.atten.reports.service;

import com.quanroon.atten.reports.entity.UpCompanyIn;
import com.quanroon.atten.reports.entity.UpCompanyInfo;
import com.quanroon.atten.reports.entity.dto.UpCompanyInDTO;

import java.util.Map;

/**
 * @author quanroon.ysq
 * @version 1.0.0
 * @content
 * @date 2020/6/30 20:03
 */
public interface UpCompanyInService {

    /**
     * 保存企业（参见单位）进退场
     * @param upCompanyInDTO
     * @param upCompanyIn
     */
    Map<String, Object> saveCompanyInOut(UpCompanyInDTO upCompanyInDTO, UpCompanyIn upCompanyIn);
}
