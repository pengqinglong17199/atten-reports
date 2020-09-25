package com.quanroon.atten.reports.service;

import com.quanroon.atten.commons.jwt.IJWTInfo;
import com.quanroon.atten.reports.entity.dto.UpSalaryInfoDTO;

import java.util.Map;

public interface UpSalaryInfoService {

    Map<String,Object> uploadSalaryAccount(UpSalaryInfoDTO dto, IJWTInfo ijwtInfo);

}
