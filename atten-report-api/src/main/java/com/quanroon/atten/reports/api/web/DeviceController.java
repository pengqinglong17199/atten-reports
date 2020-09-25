package com.quanroon.atten.reports.api.web;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Joiner;
import com.quanroon.atten.commons.base.AjaxResult;
import com.quanroon.atten.commons.base.BaseApiController;
import com.quanroon.atten.commons.enums.RepReturnCodeEnum;
import com.quanroon.atten.commons.jwt.IJWTInfo;
import com.quanroon.atten.commons.utils.StringUtils;
import com.quanroon.atten.reports.common.ReportConstant;
import com.quanroon.atten.reports.entity.UpCompanyInfo;
import com.quanroon.atten.reports.entity.UpDeviceInfo;
import com.quanroon.atten.reports.entity.dto.CityParamsDTO;
import com.quanroon.atten.reports.entity.dto.UpCompanyInfoDTO;
import com.quanroon.atten.reports.entity.dto.UpDeviceInfoDTO;
import com.quanroon.atten.reports.entity.vo.CityParamVO;
import com.quanroon.atten.reports.service.UpDeviceInfoService;
import com.quanroon.atten.reports.service.UpDynamicFieldService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author quanroon.ysq
 * @version 1.0.0
 * @content 上报考勤机内容相关接口
 * @date 2020/6/29 17:04
 */
@RestController
@RequestMapping(value = "${server.adminPath}/device")
public class DeviceController extends BaseApiController {

    @Autowired
    private UpDeviceInfoService upDeviceInfoServiceImpl;
    @Autowired
    private UpDynamicFieldService upDynamicFieldServiceImpl;
    /**
     * 上报考勤机信息
     * @param requestBodyStr
     * @param request
     * @return
     */
    @RequestMapping("upload")
    public AjaxResult uploadDeviceInfo(@RequestBody @Valid UpDeviceInfoDTO deviceInfoDTO, HttpServletRequest request){
        IJWTInfo ijwtInfo = (IJWTInfo) request.getAttribute(PROJ_INFO);//获取项目信息
        //根据城市校验必填
        CityParamsDTO paramsDTO = new CityParamsDTO();
        paramsDTO.setMethodName(ReportConstant.TYPE_DEVICE_REPORT);//上报类型
        List<CityParamVO> dynamicFieldList = upDynamicFieldServiceImpl.getDynamicFieldList(paramsDTO, ijwtInfo);
        List<String> fields = dynamicFieldList.stream().map(field->field.getParamsName()).collect(Collectors.toList());
        super.isReportParamEmpty(deviceInfoDTO, fields);
        //业务

        UpDeviceInfo upDeviceInfo = new UpDeviceInfo();
        BeanUtils.copyProperties(deviceInfoDTO, upDeviceInfo);

        upDeviceInfo.setProjId(ijwtInfo.getId());//项目id
        Map<String, Object> response = upDeviceInfoServiceImpl.saveDeivceInfo(upDeviceInfo);
        return super.success(response);
    }

    /**
     * 解绑考勤机信息
     * @param requestBodyStr
     * @param request
     * @return
     */
    @RequestMapping("unbind")
    public AjaxResult unbindDeviceInfo(@RequestBody String requestBodyStr, HttpServletRequest request){
        JSONObject jsonObject = this.getRequestJson(requestBodyStr);
        if(Objects.isNull(jsonObject.get("deviceId")))
            return super.failed(RepReturnCodeEnum.REPORT_RETURN_100001);
        UpDeviceInfo upDeviceInfo = new UpDeviceInfo();
        IJWTInfo ijwtInfo = (IJWTInfo) request.getAttribute(PROJ_INFO);//获取项目信息
        upDeviceInfo.setProjId(ijwtInfo.getId());//项目id
        upDeviceInfo.setId(jsonObject.getInteger("deviceId"));
        Map<String, Object> response = upDeviceInfoServiceImpl.removeDeviceInfo(upDeviceInfo);
        return super.success(response);
    }
}
