package com.quanroon.atten.reports.service.impl;

import com.google.common.collect.Maps;
import com.quanroon.atten.commons.enums.RepReturnCodeEnum;
import com.quanroon.atten.commons.exception.BusinessException;
import com.quanroon.atten.reports.common.ReportConstant;
import com.quanroon.atten.reports.common.ReportType;
import com.quanroon.atten.reports.dao.UpCompanyInMapper;
import com.quanroon.atten.reports.dao.UpGroupInfoMapper;
import com.quanroon.atten.reports.entity.UpCompanyIn;
import com.quanroon.atten.reports.entity.UpGroupInfo;
import com.quanroon.atten.reports.entity.dto.UpGroupInfoDTO;
import com.quanroon.atten.reports.entity.example.UpCompanyInExample;
import com.quanroon.atten.reports.entity.example.UpGroupInfoExample;
import com.quanroon.atten.reports.service.UpFileService;
import com.quanroon.atten.reports.service.UpGroupInfoService;
import com.quanroon.atten.reports.service.UpRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author quanroon.ysq
 * @version 1.0.0
 * @content
 * @date 2020/7/1 13:59
 */
@Service
@Transactional(readOnly = true)
public class UpGroupInfoServiceImpl implements UpGroupInfoService {
    @Autowired
    private UpCompanyInMapper upCompanyInMapper;
    @Autowired
    private UpGroupInfoMapper upGroupInfoMapper;
    @Autowired
    private UpRecordService upRecordService;
    @Autowired
    private UpFileService upFileServiceImpl;
    /**
     * 保存或更新（离场）班组信息,
     * @param upGroupInfo
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public Map<String, Object> saveGroupInfo(UpGroupInfo upGroupInfo, UpGroupInfoDTO upGroupInfoDTO) {
        Map<String, Object> hashMap = Maps.newHashMap();
        ReportType reportType = ReportType.group_report;
        String requestCode = null;
        //判断是否是离场操作
        if(Objects.nonNull(upGroupInfo.getLeaveDate())
                && Objects.nonNull(upGroupInfo.getId())){
            //执行离场操作
            //是否有班组进场信息
            UpGroupInfo groupInfo = upGroupInfoMapper.selectByPrimaryKey(upGroupInfo.getId());
            if(Objects.isNull(groupInfo))
                throw new BusinessException(RepReturnCodeEnum.REPORT_RETURN_320001);

            //是否允许重复离场，根据住建局给出的上报结果reportCode来判断
            if("1".equals(groupInfo.getStatus()))
                throw new BusinessException(RepReturnCodeEnum.REPORT_RETURN_320003);

            upGroupInfo.setStatus("1");//离场状态
            upGroupInfo.setUpdateDate(new Date());
            upGroupInfoMapper.updateByPrimaryKeySelective(upGroupInfo);
            reportType = ReportType.group_leave;
        }else{
            //执行进场操作
            //根据企业companyId查询是否有企业进场信息
            UpCompanyInExample upCompanyInExample = new UpCompanyInExample();
            upCompanyInExample.createCriteria()
                    .andCompanyIdEqualTo(upGroupInfo.getCompanyId())
                    .andProjIdEqualTo(upGroupInfo.getProjId());
            List<UpCompanyIn> upCompanyIns = upCompanyInMapper.selectByExample(upCompanyInExample);
            if(CollectionUtils.isEmpty(upCompanyIns))
                throw new BusinessException(RepReturnCodeEnum.REPORT_RETURN_311002);

            if("1".equals(upCompanyIns.get(0).getStatus()))
                throw new BusinessException(RepReturnCodeEnum.REPORT_RETURN_312001);

            //判断是否存在班组信息 (使用班组名称来判断)
            UpGroupInfoExample upGroupInfoExample = new UpGroupInfoExample();
            upGroupInfoExample.createCriteria()
                    .andProjIdEqualTo(upGroupInfo.getProjId())
                    .andCompanyIdEqualTo(upGroupInfo.getCompanyId())
                    .andGroupNameEqualTo(upGroupInfo.getGroupName());
            List<UpGroupInfo> upGroupInfos = upGroupInfoMapper.selectByExample(upGroupInfoExample);

            upGroupInfo.setStatus("0");//进场状态
            if(CollectionUtils.isEmpty(upGroupInfos)){
                upGroupInfo.setCreateDate(new Date());
                upGroupInfoMapper.insertSelective(upGroupInfo);
            }else{

                //是否允许重复离场，根据住建局给出的上报结果reportCode来判断
                /*if("0".equals(upGroupInfos.get(0).getStatus()))
                    throw new BusinessException(RepReturnCodeEnum.REPORT_RETURN_320002);*/

                //修改班组
                upGroupInfo.setId(upGroupInfos.get(0).getId());
                upGroupInfo.setUpdateDate(new Date());
                upGroupInfoMapper.updateByPrimaryKeySelective(upGroupInfo);
                reportType = ReportType.group_update;
            }
            //上传承包合同附件
            //String toFile = Base64Utils.base64ToFile(upGroupInfoDTO.getContractFile(), upGroupInfoDTO.getContractTitle());
            try {
                if(Objects.nonNull(upGroupInfoDTO.getContractFile())){
                    upFileServiceImpl.handleModelFile(upGroupInfoDTO.getContractFile(), upGroupInfo.getId(),
                            ReportConstant.UP_GROUP_INFO, ReportConstant.MODULE_GROUP_CONTRACT);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new BusinessException(RepReturnCodeEnum.REPORT_RETURN_362001);
            }
        }
        requestCode = upRecordService.initRequestCode(upGroupInfo.getCodeEntity(),reportType);
        hashMap.put("requestCode",requestCode);
        return hashMap;

    }
}
