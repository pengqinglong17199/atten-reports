package com.quanroon.atten.reports.api.web;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Joiner;
import com.quanroon.atten.commons.base.AjaxResult;
import com.quanroon.atten.commons.base.BaseApiController;
import com.quanroon.atten.commons.enums.RepReturnCodeEnum;
import com.quanroon.atten.commons.exception.BusinessException;
import com.quanroon.atten.commons.jwt.IJWTInfo;
import com.quanroon.atten.commons.utils.Base64Utils;
import com.quanroon.atten.reports.common.ReportConstant;
import com.quanroon.atten.reports.entity.UpGroupInfo;
import com.quanroon.atten.reports.entity.dto.CityParamsDTO;
import com.quanroon.atten.reports.entity.dto.UpGroupInfoDTO;
import com.quanroon.atten.reports.entity.vo.CityParamVO;
import com.quanroon.atten.reports.service.UpDynamicFieldService;
import com.quanroon.atten.reports.service.UpGroupInfoService;
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
 * @content 上报班组内容相关接口
 * @date 2020/6/29 17:04
 */
@RestController
@RequestMapping(value = "${server.adminPath}/group")
public class GroupController extends BaseApiController {

    @Autowired
    private UpGroupInfoService upGroupInfoServiceImpl;
    @Autowired
    private UpDynamicFieldService upDynamicFieldServiceImpl;
    /**
     * 上报班组信息
     * @param groupInfoDTO
     * @param request
     * @return
     */
    @RequestMapping("upload")
    public AjaxResult uploadGroupInfo(@RequestBody @Valid UpGroupInfoDTO groupInfoDTO, HttpServletRequest request){
        IJWTInfo ijwtInfo = (IJWTInfo) request.getAttribute(PROJ_INFO);//获取项目信息
        //根据城市校验必填
        CityParamsDTO paramsDTO = new CityParamsDTO();
        paramsDTO.setMethodName(ReportConstant.TYPE_GROUP_REPORT);//上报类型
        List<CityParamVO> dynamicFieldList = upDynamicFieldServiceImpl.getDynamicFieldList(paramsDTO, ijwtInfo);
        List<String> fields = dynamicFieldList.stream().map(field->field.getParamsName()).collect(Collectors.toList());
        super.isReportParamEmpty(groupInfoDTO, fields);

        //对附件进行校验并生成附件
        if(Objects.nonNull(groupInfoDTO.getContractFile())){
            if(Objects.isNull(groupInfoDTO.getContractTitle()))
                throw new BusinessException(RepReturnCodeEnum.REPORT_RETURN_361002);
            groupInfoDTO.setContractFile(Base64Utils.base64ToFile(groupInfoDTO.getContractFile(), groupInfoDTO.getContractTitle(), "contractFile"));
        }
        UpGroupInfo upGroupInfo = new UpGroupInfo();
        BeanUtils.copyProperties(groupInfoDTO, upGroupInfo);
        upGroupInfo.copyDateFormat(groupInfoDTO);//时间格式转换
        upGroupInfo.setProjId(ijwtInfo.getId());//项目id
        upGroupInfo.setId(groupInfoDTO.getGroupId());//用于班组信息修改
        //业务
        Map<String, Object> response = upGroupInfoServiceImpl.saveGroupInfo(upGroupInfo,groupInfoDTO);
        return super.success(response);
    }
    /**
     * 上报班组离场信息
     * @param requestBodyStr
     * @param request
     * @return
     */
    @RequestMapping("leave")
    public AjaxResult leaveGroupInfo(@RequestBody String requestBodyStr, HttpServletRequest request){
        //参数转换
        JSONObject jsonObject = super.getRequestJson(requestBodyStr);
        if(Objects.isNull(jsonObject.getInteger("groupId"))
                || Objects.isNull(jsonObject.getDate("leaveDate"))){
            return super.failed(RepReturnCodeEnum.REPORT_RETURN_100001);
        }
        UpGroupInfo upGroupInfo = new UpGroupInfo();
        IJWTInfo ijwtInfo = (IJWTInfo) request.getAttribute(PROJ_INFO);//获取项目信息
        upGroupInfo.setProjId(ijwtInfo.getId());//项目id
        upGroupInfo.setId(jsonObject.getInteger("groupId"));
        upGroupInfo.setLeaveDate(jsonObject.getDate("leaveDate"));
        Map<String, Object> response = upGroupInfoServiceImpl.saveGroupInfo(upGroupInfo, new UpGroupInfoDTO());
        return super.success(response);
    }
}
