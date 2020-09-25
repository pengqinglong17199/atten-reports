package com.quanroon.atten.reports.service;

import com.quanroon.atten.reports.entity.UpDictReport;

/**
 * @author quanroon.ysq
 * @version 1.0.0
 * @content 字典映射服务类
 * @date 2020/7/24 15:06
 */
public interface UpDictReportService {

    /**
     * 通过上报城市code+平台词典值，获取对应城市映射词典集合
     * @param dictReport
     * @return
     */
    UpDictReport getDictReportByApiCode(UpDictReport dictReport);
}
