package com.quanroon.atten.reports.entity.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.Date;

/**
 * @author quanroon.ysq
 * @version 1.0.0
 * @content
 * @date 2020/7/1 17:56
 */
@Data
public class UpDeviceInfoDTO implements Serializable {

    /**
     * 考勤机名称
     *
     * Table:     up_device_info
     * Column:    name
     * Nullable:  true
     */
    private String name;

    /**
     * 考勤机IP地址
     *
     * Table:     up_device_info
     * Column:    ip
     * Nullable:  true
     */
    private String ip;

    /**
     * 考勤机IP端口
     *
     * Table:     up_device_info
     * Column:    port
     * Nullable:  true
     */
    private String port;

    /**
     * 进出场类型
     *
     * Table:     up_device_info
     * Column:    type
     * Nullable:  true
     */
    @Length(min = 4, max = 4, message = "请参考进出场类型词典")
    private String type;

    /**
     * 设备序列号
     *
     * Table:     up_device_info
     * Column:    device_sn
     * Nullable:  true
     */
    private String deviceSn;

    /**
     * 安装位置
     *
     * Table:     up_device_info
     * Column:    address
     * Nullable:  true
     */
    private String address;
}
