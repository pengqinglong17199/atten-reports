package com.quanroon.atten.reports.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * Table: up_dict_info
 */
@Data
public class UpDictInfo implements Serializable {
    /**
     * Table:     up_dict_info
     * Column:    id
     * Nullable:  false
     */
    private Integer id;

    /**
     * 名称
     *
     * Table:     up_dict_info
     * Column:    name
     * Nullable:  true
     */
    private String name;

    /**
     * Table:     up_dict_info
     * Column:    dict_value
     * Nullable:  true
     */
    private String dictValue;

    /**
     * 类型
     *
     * Table:     up_dict_info
     * Column:    dict_type
     * Nullable:  true
     */
    private String dictType;

    /**
     * 创建日期
     *
     * Table:     up_dict_info
     * Column:    create_date
     * Nullable:  true
     */
    private Date createDate;

    /**
     * 更新日期
     *
     * Table:     up_dict_info
     * Column:    update_date
     * Nullable:  true
     */
    private Date updateDate;

    /**
     * 状态 0：可用 1：禁用
     *
     * Table:     up_dict_info
     * Column:    status
     * Nullable:  true
     */
    private String status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table up_dict_info
     *
     * @mbggenerated Wed Aug 05 11:01:25 CST 2020
     */
    private static final long serialVersionUID = 1L;
}