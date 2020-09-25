package com.quanroon.atten.reports.entity;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

/**
 * Table: up_file
 */
@Data
@Builder
public class UpFile implements Serializable {
    /**
     * 主码
     *
     * Table:     up_file
     * Column:    id
     * Nullable:  false
     */
    private Integer id;

    /**
     * 文件存放路径
     *
     * Table:     up_file
     * Column:    file_path
     * Nullable:  false
     */
    private String filePath;

    /**
     * 文件名称
     *
     * Table:     up_file
     * Column:    file_name
     * Nullable:  true
     */
    private String fileName;

    /**
     * 文件类型
     *
     * Table:     up_file
     * Column:    file_type
     * Nullable:  true
     */
    private String fileType;

    /**
     * 文件所属表名
     *
     * Table:     up_file
     * Column:    table_name
     * Nullable:  true
     */
    private String tableName;

    /**
     * 文件所属表id
     *
     * Table:     up_file
     * Column:    table_id
     * Nullable:  true
     */
    private Integer tableId;

    /**
     * 文件所属表模块
     *
     * Table:     up_file
     * Column:    table_module
     * Nullable:  true
     */
    private String tableModule;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table up_file
     *
     * @mbggenerated Mon Jun 29 15:03:37 CST 2020
     */
    private static final long serialVersionUID = 1L;
}