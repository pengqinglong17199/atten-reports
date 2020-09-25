package com.quanroon.atten.reports.entity.base;

import lombok.Builder;
import lombok.Data;

/**
 * @author quanroon.ysq
 * @version 1.0.0
 * @content 生产消息附加属性类
 * @date 2020/8/5 20:03
 */
@Data
@Builder
public class CodeEntity {

    /** 业务id*/
    private Integer tableId;
    /** 项目id*/
    private Integer projId;
    /** 表名*/
    private String tableName;
}
