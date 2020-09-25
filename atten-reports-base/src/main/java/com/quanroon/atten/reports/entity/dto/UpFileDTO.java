package com.quanroon.atten.reports.entity.dto;

import lombok.Data;

/**
 * @Description: 附件上传DTO
 * @Author: ysx
 * @Date: 2020/6/30
 */

@Data
public class UpFileDTO {

    private Integer tableId;
    private String fileName;
    private String filePath;
    private String tableName;
    private String tableModule;

}
