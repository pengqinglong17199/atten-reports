package com.quanroon.atten.reports.service.impl;

import com.quanroon.atten.commons.enums.RepReturnCodeEnum;
import com.quanroon.atten.commons.exception.BusinessException;
import com.quanroon.atten.commons.jwt.IJWTInfo;
import com.quanroon.atten.reports.common.ReportConstant;
import com.quanroon.atten.reports.common.ReportType;
import com.quanroon.atten.reports.dao.UpProjectInfoMapper;
import com.quanroon.atten.reports.dao.UpRecordMapper;
import com.quanroon.atten.reports.dao.UpSalaryInfoMapper;
import com.quanroon.atten.reports.entity.UpProjectInfo;
import com.quanroon.atten.reports.entity.UpSalaryInfo;
import com.quanroon.atten.reports.entity.dto.UpSalaryInfoDTO;
import com.quanroon.atten.reports.entity.example.UpProjectInfoExample;
import com.quanroon.atten.reports.entity.example.UpSalaryInfoExample;
import com.quanroon.atten.reports.service.UpRecordService;
import com.quanroon.atten.reports.service.UpSalaryInfoService;
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
public class UpSalaryInfoServiceImpl implements UpSalaryInfoService {

    @Autowired
    private UpSalaryInfoMapper upSalaryInfoMapper;
    @Autowired
    private UpRecordService upRecordServiceImpl;

    @Transactional(readOnly = false)
    public Map<String,Object> uploadSalaryAccount(UpSalaryInfoDTO dto,IJWTInfo ijwtInfo){
        Map<String,Object> map = new HashMap<>();
        UpSalaryInfo upSalaryInfo = new UpSalaryInfo();
        BeanUtils.copyProperties(dto, upSalaryInfo);
        upSalaryInfo.setId(dto.getSalaryAccountId());//业务id
        upSalaryInfo.setProjId(ijwtInfo.getId());//获取项目id
        ReportType reportType = ReportType.account_report;//默认新增上报
        if(null != upSalaryInfo.getId()){
            UpSalaryInfo salaryInfo = upSalaryInfoMapper.selectByPrimaryKey(upSalaryInfo.getId());
            if(Objects.isNull(salaryInfo)){
                throw new BusinessException(RepReturnCodeEnum.REPORT_RETURN_351001);
            }
            upSalaryInfoMapper.updateByPrimaryKey(upSalaryInfo);
            reportType = ReportType.account_update;
        }else{
            //一个项目只允许一个工资专户
            UpSalaryInfoExample upSalaryInfoExample = new UpSalaryInfoExample();
            upSalaryInfoExample.createCriteria().andProjIdEqualTo(upSalaryInfo.getProjId());
            List<UpSalaryInfo> upSalaryInfos = upSalaryInfoMapper.selectByExample(upSalaryInfoExample);
            if(!CollectionUtils.isEmpty(upSalaryInfos)){
                //变更为修改
                UpSalaryInfo info = upSalaryInfos.get(0);
                upSalaryInfo.setId(info.getId());
                upSalaryInfoMapper.updateByPrimaryKeySelective(upSalaryInfo);
                //throw new BusinessException(RepReturnCodeEnum.REPORT_RETURN_351002);
            }else{
                upSalaryInfoMapper.insert(upSalaryInfo);
            }
        }
        String requestCode = upRecordServiceImpl.initRequestCode(upSalaryInfo.getCodeEntity(), reportType);
        map.put("requestCode",requestCode);
        return map;
    }

}
