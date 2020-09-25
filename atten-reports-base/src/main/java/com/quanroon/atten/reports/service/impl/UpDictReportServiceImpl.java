package com.quanroon.atten.reports.service.impl;

import com.google.common.collect.Maps;
import com.quanroon.atten.commons.utils.RedisUtils;
import com.quanroon.atten.reports.dao.UpDictReportMapper;
import com.quanroon.atten.reports.entity.UpDictReport;
import com.quanroon.atten.reports.service.UpDictReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author quanroon.ysq
 * @version 1.0.0
 * @content
 * @date 2020/7/24 15:15
 */
@Slf4j
@Service
@Transactional(readOnly = true)
public class UpDictReportServiceImpl implements UpDictReportService {

    private String DICT_PREFIX = "reportApi:dictReport:";

    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private UpDictReportMapper dictReportMapper;
    /**
     * 通过上报城市code+平台词典值，获取对应城市映射词典（单个）
     * @param dictReport
     * @return
     */
    @Override
    public UpDictReport getDictReportByApiCode(UpDictReport dictReport) {
        if(dictReport.validParamsIsEmpty()){
            log.info("请求映射词典集合参数有误，请检查");
            return dictReport;
        }
        StringBuilder builder = new StringBuilder();

        //缓存字段的key
        builder.append(DICT_PREFIX)
                .append(dictReport.getReportCityCode());

        //先取缓存数据
        Map<String, List<UpDictReport>> dictMaps = (Map<String, List<UpDictReport>>)redisUtils.hget(
                builder.toString(),dictReport.getReportType());

        if(CollectionUtils.isEmpty(dictMaps)){
            //如果没有，则查询所有映射词典集合，分组进行存入缓存
            dictMaps = setDictReportToRedis(builder.toString(), dictReport);
        }

        List<UpDictReport> valueList = dictMaps.get(dictReport.getDictType());
        if(!CollectionUtils.isEmpty(valueList)){
            Map<String, String> codeToCode = valueList.stream().collect(
                    Collectors.toMap(UpDictReport::getApiCode, UpDictReport::getCode));
            //获取映射的code
            dictReport.setCode(codeToCode.get(dictReport.getApiCode()));
        }
        return dictReport;
    }

    public Map<String, Map<String, String>> getCityDictMap(){

        HashMap<String, Map<String, String>> hashMap = Maps.newHashMap();


        return hashMap;
    }

    /**
     * 将城市映射词典放入缓存中
     * @param dictKey
     * @param dictReport
     */
    private Map<String, List<UpDictReport>> setDictReportToRedis(String dictKey, UpDictReport dictReport){
        //当前城市下所有必填字段
        List<UpDictReport> dictList = dictReportMapper.selectByDictReportList(dictReport);
        if(CollectionUtils.isEmpty(dictList)){
            log.info("未查询到城市映射词典数据,请检查");
            return new HashMap<>();
        }
        Map<String, List<UpDictReport>> groupMaps = dictList.stream().collect(Collectors.groupingBy(UpDictReport::getDictType));
        //放入缓存中
        redisUtils.hset(dictKey, dictReport.getReportType(), groupMaps, UpDictReport.dictExpire);
        return groupMaps;
    }
}
