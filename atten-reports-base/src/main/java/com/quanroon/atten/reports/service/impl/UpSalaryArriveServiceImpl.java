package com.quanroon.atten.reports.service.impl;

import com.quanroon.atten.commons.enums.RepReturnCodeEnum;
import com.quanroon.atten.commons.exception.BusinessException;
import com.quanroon.atten.reports.common.ReportConstant;
import com.quanroon.atten.reports.common.ReportType;
import com.quanroon.atten.reports.dao.UpSalaryArriveMapper;
import com.quanroon.atten.reports.dao.UpSalaryInfoMapper;
import com.quanroon.atten.reports.entity.UpSalaryArrive;
import com.quanroon.atten.reports.entity.UpSalaryInfo;
import com.quanroon.atten.reports.entity.dto.UpSalaryArriveDTO;
import com.quanroon.atten.reports.entity.example.UpSalaryArriveExample;
import com.quanroon.atten.reports.entity.example.UpSalaryInfoExample;
import com.quanroon.atten.reports.service.UpRecordService;
import com.quanroon.atten.reports.service.UpSalaryArriveService;
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
public class UpSalaryArriveServiceImpl implements UpSalaryArriveService {

    @Autowired
    private UpSalaryArriveMapper upSalaryArriveMapperImpl;
    @Autowired
    private UpRecordService upRecordServiceImpl;
    @Autowired
    private UpSalaryInfoMapper upSalaryInfoMapper;

    @Transactional(readOnly = false)
    public Map<String,Object> uploadSalaryRemittance(UpSalaryArriveDTO dto){
        Map<String,Object> map = new HashMap<>();
        //判断是否存在工资专户
        UpSalaryInfoExample upSalaryInfoExample = new UpSalaryInfoExample();
        upSalaryInfoExample.createCriteria().andIdEqualTo(dto.getSalaryAccountId());
        List<UpSalaryInfo> upSalaryInfos = upSalaryInfoMapper.selectByExample(upSalaryInfoExample);
        if(Objects.isNull(upSalaryInfos)){
            throw new BusinessException(RepReturnCodeEnum.REPORT_RETURN_351001);
        }
        //执行更新或新增
        UpSalaryArrive upSalaryArrive = new UpSalaryArrive();
        BeanUtils.copyProperties(dto, upSalaryArrive);
        upSalaryArrive.setId(dto.getSalaryArriveId());//业务id

        ReportType reportType = ReportType.salary_arrive;//默认新增上报
        if(null != upSalaryArrive.getId()){
            UpSalaryArrive salaryArrive = upSalaryArriveMapperImpl.selectByPrimaryKey(upSalaryArrive.getId());
            if(Objects.isNull(salaryArrive)){
                throw new BusinessException(RepReturnCodeEnum.REPORT_RETURN_353001);
            }
            upSalaryArriveMapperImpl.updateByPrimaryKey(upSalaryArrive);
            reportType = ReportType.salary_update;
        }else{
            //这里判断一个专户只对一个到账
            UpSalaryArriveExample salaryArriveExample = new UpSalaryArriveExample();
            salaryArriveExample.createCriteria().andSalaryAccountIdEqualTo(upSalaryArrive.getSalaryAccountId());
            List<UpSalaryArrive> salaryArrive = upSalaryArriveMapperImpl.selectByExample(salaryArriveExample);
            if(!CollectionUtils.isEmpty(salaryArrive)){
                throw new BusinessException(RepReturnCodeEnum.REPORT_RETURN_353002);
            }
            upSalaryArriveMapperImpl.insert(upSalaryArrive);
        }
        String requestCode = upRecordServiceImpl.initRequestCode(upSalaryArrive.getCodeEntity(upSalaryInfos.get(0).getProjId()), reportType);
        map.put("requestCode",requestCode);
        return map;
    }

}
