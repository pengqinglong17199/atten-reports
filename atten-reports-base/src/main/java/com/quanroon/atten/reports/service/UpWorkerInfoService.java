package com.quanroon.atten.reports.service;

import com.quanroon.atten.reports.entity.UpWorkerInfo;
import com.quanroon.atten.reports.entity.dto.UpWorkerInfoDTO;

import java.util.Map;

/**
 * @author quanroon.ysq
 * @version 1.0.0
 * @content
 * @date 2020/7/1 13:38
 */
public interface UpWorkerInfoService {

    /**
     * 保存劳工信息
     * @param upWorkerInfoDTO
     */
    Map<String, Object> saveWorkerInfo(UpWorkerInfoDTO upWorkerInfoDTO,UpWorkerInfo upWorkerInfo);


    /**
     * 查找劳工信息
     * @param workerId
     */
    UpWorkerInfo findWorkerInfoByWorkerId(Integer workerId);

}
