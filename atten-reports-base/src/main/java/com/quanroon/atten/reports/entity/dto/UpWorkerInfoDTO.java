package com.quanroon.atten.reports.entity.dto;

import com.quanroon.atten.reports.entity.UpWorkerExp;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author quanroon.ysq
 * @version 1.0.0
 * @content
 * @date 2020/7/1 15:58
 */
@Data
public class UpWorkerInfoDTO implements Serializable {

    /**
     * 工人姓名
     *
     * Table:     up_worker_info
     * Column:    name
     * Nullable:  true
     */
    private String name;

    /**
     * 年龄
     *
     * Table:     up_worker_info
     * Column:    age
     * Nullable:  true
     */
    private Integer age;

    /**
     * 性别
     *
     * Table:     up_worker_info
     * Column:    sex
     * Nullable:  true
     */
    private String sex;

    /**
     * 出生日期
     *
     * Table:     up_worker_info
     * Column:    birthday
     * Nullable:  true
     */
    private Date birthday;

    /**
     * 民族
     *
     * Table:     up_worker_info
     * Column:    nation
     * Nullable:  true
     */
    private String nation;

    /**
     * 现居住地址
     *
     * Table:     up_worker_info
     * Column:    current_address
     * Nullable:  true
     */
    private String currentAddress;

    /**
     * 政治面貌
     *
     * Table:     up_worker_info
     * Column:    political
     * Nullable:  true
     */
    private String political;

    /**
     * 户籍
     *
     * Table:     up_worker_info
     * Column:    household_register
     * Nullable:  true
     */
    private String householdRegister;

    /**
     * 手机号码
     *
     * Table:     up_worker_info
     * Column:    mobile
     * Nullable:  true
     */
    private String mobile;

    /**
     * 文化程度
     *
     * Table:     up_worker_info
     * Column:    education_degree
     * Nullable:  true
     */
    private String educationDegree;

    /**
     * 身份证地址
     *
     * Table:     up_worker_info
     * Column:    card_no_address
     * Nullable:  true
     */
    private String cardNoAddress;

    /**
     * 身份证有效开始日期
     *
     * Table:     up_worker_info
     * Column:    card_no_start_date
     * Nullable:  true
     */
    private Date cardNoStartDate;

    /**
     * 身份证有效结束日期
     *
     * Table:     up_worker_info
     * Column:    card_no_end_date
     * Nullable:  true
     */
    private Date cardNoEndDate;

    /**
     * 证件号码
     *
     * Table:     up_worker_info
     * Column:    card_no
     * Nullable:  true
     */
    private String cardNo;

    /**
     * 证件类型
     *
     * Table:     up_worker_info
     * Column:    card_no_type
     * Nullable:  true
     */
    private String cardNoType;

    /**
     * 发证机关
     *
     * Table:     up_worker_info
     * Column:    card_no_issuing
     * Nullable:  true
     */
    private String cardNoIssuing;

    /**
     * 健康状况
     *
     * Table:     up_worker_info
     * Column:    healthy
     * Nullable:  true
     */
    private String healthy;

    /**
     * 是否有重大病史
     *
     * Table:     up_worker_info
     * Column:    is_diseases
     * Nullable:  true
     */
    private String isDiseases;

    /**
     * 劳工资质集合
     */
    private List<UpWorkerExp> certificates;


    /**
     * 身份证人脸照片（base64编码，带标签头）
     */
    private String cardNoPersonPicture;
    /**
     * 劳工照片
     */
    private String workerPicture;
    /**
     * 身份证正面照片
     */
    private String cardNoHeadsPicture;
    /**
     * 身份证反面照片
     */
    private String cardNoTailsPicture;
}
