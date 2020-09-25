package com.quanroon.atten.reports.service.impl;

import com.google.common.collect.Maps;
import com.quanroon.atten.commons.enums.RepReturnCodeEnum;
import com.quanroon.atten.commons.exception.BusinessException;
import com.quanroon.atten.commons.utils.StringUtils;
import com.quanroon.atten.reports.common.ReportConstant;
import com.quanroon.atten.reports.common.ReportType;
import com.quanroon.atten.reports.dao.UpParamsMapper;
import com.quanroon.atten.reports.entity.UpParams;
import com.quanroon.atten.reports.entity.UpProjectInfo;
import com.quanroon.atten.reports.entity.example.UpParamsExample;
import com.quanroon.atten.reports.service.UpParamsService;
import com.quanroon.atten.reports.service.UpProjectAuthService;
import com.quanroon.atten.reports.service.UpProjectInfoService;
import com.quanroon.atten.reports.service.UpRecordService;
import com.quanroon.atten.reports.utils.ReportUtils;
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
public class UpParamsServiceImpl implements UpParamsService {

    @Autowired
    private UpParamsMapper upParamsMapper;
    @Autowired
    private UpRecordService upRecordServiceImpl;
    @Autowired
    private UpProjectAuthService upProjectAuthServiceImpl;
    @Autowired
    private UpProjectInfoService upProjectInfoServiceImpl;

    /**
     * 保存/更新项目参数配置
     *
     * @param upParams
     * @return int
     */
    @Override
    @Transactional(readOnly = false)
    public Map<String, Object> saveProjConfig(UpParams upParams) {
        HashMap<String, Object> hashMap = Maps.newHashMap();
        //查询是否上报了项目
        UpProjectInfo upProjectInfo = upProjectInfoServiceImpl.selectByPrimaryKey(upParams.getProjId());
        if (Objects.isNull(upProjectInfo))
            throw new BusinessException(RepReturnCodeEnum.REPORT_RETURN_200002);
        //查询住建局配置是否存在
        UpParamsExample example = new UpParamsExample();
        example.createCriteria().andProjIdEqualTo(upParams.getProjId());
        List<UpParams> list = upParamsMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(list)) {
            //新增住建配置
            upParamsMapper.insert(upParams);
            //生成项目密钥,用于项目鉴权
            upProjectAuthServiceImpl.initAuth(upParams.getProjId());
        } else {
            upParams.setId(list.get(0).getId());
            upParamsMapper.updateByPrimaryKeySelective(upParams);
        }
        //配置完成后，进行对应项目上报
        String requestCode = upRecordServiceImpl.initRequestCode(upParams.getCodeEntity(UpProjectInfo.class), ReportType.proj_report);
        hashMap.put("requestCode", requestCode);
        return hashMap;
    }

    /**
     * 根据项目id获取上报城市的code
     * @param upParams
     * @return
     */
    @Override
    public String getReportCityCode(UpParams upParams) {
        if(Objects.nonNull(upParams.getProjId())){
            UpParamsExample upParamsExample = new UpParamsExample();
            upParamsExample.createCriteria().andProjIdEqualTo(upParams.getProjId());
            List<UpParams> params = upParamsMapper.selectByExample(upParamsExample);
            if(!CollectionUtils.isEmpty(params)){
                //return getCode(params.get(0));
                return ReportUtils.getCode(params.get(0));
            }
        }
        return null;
    }
}
