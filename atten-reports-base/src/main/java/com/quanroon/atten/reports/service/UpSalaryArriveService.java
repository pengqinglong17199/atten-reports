package com.quanroon.atten.reports.service;

import com.quanroon.atten.reports.entity.dto.UpSalaryArriveDTO;

import java.util.Map;

public interface UpSalaryArriveService {

    Map<String,Object> uploadSalaryRemittance(UpSalaryArriveDTO dto);

}
