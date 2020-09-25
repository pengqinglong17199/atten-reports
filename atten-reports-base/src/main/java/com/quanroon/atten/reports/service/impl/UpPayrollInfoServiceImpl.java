package com.quanroon.atten.reports.service.impl;

import com.quanroon.atten.commons.enums.RepReturnCodeEnum;
import com.quanroon.atten.commons.exception.BusinessException;
import com.quanroon.atten.reports.common.ReportConstant;
import com.quanroon.atten.reports.common.ReportType;
import com.quanroon.atten.reports.dao.UpGroupInfoMapper;
import com.quanroon.atten.reports.dao.UpPayrollInfoMapper;
import com.quanroon.atten.reports.entity.UpGroupInfo;
import com.quanroon.atten.reports.entity.UpPayrollInfo;
import com.quanroon.atten.reports.entity.dto.UpPayrollInfoDTO;
import com.quanroon.atten.reports.entity.example.UpGroupInfoExample;
import com.quanroon.atten.reports.entity.example.UpPayrollInfoExample;
import com.quanroon.atten.reports.service.UpPayrollInfoService;
import com.quanroon.atten.reports.service.UpRecordService;
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
public class UpPayrollInfoServiceImpl implements UpPayrollInfoService {


    @Autowired
    private UpPayrollInfoMapper upPayrollInfoMapper;
    @Autowired
    private UpRecordService upRecordServiceImpl;
    @Autowired
    private UpGroupInfoMapper upGroupInfoMapper;

    /**
     * 上报工资单
     * @param upPayrollInfo
     * @return
     */
    @Transactional(readOnly = false)
    public Map<String,Object> uploadPayrollInfo(UpPayrollInfo upPayrollInfo){
        Map<String,Object> map = new HashMap<>();
        //查询班组是否存在
        UpGroupInfo upGroupInfo = upGroupInfoMapper.selectByPrimaryKey(upPayrollInfo.getGroupId());
        if(Objects.isNull(upGroupInfo)){
            throw new BusinessException(RepReturnCodeEnum.REPORT_RETURN_320001);
        }
        upPayrollInfo.setCompanyId(upGroupInfo.getCompanyId());//参建单位id

        ReportType reportType = ReportType.payroll_report;//默认新增上报

        if(null != upPayrollInfo.getId()){
            UpPayrollInfo payrollInfo = upPayrollInfoMapper.selectByPrimaryKey(upPayrollInfo.getId());
            if(Objects.isNull(payrollInfo)){
                throw new BusinessException(RepReturnCodeEnum.REPORT_RETURN_352001);
            }
            upPayrollInfoMapper.updateByPrimaryKey(upPayrollInfo);
            reportType = ReportType.payroll_update;
        }else{
            //查询该项目下，班组下，同一月份工资是否有上报
            UpPayrollInfoExample example = new UpPayrollInfoExample();
            example.createCriteria().andProjIdEqualTo(upPayrollInfo.getProjId())
                    .andCompanyIdEqualTo(upGroupInfo.getCompanyId())
                    .andGroupIdEqualTo(upPayrollInfo.getGroupId())
                    .andGrantSalaryDateEqualTo(upPayrollInfo.getGrantSalaryDate());

            List<UpPayrollInfo> upPayrollInfos = upPayrollInfoMapper.selectByExample(example);
            if(!CollectionUtils.isEmpty(upPayrollInfos)){
               // throw new BusinessException(RepReturnCodeEnum.REPORT_RETURN_352002);
                upPayrollInfo = upPayrollInfos.get(0);
                upPayrollInfoMapper.updateByPrimaryKeySelective(upPayrollInfo);
            }else{
                upPayrollInfoMapper.insert(upPayrollInfo);
            }
        }
        String requestCode = upRecordServiceImpl.initRequestCode(upPayrollInfo.getCodeEntity(), reportType);
        map.put("requestCode",requestCode);
        return map;
    }

}
