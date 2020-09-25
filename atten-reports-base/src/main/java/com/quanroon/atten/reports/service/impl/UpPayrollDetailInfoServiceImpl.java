package com.quanroon.atten.reports.service.impl;

import com.google.common.collect.Maps;
import com.quanroon.atten.commons.enums.RepReturnCodeEnum;
import com.quanroon.atten.commons.exception.BusinessException;
import com.quanroon.atten.reports.common.ReportConstant;
import com.quanroon.atten.reports.common.ReportType;
import com.quanroon.atten.reports.dao.UpFileMapper;
import com.quanroon.atten.reports.dao.UpPayrollDetailInfoMapper;
import com.quanroon.atten.reports.dao.UpPayrollInfoMapper;
import com.quanroon.atten.reports.entity.UpFile;
import com.quanroon.atten.reports.entity.UpPayrollDetailInfo;
import com.quanroon.atten.reports.entity.UpPayrollInfo;
import com.quanroon.atten.reports.entity.UpWorkerInfo;
import com.quanroon.atten.reports.entity.dto.UpPayrollDetailInfoDTO;
import com.quanroon.atten.reports.entity.example.UpFileExample;
import com.quanroon.atten.reports.entity.example.UpPayrollDetailInfoExample;
import com.quanroon.atten.reports.entity.example.UpPayrollInfoExample;
import com.quanroon.atten.reports.service.UpFileService;
import com.quanroon.atten.reports.service.UpPayrollDetailInfoService;
import com.quanroon.atten.reports.service.UpRecordService;
import com.quanroon.atten.reports.service.UpWorkerInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
public class UpPayrollDetailInfoServiceImpl implements UpPayrollDetailInfoService {


    @Autowired
    private UpPayrollDetailInfoMapper upPayrollDetailInfoMapper;
    @Autowired
    private UpRecordService upRecordServiceImpl;
    @Autowired
    private UpFileService upFileServiceImpl;
    @Autowired
    private UpPayrollInfoMapper upPayrollInfoMapper;
    @Autowired
    private UpWorkerInfoService upWorkerInfoServiceImpl;

    @Transactional(readOnly = false)
    public Map<String,Object> uploadSalaryDetail(UpPayrollDetailInfoDTO dto){

        //判断劳工存不存在
        UpWorkerInfo upWorkerInfo = upWorkerInfoServiceImpl.findWorkerInfoByWorkerId(dto.getWorkerId());
        if(Objects.isNull(upWorkerInfo)){
            throw new BusinessException(RepReturnCodeEnum.REPORT_RETURN_330001);
        }

        //判断工资单是否上传
        UpPayrollInfoExample upPayrollInfoExample = new UpPayrollInfoExample();
        upPayrollInfoExample.createCriteria().andIdEqualTo(dto.getSalaryId());
        List<UpPayrollInfo> upPayrollInfos = upPayrollInfoMapper.selectByExample(upPayrollInfoExample);
        if(CollectionUtils.isEmpty(upPayrollInfos)){
            throw new BusinessException(RepReturnCodeEnum.REPORT_RETURN_352001);
        }
        Map<String,Object> map = new HashMap<>();
        //用于存储
        UpPayrollDetailInfo upPayrollDetailInfo = new UpPayrollDetailInfo();
        BeanUtils.copyProperties(dto, upPayrollDetailInfo);
        upPayrollDetailInfo.setId(dto.getPayrollDetailId());//工资单明细id

        ReportType reportType = ReportType.payroll_detail;
        if(null != upPayrollDetailInfo.getId()){
            //更新数据
            UpPayrollDetailInfo payrollDetailInfo = upPayrollDetailInfoMapper.selectByPrimaryKey(upPayrollDetailInfo.getId());
            if(Objects.isNull(payrollDetailInfo)){
                throw new BusinessException(RepReturnCodeEnum.REPORT_RETURN_354001);
            }
            upPayrollDetailInfoMapper.updateByPrimaryKey(upPayrollDetailInfo);
            reportType = ReportType.payroll_detail_update;
        }else{
            UpPayrollDetailInfoExample example = new UpPayrollDetailInfoExample();
            example.createCriteria().andSalaryIdEqualTo(upPayrollDetailInfo.getSalaryId());
            List<UpPayrollDetailInfo> payrollDetailInfos = upPayrollDetailInfoMapper.selectByExample(example);
            if(payrollDetailInfos.size() > 0){
                upPayrollDetailInfo = payrollDetailInfos.get(0);
                upPayrollDetailInfoMapper.updateByPrimaryKeySelective(upPayrollDetailInfo);
            }else{
                //插入数据
                upPayrollDetailInfoMapper.insert(upPayrollDetailInfo);
            }
        }
        //进行附件上传
        try {
            //上传工资单附件和工资单清册凭据
            if(Objects.nonNull(dto.getPayRollFilePath())){
                upFileServiceImpl.handleModelFile(dto.getPayRollFilePath(), upPayrollDetailInfo.getId(),
                        ReportConstant.UP_PAYROLL_DETAIL_INFO, ReportConstant.MODULE_PAYROLL_File);
            }
            if(Objects.nonNull(dto.getPayRollCertificatePath())){
                upFileServiceImpl.handleModelFile(dto.getPayRollCertificatePath(), upPayrollDetailInfo.getId(),
                        ReportConstant.UP_PAYROLL_DETAIL_INFO, ReportConstant.MODULE_PAYROLL_CERTIFICATE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(RepReturnCodeEnum.REPORT_RETURN_362001);
        }
        String requestCode = upRecordServiceImpl.initRequestCode(upPayrollDetailInfo.getCodeEntity(upPayrollInfos.get(0).getProjId()), reportType);
        map.put("requestCode",requestCode);

        return map;
    }

}
