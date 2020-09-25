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
import com.quanroon.atten.reports.entity.UpPayrollDetailInfo;
import com.quanroon.atten.reports.entity.UpPayrollInfo;
import com.quanroon.atten.reports.entity.dto.*;
import com.quanroon.atten.reports.entity.vo.CityParamVO;
import com.quanroon.atten.reports.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author quanroon.ysq
 * @version 1.0.0
 * @content 上报项目工资内容接口
 * @date 2020/7/05 12:04
 */
@RestController
@RequestMapping(value = "${server.adminPath}/salary")
public class SalaryController extends BaseApiController {

    @Autowired
    private UpSalaryInfoService upSalaryInfoServiceImpl;
    @Autowired
    private UpSalaryArriveService upSalaryArriveServiceImpl;
    @Autowired
    private UpPayrollInfoService upPayrollInfoServiceImpl;
    @Autowired
    private UpPayrollDetailInfoService upPayrollDetailInfoServiceImpl;
    @Autowired
    private UpDynamicFieldService upDynamicFieldServiceImpl;

    /**
     * 上报项目工资专户
     * @param dto
     * @param request
     * @return
     */
    @RequestMapping(value = "account")
    public AjaxResult uploadSalaryAccount(@RequestBody UpSalaryInfoDTO dto, HttpServletRequest request) {
        IJWTInfo ijwtInfo = (IJWTInfo) request.getAttribute(PROJ_INFO);
        //根据城市校验必填
        CityParamsDTO paramsDTO = new CityParamsDTO();
        paramsDTO.setMethodName(ReportConstant.TYPE_ACCOUNT_REPORT);//上报类型
        List<CityParamVO> dynamicFieldList = upDynamicFieldServiceImpl.getDynamicFieldList(paramsDTO, ijwtInfo);
        List<String> fields = dynamicFieldList.stream().map(field->field.getParamsName()).collect(Collectors.toList());
        super.isReportParamEmpty(dto, fields);
        //保存数据
        Map<String,Object> map = upSalaryInfoServiceImpl.uploadSalaryAccount(dto,ijwtInfo);
        return super.success(map);
    }

    /**
     * 上报项目工资专户到账信息
     * @param dto
     * @param request
     * @return
     */
    @RequestMapping(value = "remittance")
    public AjaxResult uploadSalaryRemittance(@RequestBody UpSalaryArriveDTO dto, HttpServletRequest request) {
        IJWTInfo ijwtInfo = (IJWTInfo) request.getAttribute(PROJ_INFO);
        //根据城市校验必填
        CityParamsDTO paramsDTO = new CityParamsDTO();
        paramsDTO.setMethodName(ReportConstant.TYPE_SALARY_ARRIVE);//上报类型
        List<CityParamVO> dynamicFieldList = upDynamicFieldServiceImpl.getDynamicFieldList(paramsDTO, ijwtInfo);
        List<String> fields = dynamicFieldList.stream().map(field->field.getParamsName()).collect(Collectors.toList());
        super.isReportParamEmpty(dto, fields);
        //保存数据
        Map<String,Object> map = upSalaryArriveServiceImpl.uploadSalaryRemittance(dto);
        return super.success(map);
    }

    /**
     * 上报项目月工资单
     * @param dto
     * @param request
     * @return
     */
    @RequestMapping(value = "upload")
    public AjaxResult uploadPayrollInfo(@RequestBody UpPayrollInfoDTO dto, HttpServletRequest request) {
        IJWTInfo ijwtInfo = (IJWTInfo) request.getAttribute(PROJ_INFO);
        //根据城市校验必填
        CityParamsDTO paramsDTO = new CityParamsDTO();
        paramsDTO.setMethodName(ReportConstant.TYPE_PAYROLL_REPORT);//上报类型
        List<CityParamVO> dynamicFieldList = upDynamicFieldServiceImpl.getDynamicFieldList(paramsDTO, ijwtInfo);
        List<String> fields = dynamicFieldList.stream().map(field->field.getParamsName()).collect(Collectors.toList());
        super.isReportParamEmpty(dto, fields);

        //保存数据
        UpPayrollInfo upPayrollInfo = new UpPayrollInfo();
        BeanUtils.copyProperties(dto, upPayrollInfo);
        upPayrollInfo.setId(dto.getPayrollId());//业务id
        upPayrollInfo.setProjId(ijwtInfo.getId());//项目id
        upPayrollInfo.setGrantSalaryDate(dto.getGrantSalaryDate());
        Map<String,Object> map = upPayrollInfoServiceImpl.uploadPayrollInfo(upPayrollInfo);
        return super.success(map);
    }

    /**
     * 上报项目月工资单明细
     * @param dto
     * @param request
     * @return
     */
    @RequestMapping(value = "detail")
    public AjaxResult uploadSalaryDetail(@RequestBody UpPayrollDetailInfoDTO dto, HttpServletRequest request) {
        IJWTInfo ijwtInfo = (IJWTInfo) request.getAttribute(PROJ_INFO);
        //根据城市校验必填
        CityParamsDTO paramsDTO = new CityParamsDTO();
        paramsDTO.setMethodName(ReportConstant.TYPE_PAYROLL_DETAIL);//上报类型
        List<CityParamVO> dynamicFieldList = upDynamicFieldServiceImpl.getDynamicFieldList(paramsDTO, ijwtInfo);
        List<String> fields = dynamicFieldList.stream().map(field->field.getParamsName()).collect(Collectors.toList());
        super.isReportParamEmpty(dto, fields);
        //对附件进行转换
        if(Objects.nonNull(dto.getPayRollFilePath())){
            if(Objects.isNull(dto.getPayRollFileName()))
                throw new BusinessException(RepReturnCodeEnum.REPORT_RETURN_361002);
            dto.setPayRollFilePath(Base64Utils.base64ToFile(dto.getPayRollFilePath(),dto.getPayRollFileName(),"payrollFilePath"));
        }
        if(Objects.nonNull(dto.getPayRollCertificatePath())){
            dto.setPayRollCertificatePath(Base64Utils.base64ToFile(dto.getPayRollCertificatePath(),null,"payrollCertificatePath"));
        }
        //保存数据
        Map<String,Object> map = upPayrollDetailInfoServiceImpl.uploadSalaryDetail(dto);

        return super.success(map);
    }


}

