package com.quanroon.atten.reports.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 罗森林
 * @Auther:
 * @Date: 2020-09-02 16:29
 * @Description:
 */
@Data
public class UpBengbuSignlogDTO implements Serializable {
    /** 项目id */
    private Integer projId;
    /**所属单位编码(统一社会信息码) */
    private String corpCode;
    /**证件号码 */
    private String cardNo;
    /**姓名 */
    private String name;
    /** 打卡时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date time;
    /** 项目编码 */
    private String projNo;
    /** 班组编码 */
    private String groupNo;
    /** 进出场类型(01:入场;02:出场) */
    private String direction;

}
