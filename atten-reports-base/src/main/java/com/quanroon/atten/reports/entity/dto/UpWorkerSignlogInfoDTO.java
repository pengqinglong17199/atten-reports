package com.quanroon.atten.reports.entity.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UpWorkerSignlogInfoDTO {

    /** 进场*/
    public static final Integer IOTYPE_IN = 1;
    /** 离场*/
    public static final Integer IOTYPE_LEAVE = 2;

    private Integer id;
    private Integer workerId;
    private Integer deviceId;
    private Date ioTime;
    private Integer ioMode;
    private Integer ioType;
    private Float similar;
    private Float lat;
    private Float lgt;
    private String photo;

}
