package com.quanroon.atten.reports.service;

import com.quanroon.atten.reports.entity.dto.UpPayrollDetailInfoDTO;

import java.util.Map;

public interface UpPayrollDetailInfoService {

    Map<String,Object> uploadSalaryDetail(UpPayrollDetailInfoDTO dto);

}
