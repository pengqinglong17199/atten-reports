package com.quanroon.atten.reports.api.web;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Joiner;
import com.quanroon.atten.commons.base.AjaxResult;
import com.quanroon.atten.commons.base.BaseApiController;
import com.quanroon.atten.commons.enums.RepReturnCodeEnum;
import com.quanroon.atten.commons.enums.ReturnCodeEnum;
import com.quanroon.atten.commons.jwt.IJWTInfo;
import com.quanroon.atten.commons.utils.StringUtils;
import com.quanroon.atten.reports.common.ReportConstant;
import com.quanroon.atten.reports.entity.UpCompanyIn;
import com.quanroon.atten.reports.entity.UpCompanyInfo;
import com.quanroon.atten.reports.entity.UpProjectInfo;
import com.quanroon.atten.reports.entity.dto.CityParamsDTO;
import com.quanroon.atten.reports.entity.dto.UpCompanyInDTO;
import com.quanroon.atten.reports.entity.dto.UpCompanyInfoDTO;
import com.quanroon.atten.reports.entity.vo.CityParamVO;
import com.quanroon.atten.reports.service.UpCompanyInService;
import com.quanroon.atten.reports.service.UpCompanyService;
import com.quanroon.atten.reports.service.UpDynamicFieldService;
import com.quanroon.atten.reports.service.UpProjectInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * @content 上报企业内容相关接口
 * @date 2020/6/29 17:04
 */
@RestController
@RequestMapping(value = "${server.adminPath}/company")
public class CompanyController extends BaseApiController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UpCompanyService upCompanyServiceImpl;
    @Autowired
    private UpCompanyInService upCompanyInServiceImpl;
    @Autowired
    private UpDynamicFieldService upDynamicFieldServiceImpl;
    /**
     * 上报企业（参见单位）信息
     * @param companyInfoDTO
     * @param request
     * @return
     */
    @RequestMapping("upload")
    public AjaxResult uploadInfo(@RequestBody @Valid UpCompanyInfoDTO companyInfoDTO , HttpServletRequest request){
        IJWTInfo ijwtInfo = (IJWTInfo) request.getAttribute(PROJ_INFO);//获取项目id

        //根据城市校验必填
        CityParamsDTO paramsDTO = new CityParamsDTO();
        paramsDTO.setMethodName(ReportConstant.TYPE_COMPANY_REPORT);//上报类型
        List<CityParamVO> dynamicFieldList = upDynamicFieldServiceImpl.getDynamicFieldList(paramsDTO, ijwtInfo);
        List<String> fields = dynamicFieldList.stream()
                .map(field->field.getParamsName()).collect(Collectors.toList());
        super.isReportParamEmpty(companyInfoDTO, fields);

        //业务
        UpCompanyInfo upCompanyInfo = new UpCompanyInfo();
        BeanUtils.copyProperties(companyInfoDTO, upCompanyInfo);
        //时间格式转换
        upCompanyInfo.copyDateFormat(companyInfoDTO);

        //项目id
        upCompanyInfo.setProjId(ijwtInfo.getId());
        Map<String, Object> response = upCompanyServiceImpl.saveCompanyInfo(upCompanyInfo);
        return super.success(response);
    }
    /**
     * 企业（参见单位）入场
     * @param companyInDTO
     * @param request
     * @return
     */
    @RequestMapping("enter")
    public AjaxResult enterCompany(@RequestBody @Valid UpCompanyInDTO companyInDTO, HttpServletRequest request){
        IJWTInfo ijwtInfo = (IJWTInfo) request.getAttribute(PROJ_INFO);//获取项目id
        //根据城市校验必填
        CityParamsDTO paramsDTO = new CityParamsDTO();
        paramsDTO.setMethodName(ReportConstant.TYPE_COMPANY_ENTER);//上报类型
        List<CityParamVO> dynamicFieldList = upDynamicFieldServiceImpl.getDynamicFieldList(paramsDTO, ijwtInfo);
        List<String> fields = dynamicFieldList.stream()
                .map(field->field.getParamsName()).collect(Collectors.toList());
        super.isReportParamEmpty(companyInDTO, fields);
        //业务
        UpCompanyIn upCompanyIn = new UpCompanyIn();
        upCompanyIn.setProjId(ijwtInfo.getId());
        upCompanyIn.copyDateFormat(companyInDTO);//时间格式转换
        Map<String, Object> requestCode = upCompanyInServiceImpl.saveCompanyInOut(companyInDTO, upCompanyIn);
        return super.success(requestCode);
    }
    /**
     * 企业（参见单位）离场
     * @param companyInDTO
     * @param request
     * @return
     */
    @RequestMapping("leave")
    public AjaxResult leaveCompany(@RequestBody @Valid UpCompanyInDTO companyInDTO, HttpServletRequest request){
        IJWTInfo ijwtInfo = (IJWTInfo) request.getAttribute(PROJ_INFO);//获取项目id
        //根据城市校验必填
        CityParamsDTO paramsDTO = new CityParamsDTO();
        paramsDTO.setMethodName(ReportConstant.TYPE_COMPANY_LEAVE);//上报类型
        List<CityParamVO> dynamicFieldList = upDynamicFieldServiceImpl.getDynamicFieldList(paramsDTO, ijwtInfo);
        List<String> fields = dynamicFieldList.stream()
                .map(field->field.getParamsName()).collect(Collectors.toList());
        super.isReportParamEmpty(companyInDTO, fields);
        //业务
        UpCompanyIn upCompanyIn = new UpCompanyIn();
        upCompanyIn.setProjId(ijwtInfo.getId());
        upCompanyIn.copyDateFormat(companyInDTO);//时间格式转换
        Map<String, Object> requestCode = upCompanyInServiceImpl.saveCompanyInOut(companyInDTO, upCompanyIn);
        return super.success(requestCode);
    }

}
