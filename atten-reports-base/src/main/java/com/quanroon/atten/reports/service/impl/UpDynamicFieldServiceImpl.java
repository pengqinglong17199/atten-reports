package com.quanroon.atten.reports.service.impl;

import com.google.common.collect.Lists;
import com.quanroon.atten.commons.enums.RepReturnCodeEnum;
import com.quanroon.atten.commons.exception.BusinessException;
import com.quanroon.atten.commons.jwt.IJWTInfo;
import com.quanroon.atten.commons.utils.RedisUtils;
import com.quanroon.atten.commons.utils.StringUtils;
import com.quanroon.atten.reports.dao.UpDynamicFieldMapper;
import com.quanroon.atten.reports.entity.UpDynamicField;
import com.quanroon.atten.reports.entity.UpParams;
import com.quanroon.atten.reports.entity.UpPlatformAuth;
import com.quanroon.atten.reports.entity.UpProjectInfo;
import com.quanroon.atten.reports.entity.dto.CityParamsDTO;
import com.quanroon.atten.reports.entity.example.UpDynamicFieldExample;
import com.quanroon.atten.reports.entity.vo.CityParamVO;
import com.quanroon.atten.reports.service.UpDynamicFieldService;
import com.quanroon.atten.reports.service.UpParamsService;
import com.quanroon.atten.reports.service.UpProjectInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class UpDynamicFieldServiceImpl implements UpDynamicFieldService {

    @Autowired private UpDynamicFieldMapper upDynamicFieldMapper;
    @Autowired private RedisUtils redisUtils;
    @Autowired private UpParamsService upParamsServiceImpl;
    @Autowired private UpProjectInfoService upProjectInfoImpl;

    @Override
    public List<UpDynamicField> selectByExample(UpDynamicFieldExample example) {
        return upDynamicFieldMapper.selectByExample(example);
    }

    /**
     * 获取上报方法的必填字段（平台级别）
     * @param cityParamsDto
     * @param ijwtInfo
     * @return
     */
    @Override
    public List<CityParamVO> getDynamicFieldList(CityParamsDTO cityParamsDto, IJWTInfo ijwtInfo) {
        //获取上报城市的code
        Integer platformId = ijwtInfo.getId();//默认平台Id
        //如果没有，则获取已上报的住建配置
        if(StringUtils.isEmpty(cityParamsDto.getCityCode())){
            UpParams upParams = new UpParams();
            upParams.setProjId(ijwtInfo.getId());//此时为项目Id
            String reportCityCode = upParamsServiceImpl.getReportCityCode(upParams);
            if(StringUtils.isEmpty(reportCityCode))
                throw new BusinessException(RepReturnCodeEnum.REPORT_RETURN_372001);
            //获取项目信息
            UpProjectInfo upProjectInfo = upProjectInfoImpl.selectByPrimaryKey(ijwtInfo.getId());
            if(Objects.isNull(upProjectInfo))
                throw new BusinessException(RepReturnCodeEnum.REPORT_RETURN_200002);

            platformId = upProjectInfo.getPlatformId();
            cityParamsDto.setCityCode(reportCityCode);//查询出的上报城市code
        }
        //缓存城市的必填字段数据
        String fieldKey = UpPlatformAuth.CACHE_PREFIX + platformId + UpPlatformAuth.FIELD_PREFIX ;//缓存字段的key
        Map<String, List<UpDynamicField>> fieldMaps = (Map<String, List<UpDynamicField>>)redisUtils.hget(fieldKey,cityParamsDto.getCityCode());
        if(CollectionUtils.isEmpty(fieldMaps)){
            fieldMaps = setDynamicFieldsToRedis(fieldKey, cityParamsDto.getCityCode());
        }
        List<UpDynamicField> fieldList = fieldMaps.get(cityParamsDto.getMethodName());
        List<CityParamVO> result = Lists.newArrayList();
        if(!CollectionUtils.isEmpty(fieldList)){
            for (UpDynamicField field : fieldList) {
                result.add(new CityParamVO(field));
            }
        }
        return result;
    }

    /**
     * 将必填字段放入缓存中
     * @param fieldKey
     * @param reportCityCode
     */
    private Map<String, List<UpDynamicField>> setDynamicFieldsToRedis(String fieldKey, String reportCityCode){
        UpDynamicFieldExample exampleFields = new UpDynamicFieldExample();
        exampleFields.createCriteria()
                .andCityCodeEqualTo(reportCityCode);
        //当前城市下所有必填字段
        List<UpDynamicField> fieldList = selectByExample(exampleFields);
        if(CollectionUtils.isEmpty(fieldList))
            throw new BusinessException(RepReturnCodeEnum.REPORT_RETURN_371001);//上报城市需要有必填字段，
        Map<String, List<UpDynamicField>> groupMaps = fieldList.stream().collect(Collectors.groupingBy(UpDynamicField::getReportType));
        //放入缓存中
        redisUtils.hset(fieldKey, reportCityCode, groupMaps, (UpDynamicField.fieldsExpire - 100));
        return groupMaps;
    }
}
