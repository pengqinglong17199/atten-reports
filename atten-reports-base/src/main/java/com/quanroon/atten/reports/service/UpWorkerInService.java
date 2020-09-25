package com.quanroon.atten.reports.service;

import com.quanroon.atten.reports.entity.UpWorkerIn;
import com.quanroon.atten.reports.entity.dto.UpWorkerInDTO;

import java.util.Map;

/**
 * @author quanroon.ysq
 * @version 1.0.0
 * @content
 * @date 2020/7/1 13:40
 */
public interface UpWorkerInService {

    /**
     * 保存劳工进退场
     * @param workerInDTO
     * @param upWorkerInIn
     */
    Map<String, Object> saveWorkerInOut(UpWorkerInDTO workerInDTO, UpWorkerIn upWorkerInIn);
}
