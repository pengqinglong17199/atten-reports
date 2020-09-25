package com.quanroon.atten.reports.api.web;

import com.alibaba.fastjson.JSONObject;
import com.quanroon.atten.commons.annotation.JsonValueValidate;
import com.quanroon.atten.commons.base.AjaxResult;
import com.quanroon.atten.commons.base.BaseApiController;
import com.quanroon.atten.commons.enums.RepReturnCodeEnum;
import com.quanroon.atten.commons.exception.BusinessException;
import com.quanroon.atten.commons.jwt.IJWTInfo;
import com.quanroon.atten.commons.utils.Base64Utils;
import com.quanroon.atten.reports.common.ReportConstant;
import com.quanroon.atten.reports.entity.UpWorkerIn;
import com.quanroon.atten.reports.entity.UpWorkerInfo;
import com.quanroon.atten.reports.entity.dto.CityParamsDTO;
import com.quanroon.atten.reports.entity.dto.UpWorkerInDTO;
import com.quanroon.atten.reports.entity.dto.UpWorkerInfoDTO;
import com.quanroon.atten.reports.entity.vo.CityParamVO;
import com.quanroon.atten.reports.service.UpDynamicFieldService;
import com.quanroon.atten.reports.service.UpWorkerInService;
import com.quanroon.atten.reports.service.UpWorkerInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author quanroon.ysq
 * @version 1.0.0
 * @content 上报劳工内容相关接口
 * @date 2020/6/29 17:04
 */
@RestController
@RequestMapping(value = "${server.adminPath}/worker")
public class WorkerController extends BaseApiController {

    @Autowired
    private UpWorkerInfoService upWorkerInfoServiceImpl;
    @Autowired
    private UpWorkerInService upWorkerInServiceImpl;
    @Autowired
    private UpDynamicFieldService upDynamicFieldServiceImpl;

