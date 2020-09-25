package com.quanroon.atten.reports.service.impl;

import com.google.common.collect.Maps;
import com.quanroon.atten.commons.enums.RepReturnCodeEnum;
import com.quanroon.atten.commons.exception.BusinessException;
import com.quanroon.atten.reports.common.ReportConstant;
import com.quanroon.atten.reports.common.ReportType;
import com.quanroon.atten.reports.dao.UpDeviceInfoMapper;
import com.quanroon.atten.reports.entity.UpDeviceInfo;
import com.quanroon.atten.reports.entity.example.UpDeviceInfoExample;
import com.quanroon.atten.reports.service.UpDeviceInfoService;
import com.quanroon.atten.reports.service.UpRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author quanroon.ysq
 * @version 1.0.0
 * @content
 * @date 2020/7/1 17:58
 */
@Service
@Transactional(readOnly = true)
public class UpDeviceInfoServiceImpl implements UpDeviceInfoService {

    @Autowired
    private UpDeviceInfoMapper upDeviceInfoMapper;

    @Autowired
    private UpRecordService upRecordServiceImpl;
    /**
     * 保存或更新信息
     * @param upDeviceInfo
     */
    @Override
    @Transactional(readOnly = false)
    public Map<String, Object> saveDeivceInfo(UpDeviceInfo upDeviceInfo) {

        HashMap<String, Object> hashMap = Maps.newHashMap();
        //判断是否存在考勤机信息
        UpDeviceInfoExample upDeviceInfoExample = new UpDeviceInfoExample();
        upDeviceInfoExample.createCriteria().andProjIdEqualTo(upDeviceInfo.getProjId())
                .andDeviceSnEqualTo(upDeviceInfo.getDeviceSn());
        List<UpDeviceInfo> upDeviceInfos = upDeviceInfoMapper.selectByExample(upDeviceInfoExample);
        if(CollectionUtils.isEmpty(upDeviceInfos)){
            upDeviceInfo.setCreateDate(new Date());
            upDeviceInfo.setStatus("0");
            upDeviceInfoMapper.insertSelective(upDeviceInfo);
        }else{
            //判断没有解绑的考勤机，不能修改
            if("0".equals(upDeviceInfos.get(0).getStatus()))
                throw new BusinessException(RepReturnCodeEnum.REPORT_RETURN_340002);

            upDeviceInfoExample.clear();
            upDeviceInfoExample.createCriteria()
                    .andIdEqualTo(upDeviceInfos.get(0).getId());

            upDeviceInfo.setId(upDeviceInfos.get(0).getId());
            upDeviceInfo.setUpdateDate(new Date());
            upDeviceInfoMapper.updateByExampleSelective(upDeviceInfo,upDeviceInfoExample);
        }
        String requestCode = upRecordServiceImpl.initRequestCode(upDeviceInfo.getCodeEntity(), ReportType.device_report);
        hashMap.put("requestCode",requestCode);
        return hashMap;
    }

    /**
     * 解绑考勤机信息
     * @param upDeviceInfo
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public Map<String, Object> removeDeviceInfo(UpDeviceInfo upDeviceInfo) {
        HashMap<String, Object> hashMap = Maps.newHashMap();
        //判断是否存在考勤机信息
        UpDeviceInfo upDeviceInfos = upDeviceInfoMapper.selectByPrimaryKey(upDeviceInfo.getId());
        if(Objects.isNull(upDeviceInfos))
            throw new BusinessException(RepReturnCodeEnum.REPORT_RETURN_340001);
        if("1".equals(upDeviceInfos.getStatus()))
            throw new BusinessException(RepReturnCodeEnum.REPORT_RETURN_342001);

        UpDeviceInfoExample upDeviceInfoExample = new UpDeviceInfoExample();
        upDeviceInfoExample.createCriteria()
                .andIdEqualTo(upDeviceInfo.getId());
        upDeviceInfo.setStatus("1");//解绑
        upDeviceInfo.setUpdateDate(new Date());
        upDeviceInfoMapper.updateByExampleSelective(upDeviceInfo,upDeviceInfoExample);
        String requestCode = upRecordServiceImpl.initRequestCode(upDeviceInfo.getCodeEntity(), ReportType.device_unbind);
        hashMap.put("requestCode",requestCode);
        return hashMap;
    }


    /**
    * @Description: 查询考勤机
    * @Author: ysx
    * @Date: 2020/7/10
    */
    public UpDeviceInfo findDeviceInfoById(Integer deviceId){
        return upDeviceInfoMapper.selectByPrimaryKey(deviceId);
    }


}
