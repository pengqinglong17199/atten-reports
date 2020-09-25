package com.quanroon.atten.reports.api.server.entity.request;

import com.quanroon.atten.commons.utils.DateUtils;
import com.quanroon.atten.reports.api.server.annotation.Length;
import com.quanroon.atten.reports.entity.dto.UpWorkerSignlogInfoDTO;
import lombok.Data;


import java.util.Date;

import static com.quanroon.atten.reports.api.server.common.SocketCommon.SIGNLOG;

/**
* @Description: 打卡记录控制器
* @Author: ysx
* @Date: 2020/7/1
*/
@Data
public class SignlogRequest extends PacketRequest{
    //劳工id
    @Length(value = 4,sort = 2)
    private Integer workerId;
    //设备id
    @Length(value = 4,sort = 3)
    private Integer deviceId;
    //打卡时间
    @Length(value = 14,sort = 4)
    private Date ioTime;
    //打卡模式
    @Length(value = 4,sort = 5)
    private Integer ioMode;
    //打卡类型
    @Length(value = 4,sort = 6)
    private Integer ioType;
    //相似度
    @Length(value = 4,sort = 7)
    private Float similar;
    //经度
    @Length(value = 4,sort = 8)
    private Float lat;
    //纬度
    @Length(value = 4,sort = 9)
    private Float lgt;
    //考勤图片
    private String photo;
    // 校验和
    @Length(value = 1,sort = 10)
    private Byte xor;

    @Override
    public short getCommand() {
        return SIGNLOG;
    }

    public UpWorkerSignlogInfoDTO getSignlog() throws Exception{
        UpWorkerSignlogInfoDTO dto = new UpWorkerSignlogInfoDTO();
        dto.setWorkerId(this.workerId);
        dto.setDeviceId(this.deviceId);
        Date date = DateUtils.parseDate(DateUtils.formatDate(this.ioTime,"yyyy-MM-dd HH:mm:ss"));
        dto.setIoTime(date);
        dto.setIoMode(this.ioMode);
        dto.setIoType(this.ioType == 1 ? UpWorkerSignlogInfoDTO.IOTYPE_IN : UpWorkerSignlogInfoDTO.IOTYPE_LEAVE);
        dto.setSimilar(this.similar);
        dto.setLat(this.lat);
        dto.setLgt(this.lgt);
        if(this.photo!=null){
            dto.setPhoto(this.photo.toString());
        }
        return dto;
    }

}
