package com.quanroon.atten.reports.service;

import com.quanroon.atten.reports.entity.UpGroupInfo;
import com.quanroon.atten.reports.entity.dto.UpGroupInfoDTO;

import java.util.Map;

/**
 * @author quanroon.ysq
 * @version 1.0.0
 * @content
 * @date 2020/7/1 13:38
 */
public interface UpGroupInfoService {

    /**
     * 保存班组信息
     * @param upGroupInfo
     * @param upGroupInfoDTO
     */
    Map<String, Object> saveGroupInfo(UpGroupInfo upGroupInfo, UpGroupInfoDTO upGroupInfoDTO);
}
