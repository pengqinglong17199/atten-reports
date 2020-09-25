package com.quanroon.atten.reports.entity.dto;

import lombok.Data;

/**
 * 项目密钥dto
 * @author 彭清龙
 * @date 2020/6/30 17:54
 */
@Data
public class ProjAuthDTO {

    private Integer projId;
    private String authKey;
    private String version;

}
