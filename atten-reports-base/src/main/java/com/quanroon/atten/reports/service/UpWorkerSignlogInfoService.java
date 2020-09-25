package com.quanroon.atten.reports.service;

import com.quanroon.atten.reports.entity.UpWorkerSignlogInfo;
import com.quanroon.atten.reports.entity.dto.UpWorkerSignlogInfoDTO;

public interface UpWorkerSignlogInfoService {

    String save(UpWorkerSignlogInfo upWorkerSignlogInfo, UpWorkerSignlogInfoDTO dto);

}