    /**
     * 上报劳工信息
     * @param requestBodyStr
     * @param request
     * @return
     */
    @RequestMapping("upload")
    public AjaxResult uploadWorkerInfo(@RequestBody String requestBodyStr, HttpServletRequest request){
        //参数转换
        JSONObject jsonObject = super.getRequestJson(requestBodyStr);
        Integer workerId = jsonObject.getInteger("workerId");//用于劳工信息修改
        UpWorkerInfoDTO workerInfoDTO = JSONObject.parseObject(requestBodyStr, UpWorkerInfoDTO.class);
        IJWTInfo ijwtInfo = (IJWTInfo) request.getAttribute(PROJ_INFO);//获取项目信息
        //根据城市校验必填
        CityParamsDTO paramsDTO = new CityParamsDTO();
        paramsDTO.setMethodName(ReportConstant.TYPE_WORKER_REPORT);//上报类型
        List<CityParamVO> dynamicFieldList = upDynamicFieldServiceImpl.getDynamicFieldList(paramsDTO, ijwtInfo);
        List<String> fields = dynamicFieldList.stream().map(field->field.getParamsName()).collect(Collectors.toList());
        super.isReportParamEmpty(workerInfoDTO, fields);
        //业务
        UpWorkerInfo upWorkerInfo = new UpWorkerInfo();
        upWorkerInfo.setProjId(ijwtInfo.getId());//项目id
        //对照片附件进行校验并生成服务器资源
        if(Objects.nonNull(workerInfoDTO.getWorkerPicture())){
            workerInfoDTO.setWorkerPicture(Base64Utils.base64ToFile(workerInfoDTO.getWorkerPicture(), null,"workerPicture"));
        }
        if(Objects.nonNull(workerInfoDTO.getCardNoPersonPicture())){
            workerInfoDTO.setCardNoPersonPicture(Base64Utils.base64ToFile(workerInfoDTO.getCardNoPersonPicture(), null,"cardNoPersonPicture"));
        }
        if(Objects.nonNull(workerInfoDTO.getCardNoHeadsPicture())){
            workerInfoDTO.setCardNoHeadsPicture(Base64Utils.base64ToFile(workerInfoDTO.getCardNoHeadsPicture(), null,"cardNoHeadsPicture"));
        }
        if(Objects.nonNull(workerInfoDTO.getCardNoTailsPicture())){
            workerInfoDTO.setCardNoTailsPicture(Base64Utils.base64ToFile(workerInfoDTO.getCardNoTailsPicture(), null,"cardNoTailsPicture"));
        }

        upWorkerInfo.setId(workerId);
        Map<String, Object> response = upWorkerInfoServiceImpl.saveWorkerInfo(workerInfoDTO,upWorkerInfo);
        return super.success(response);
    }
    /**
     * 上报劳工进场信息
     * @param workerInDTO
     * @param request
     * @return
     */
    @RequestMapping("enter")
    public AjaxResult enterWorkerIn(@RequestBody @Valid UpWorkerInDTO workerInDTO, HttpServletRequest request){
        //参数转换
        IJWTInfo ijwtInfo = (IJWTInfo) request.getAttribute(PROJ_INFO);//获取项目信息
        //根据城市校验必填
        CityParamsDTO paramsDTO = new CityParamsDTO();
        paramsDTO.setMethodName(ReportConstant.TYPE_WORKER_ENTER);//上报类型
        List<CityParamVO> dynamicFieldList = upDynamicFieldServiceImpl.getDynamicFieldList(paramsDTO, ijwtInfo);
        List<String> fields = dynamicFieldList.stream().map(field->field.getParamsName()).collect(Collectors.toList());
        super.isReportParamEmpty(workerInDTO, fields);
        //数据转换
        UpWorkerIn workerIn = new UpWorkerIn();
        BeanUtils.copyProperties(workerInDTO,workerIn);
        workerIn.copyDateFormat(workerInDTO);//时间格式转换
        workerIn.setProjId(ijwtInfo.getId());//项目id
        //对照片附件进行校验并生成服务器资源
        if(Objects.nonNull(workerInDTO.getContractFile())){
            if(Objects.isNull(workerInDTO.getContractTitle()))
                throw new BusinessException(RepReturnCodeEnum.REPORT_RETURN_361002);
            workerInDTO.setContractFile(Base64Utils.base64ToFile(workerInDTO.getContractFile(), workerInDTO.getContractTitle(),"contractFile"));
        }
        if(Objects.nonNull(workerInDTO.getInsurancePicture())){
            workerInDTO.setInsurancePicture(Base64Utils.base64ToFile(workerInDTO.getInsurancePicture(), null,"insurancePicture"));
        }
        if(Objects.nonNull(workerInDTO.getCertificatePicture())){
            workerInDTO.setCertificatePicture(Base64Utils.base64ToFile(workerInDTO.getCertificatePicture(), null,"certificatePicture"));
        }
        if(Objects.nonNull(workerInDTO.getBankPicture())){
            workerInDTO.setBankPicture(Base64Utils.base64ToFile(workerInDTO.getBankPicture(), null,"bankPicture"));
        }
        //业务
        Map<String, Object> response = upWorkerInServiceImpl.saveWorkerInOut(workerInDTO, workerIn);
        return super.success(response);
    }

    /**
     * 上报劳工离场信息
     * @param workerInDTO
     * @param request
     * @return
     */
    @RequestMapping("leave")
    public AjaxResult leaveWorkerIn(@RequestBody @Valid UpWorkerInDTO workerInDTO, HttpServletRequest request){
        IJWTInfo ijwtInfo = (IJWTInfo) request.getAttribute(PROJ_INFO);//获取项目信息
        //根据城市校验必填
        CityParamsDTO paramsDTO = new CityParamsDTO();
        paramsDTO.setMethodName(ReportConstant.TYPE_WORKER_LEAVE);//上报类型
        List<CityParamVO> dynamicFieldList = upDynamicFieldServiceImpl.getDynamicFieldList(paramsDTO, ijwtInfo);
        List<String> fields = dynamicFieldList.stream().map(field->field.getParamsName()).collect(Collectors.toList());
        super.isReportParamEmpty(workerInDTO, fields);
        //业务
        UpWorkerIn workerIn = new UpWorkerIn();
        workerIn.setProjId(ijwtInfo.getId());//项目id
        BeanUtils.copyProperties(workerInDTO, workerIn);
        workerIn.copyDateFormat(workerInDTO);//时间格式转换
        Map<String, Object> response = upWorkerInServiceImpl.saveWorkerInOut(null, workerIn);
        return super.success(response);
    }
}
