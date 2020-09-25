package com.quanroon.atten.reports.api.web;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Joiner;
import com.quanroon.atten.commons.base.AjaxResult;
import com.quanroon.atten.commons.base.BaseApiController;
import com.quanroon.atten.commons.enums.RepReturnCodeEnum;
import com.quanroon.atten.commons.exception.BusinessException;
import com.quanroon.atten.commons.jwt.IJWTInfo;
import com.quanroon.atten.commons.utils.StringUtils;
import com.quanroon.atten.reports.common.ReportConstant;
import com.quanroon.atten.reports.entity.UpParams;
import com.quanroon.atten.reports.entity.UpProjectInfo;
import com.quanroon.atten.reports.entity.dto.CityParamsDTO;
import com.quanroon.atten.reports.entity.dto.UpParamDTO;
import com.quanroon.atten.reports.entity.dto.UpProjectInfoDTO;
import com.quanroon.atten.reports.entity.vo.CityParamVO;
import com.quanroon.atten.reports.service.UpDynamicFieldService;
import com.quanroon.atten.reports.service.UpParamsService;
import com.quanroon.atten.reports.service.UpProjectInfoService;
import com.quanroon.atten.reports.utils.ReportUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author quanroon.ysq
 * @version 1.0.0
 * @content 上报项目内容相关接口
 * @date 2020/6/29 17:04
 */
@RestController
@RequestMapping(value = "${server.adminPath}/proj")
public class ProjInfoController extends BaseApiController {

    @Autowired private UpProjectInfoService upProjectInfoServiceImpl;
    @Autowired private UpParamsService upParamsServiceImpl;
    @Autowired private UpDynamicFieldService upDynamicFieldServiceImpl;

    /**
     * 上报项目信息
     * @param projectInfoDTO
     * @param request
     * @return
     */
    @RequestMapping("upload")
    public AjaxResult uploadProjectInfo(@RequestBody @Valid UpProjectInfoDTO projectInfoDTO, HttpServletRequest request){
        IJWTInfo ijwtInfo = (IJWTInfo) request.getAttribute(COMPANY_INFO);//获取平台id
        //根据城市校验必填
        CityParamsDTO paramsDTO = new CityParamsDTO();
        paramsDTO.setMethodName(ReportConstant.TYPE_PROJ_REPORT);//上报类型
        paramsDTO.setCityCode(projectInfoDTO.getReportCityCode());//上报城市code
        List<CityParamVO> dynamicFieldList = upDynamicFieldServiceImpl.getDynamicFieldList(paramsDTO, ijwtInfo);
        List<String> fields = dynamicFieldList.stream().map(field->field.getParamsName()).collect(Collectors.toList());
        super.isReportParamEmpty(projectInfoDTO, fields);
        //业务
        UpProjectInfo upProjectInfo = new UpProjectInfo();
        upProjectInfo.setPlatformId(ijwtInfo.getId());//平台id
        upProjectInfo.setId(projectInfoDTO.getProjId());//项目id（修改时使用）
        upProjectInfo.copyDateFormat(projectInfoDTO);//时间格式转换
        Map<String, Object> response = upProjectInfoServiceImpl.saveUpProjectInfo(projectInfoDTO, upProjectInfo);
        return super.success(response);
    }

    /**
     * 上报项目住建局参数配置
     * @param upParamDTO, request
     * @return com.quanroon.atten.commons.base.AjaxResult
     * @author 彭清龙
     * @date 2020/7/1 10:45
     */
    @RequestMapping("config")
    public AjaxResult configInfo(@RequestBody @Valid UpParamDTO upParamDTO, HttpServletRequest request){
        IJWTInfo ijwtInfo = (IJWTInfo) request.getAttribute(COMPANY_INFO);//获取平台id
        UpParams upParams = new UpParams();
        BeanUtils.copyProperties(upParamDTO, upParams);
        // 根据城市校验必填
        CityParamsDTO paramsDTO = new CityParamsDTO();
        paramsDTO.setMethodName(ReportConstant.TYPE_PROJ_CONFIG);//上报类型
        String reportCode = ReportUtils.getCode(upParams);//优先级 区 > 市 > 省

        if(StringUtils.isEmpty(reportCode))
            throw new BusinessException(RepReturnCodeEnum.REPORT_RETURN_373001);

        paramsDTO.setCityCode(reportCode);
        List<CityParamVO> dynamicFieldList = upDynamicFieldServiceImpl.getDynamicFieldList(paramsDTO, ijwtInfo);
        List<String> fields = dynamicFieldList.stream().map(field->field.getParamsName()).collect(Collectors.toList());
        super.isReportParamEmpty(upParamDTO, fields);
        // 保存参数
        Map<String,Object> requestCode = upParamsServiceImpl.saveProjConfig(upParams);
        return super.success(requestCode);
    }

}
