package com.quanroon.atten.reports.report.definition;

import com.quanroon.atten.reports.report.entity.ReportConfig;
import lombok.Data;


/**
 * 上报业务定义 存放于容器中 用于实际上报业务
 * @author 彭清龙
 * @date 2020-04-27 20:47:37
 */
@Data
public class ReportDefinition extends BaseDefinition{

    private ReportCommonDefinition commonDefinition;// 所属功能
    private ReportParamDefinition paramDefinition;  // 请求实体
    private ReportResultDefinition resultDefinition;// 响应实体
    private ReportConfig config;                    // 上报配置
}
