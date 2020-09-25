/*
 * Copyright (c) 2020, QUANRONG TECHNOLOGY LTD. All rights reserved.
 */
package com.quanroon.atten.reports.job.henan.serviceimpl;

import com.alibaba.fastjson.JSONObject;
import com.quanroon.atten.commons.utils.StringUtils;
import com.quanroon.atten.reports.common.ReportConstant;
import com.quanroon.atten.reports.common.ReportType;
import com.quanroon.atten.reports.dao.*;
import com.quanroon.atten.reports.entity.*;
import com.quanroon.atten.reports.entity.example.UpFileExample;
import com.quanroon.atten.reports.job.henan.config.HeNanConfig;
import com.quanroon.atten.reports.job.henan.entity.HeNanResult;
import com.quanroon.atten.reports.job.henan.service.HeNanService;
import com.quanroon.atten.reports.job.henan.utils.HeNanHttpUtils;
import com.quanroon.atten.reports.job.henan.utils.ImageUtils;
import com.quanroon.atten.reports.job.jinhua.config.AESUtils;
import com.quanroon.atten.reports.report.annotation.City;
import com.quanroon.atten.reports.report.annotation.ReportMethod;
import com.quanroon.atten.reports.report.constant.ReportCityCode;
import com.quanroon.atten.reports.report.entity.ReportResult;
import com.quanroon.atten.reports.report.entity.ReportService;
import com.quanroon.atten.reports.service.UpDictReportService;
import com.quanroon.atten.reports.service.UpProjectInfoService;
import com.quanroon.atten.reports.service.UpRecordService;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.*;

/*import com.quanroon.atten.reports.job.henan.config.RocketmqProducer;*/

/**
 * @Auther: Elvis
 * @Date: 2020-07-15 16:47
 * @Description: 河南上报
 */
@City(ReportCityCode.HENAN)
@Slf4j
public class HeNanServiceImpl implements HeNanService,ReportService {

    private final static String HENAN_CODE = "410000";

    @Autowired
    private UpProjectInfoService upProjectInfoService;

    @Autowired
    private UpRecordService upRecordService;

    @Autowired
    private UpCompanyInfoMapper upCompanyInfoMapper;

    @Autowired
    private UpProjectCertificateMapper upProjectCertificateMapper;

    @Autowired
    private UpGroupInfoMapper upGroupInfoMapper;

    @Autowired
    private UpWorkerInfoMapper upWorkerInfoMapper;

    @Autowired
    private UpWorkerInMapper upWorkerInMapper;

    @Autowired
    private UpWorkerSignlogInfoMapper upWorkerSignlogInfoMapper;

    @Autowired
    private UpCompanyInMapper upCompanyInMapper;

    @Autowired
    private UpFileMapper upFileMapper;

    @Autowired
    private UpParamsMapper upParamsMapper;

/*    @Autowired
    private RocketmqProducer rocketmqProducer;*/

    @Autowired
    private UpDictReportService upDictReportService;

    /**
     * 上报项目
     * @param reportMessage
     * @return
     */
    @Override
    @ReportMethod({ReportType.proj_report,ReportType.proj_update})
    public ReportResult reportProject(ReportMessage reportMessage) {
        log.info("===========================》开始上报河南项目,入参为："+JSONObject.toJSONString(reportMessage));
        HeNanResult heNanResult = new HeNanResult();
        String result = null;
        String id = reportMessage.getDataId();
        String requestCode = reportMessage.getRequestCode();
        ReportType reportType = reportMessage.getReportType();
        try{
            UpProjectInfo upProjectInfo =  upProjectInfoService.selectByPrimaryKey(Integer.parseInt(id));
            Map<String,Object> dataMap = this.assembleProjectData(upProjectInfo);
            UpParams upParams = upParamsMapper.selectByProjId(Integer.parseInt(id));
            JSONObject jsonObject = HeNanHttpUtils.httpPost(HeNanConfig.ADD_PROJECT_METHOD,dataMap,upParams);
            String code = jsonObject.getString("requestcode");
            JSONObject json = HeNanHttpUtils.findResult(code,upParams);
            if(HeNanConfig.SUCCESS_CODE==json.getJSONObject("data").getJSONObject("DEALRESULT").getInteger("code")){
                String responseCode = json.getJSONObject("data").getJSONObject("DEALRESULT").getJSONObject("data").getString("PROJECTCODE");
                UpProjectInfo record = new UpProjectInfo();
                record.setId(Integer.parseInt(id));
                record.setReportCode(responseCode);
                upProjectInfoService.updateByPrimaryKey(record);
                heNanResult.setCode(HeNanConfig.RESULT_SUCCESS_CODE);
                log.info("===========================》上报河南項目成功！！！");
            }else{
                this.reportFail(heNanResult,json.getString("message"));
            }
        }catch (Exception e){
            log.error("=========================》数据异常，上报河南项目失败!");
            e.printStackTrace();
            this.reportFail(heNanResult,"数据异常，上报河南企业失败！");
        }

        //发送rocketmq消息
        //rocketmqProducer.send(new HeNanMessage("21",HeNanConfig.ADD_PROJECT_METHOD,code,code));
        return heNanResult;
    }

    /**
     * 组装上报企业数据
     * @param reportMessage
     * @return
     */

    public ReportResult reportCompany(ReportMessage reportMessage){
        log.info("===========================》开始上报河南企业,入参为："+JSONObject.toJSONString(reportMessage));
        HeNanResult heNanResult = new HeNanResult();
        String result = null;
        String id = reportMessage.getDataId();
        try{
/*            UpCompanyInfo upCompanyInfo = upCompanyInfoMapper.selectByPrimaryKey(Integer.parseInt(id));
            UpCompanyIn upCompanyIn = upCompanyInMapper.selectByCompanyId(upCompanyInfo.getId());*/
            UpCompanyIn upCompanyIn = upCompanyInMapper.selectByPrimaryKey(Integer.parseInt(reportMessage.getDataId()));
            UpProjectInfo upProjectInfo = upProjectInfoService.selectByPrimaryKey(upCompanyIn.getProjId());
            UpCompanyInfo upCompanyInfo = upCompanyInfoMapper.selectByPrimaryKey(upCompanyIn.getCompanyId());
            Map<String,Object> dataMap = this.assembleCompanyData(upCompanyInfo,upCompanyIn,upProjectInfo);
            UpParams upParams = upParamsMapper.selectByProjId(upProjectInfo.getId());
            JSONObject jsonObject = HeNanHttpUtils.httpPost(HeNanConfig.ADD_COMPANY_METHOD,dataMap,upParams);
            String code = jsonObject.getString("requestcode");
            JSONObject json = HeNanHttpUtils.findResult(code,upParams);
            if(HeNanConfig.SUCCESS_CODE==json.getJSONObject("data").getJSONObject("DEALRESULT").getInteger("code")){
                log.info("===========================》上报企业成功！！！");
                //this.reportContractor(reportMessage);
                heNanResult.setCode(HeNanConfig.RESULT_SUCCESS_CODE);
            /*String responseCode = json.getJSONObject("data").getJSONObject("DEALRESULT").getJSONObject("data").getString("PROJECTCODE");
            upCompanyInMapper.updateByCompanyId(upCompanyInfo.getId(),responseCode);*/
            }else{
                this.reportFail(heNanResult,json.getString("message"));
            }
        }catch (Exception e){
            log.error("=========================》数据异常，上报河南企业失败！");
            e.printStackTrace();
            this.reportFail(heNanResult,"数据异常，上报河南企业失败！");
        }

        return heNanResult;
    }

    /**
     * 上报参建单位
     * @param reportMessage
     * @return
     */
    @ReportMethod({ReportType.company_enter})
    public ReportResult reportContractor(ReportMessage reportMessage){
        log.info("===========================》开始上报河南参建单位信息,入参为："+JSONObject.toJSONString(reportMessage));
        HeNanResult heNanResult = new HeNanResult();
        String result = null;
        String id = reportMessage.getDataId();
        UpCompanyIn upCompanyIn = upCompanyInMapper.selectByCompanyId(Integer.parseInt(reportMessage.getDataId()));
        UpCompanyInfo upCompanyInfo = upCompanyInfoMapper.selectByPrimaryKey(upCompanyIn.getCompanyId());
        UpProjectInfo upProjectInfo = upProjectInfoService.selectByPrimaryKey(upCompanyIn.getProjId());
        UpParams upParams = upParamsMapper.selectByProjId(upProjectInfo.getId());
        Map<String,Object> dataMap = this.assembleContractorData(upCompanyInfo,upCompanyIn,upProjectInfo);
        JSONObject jsonObject = HeNanHttpUtils.httpPost(HeNanConfig.ADD_CONTRACTOR_METHOD,dataMap,upParams);
        String code = jsonObject.getString("requestcode");
        JSONObject json = HeNanHttpUtils.findResult(code,upParams);
        if(HeNanConfig.SUCCESS_CODE==json.getJSONObject("data").getJSONObject("DEALRESULT").getInteger("code")){
            log.info("上报企业成功！！！");
            log.info("===========================》上报河南参建单位信息成功！");
            heNanResult.setCode(HeNanConfig.RESULT_SUCCESS_CODE);
        }
        return heNanResult;
    }

    /**
     * 上报班组
     * @param reportMessage
     * @return
     */
    @ReportMethod({ReportType.group_report, ReportType.group_update})
    public ReportResult reportTeam(ReportMessage reportMessage){
        log.info("===========================》开始上报河南班组信息,入参为："+JSONObject.toJSONString(reportMessage));
        HeNanResult heNanResult = new HeNanResult();
        try{
            UpGroupInfo upGroupInfo =  upGroupInfoMapper.selectByPrimaryKey(Integer.parseInt(reportMessage.getDataId()));
            UpParams upParams = upParamsMapper.selectByProjId(upGroupInfo.getProjId());
            Map<String,Object> dataMap = this.assembleGroupData(upGroupInfo);
            JSONObject jsonObject = HeNanHttpUtils.httpPost(HeNanConfig.ADD_GROUP_METHOD,dataMap,upParams);
            String code = jsonObject.getString("requestcode");
            JSONObject json = HeNanHttpUtils.findResult(code,upParams);
            if(HeNanConfig.SUCCESS_CODE==json.getJSONObject("data").getJSONObject("DEALRESULT").getInteger("code")){
                String responseCode = json.getJSONObject("data").getJSONObject("DEALRESULT").getJSONObject("data").getString("TEAMSYSNO");
                UpGroupInfo groupInfo = new UpGroupInfo();
                groupInfo.setId(upGroupInfo.getId());
                groupInfo.setReportCode(responseCode);
                upGroupInfoMapper.updateByPrimaryKeySelective(groupInfo);
                heNanResult.setCode(HeNanConfig.RESULT_SUCCESS_CODE);
                log.error("===========================》上报河南班组成功！");
            }else{
                this.reportFail(heNanResult,json.getString("message"));
            }
        }catch (Exception e){
            log.error("===========================》数据异常，上报河南班组失败！");
            e.printStackTrace();
            this.reportFail(heNanResult,"数据异常，上报河南班组失败！");

        }
        return heNanResult;
    }

    /**
     * 上报项目工人
     * @param reportMessage
     * @return
     */
    @ReportMethod({ReportType.worker_enter})
    public ReportResult reportProjectWorker(ReportMessage reportMessage){
        log.info("===========================》开始上报河南项目工人,入参为："+JSONObject.toJSONString(reportMessage));
        HeNanResult heNanResult = new HeNanResult();
        String result = null;
        try{
            UpWorkerIn upWorkerIn = upWorkerInMapper.selectByPrimaryKey(Integer.parseInt(reportMessage.getDataId()));
            UpWorkerInfo upWorkerInfo = upWorkerInfoMapper.selectByPrimaryKey(upWorkerIn.getWorkerId());
            UpParams upParams = upParamsMapper.selectByProjId(upWorkerInfo.getProjId());
            Map<String,Object> dataMap = this.assembleProjectWorkerData(upWorkerInfo,upWorkerIn,upParams);
            JSONObject jsonObject = HeNanHttpUtils.httpPost(HeNanConfig.ADD_WORKER_METHOD,dataMap,upParams);
            String code = jsonObject.getString("requestcode");
            JSONObject json = HeNanHttpUtils.findResult(code,upParams);
            if(HeNanConfig.SUCCESS_CODE==json.getJSONObject("data").getJSONObject("DEALRESULT").getInteger("code")){
                String responseCode = json.getJSONObject("data").getJSONObject("DEALRESULT").getJSONObject("data").getString("CONTRACTTYPE");
                upWorkerInMapper.updateResponseCodeByWorkerId(upWorkerInfo.getId(),responseCode);
                heNanResult.setCode(HeNanConfig.RESULT_SUCCESS_CODE);
                log.info("===========================》上报河南项目工人成功！");
            }else{
                this.reportFail(heNanResult,json.getString("message"));
            }
        }catch (Exception e){
            log.error("===========================》数据异常，上报河南劳工失败！", e);
            e.printStackTrace();
            this.reportFail(heNanResult,"数据异常，上报河南劳工失败！");
        }
        return heNanResult;
    }

    /**
     * 上报项目工人考勤
     * @param reportMessage
     * @return
     */
    @ReportMethod({ReportType.worker_signlog})
    public ReportResult reportWorkerAttendance(ReportMessage reportMessage){
        log.info("===========================》开始上报河南项目工人,入参为："+JSONObject.toJSONString(reportMessage));
        HeNanResult heNanResult = new HeNanResult();
        try{
            UpWorkerSignlogInfo upWorkerSignlogInfo = upWorkerSignlogInfoMapper.selectByPrimaryKey(Integer.parseInt(reportMessage.getDataId()));
            UpParams upParams = upParamsMapper.selectByProjId(upWorkerSignlogInfo.getProjId());
            Map<String,Object> dataMap = this.assembleWorkerAttendanceData(upWorkerSignlogInfo,upParams);
            JSONObject jsonObject = HeNanHttpUtils.httpPost(HeNanConfig.ADD_WORKER_ATTENDANCE_METHOD,dataMap,upParams);
            String code = jsonObject.getString("requestcode");
            JSONObject json = HeNanHttpUtils.findResultSignlog(code,upParams,120000l);
            log.info("=============================>上报劳工考勤查询结果："+JSONObject.toJSONString(json));
            if(HeNanConfig.SUCCESS_CODE==json.getJSONObject("data").getJSONObject("DEALRESULT").getInteger("code")){
                heNanResult.setCode(HeNanConfig.RESULT_SUCCESS_CODE);
                log.info("===========================》上报劳工考勤成功！！！");
            }else{
                this.reportFail(heNanResult,json.getString("message"));
            }
        }catch (Exception e){
            log.error("===========================》数据异常，上报河南考勤失败！");
            e.printStackTrace();
            this.reportFail(heNanResult,"数据异常，上报河南考勤失败！");
        }
        return heNanResult;
    }

    /**
     * 组装上报考勤
     * @param upWorkerSignlogInfo
     * @return
     */
    public Map<String,Object> assembleWorkerAttendanceData(UpWorkerSignlogInfo upWorkerSignlogInfo,UpParams upParams){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        UpWorkerIn upWorkerIn = upWorkerInMapper.selectByWorkerId(upWorkerSignlogInfo.getWorkerId());
        UpWorkerInfo upWorkerInfo = upWorkerInfoMapper.selectByPrimaryKey(upWorkerSignlogInfo.getWorkerId());
        UpGroupInfo upGroupInfo = upGroupInfoMapper.selectByPrimaryKey(upWorkerIn.getGroupId());
        UpCompanyInfo upCompanyInfo = upCompanyInfoMapper.selectByPrimaryKey(upGroupInfo.getCompanyId());
        UpProjectInfo upProjectInfo = upProjectInfoService.selectByPrimaryKey(upWorkerInfo.getProjId());
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("PROJECTCODE",upProjectInfo.getReportCode()); //项目编码(上传项目返回)
        dataMap.put("TEAMSYSNO",upGroupInfo.getReportCode()); //班组编号(上传班组返回)
        dataMap.put("SOCIALCREDITCODE",upCompanyInfo.getCorpCode()); //参建单位的统一社会信用代码
        dataMap.put("CONTRACTTYPE",upWorkerIn.getReportCode()); //平台生成的合同编号(上传工人合同返回)
        dataMap.put("IDNUMBER",AESUtils.aesEncrypt(upWorkerInfo.getCardNo(), upParams.getSecret())); //农民工身份证号码AES
        dataMap.put("STARTTIME",sdf.format(upWorkerSignlogInfo.getTime())); //上班打卡时间，yyyy-MM-dd HH:mm:ss
        dataMap.put("SIGNDATE",formatDate.format(upWorkerSignlogInfo.getTime())); //打卡日期，yyyy-MM-dd
        return dataMap;
    }


    /**
     * 组装上报项目工人数据
     * @param upWorkerInfo
     * @return
     */
    public Map<String,Object> assembleProjectWorkerData(UpWorkerInfo upWorkerInfo,UpWorkerIn upWorkerIn,UpParams upParams){
        Map<String,Object> dataMap = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        UpGroupInfo upGroupInfo = upGroupInfoMapper.selectByPrimaryKey(upWorkerIn.getGroupId());
        UpCompanyInfo upCompanyInfo = upCompanyInfoMapper.selectByPrimaryKey(upGroupInfo.getCompanyId());
        UpProjectInfo upProjectInfo = upProjectInfoService.selectByPrimaryKey(upWorkerIn.getProjId());
        dataMap.put("PROJECTCODE",upProjectInfo.getReportCode()); //项目编码(上传项目返回)
        dataMap.put("SOCIALCREDITCODE",upCompanyInfo.getCorpCode()); //所在施工单位统一社会信用代码
        dataMap.put("TEAMSYSNO",upGroupInfo.getReportCode()); //班组编号(上传班组返回)
        dataMap.put("COMPANYCODE",upCompanyInfo.getCorpCode()); //工人所属参建单位统一社会信用编码
        dataMap.put("NAME",upWorkerInfo.getName()); //工人姓名
        dataMap.put("IDNUMBER", AESUtils.aesEncrypt(upWorkerInfo.getCardNo(),upParams.getSecret())); //证件号码。AES
        String workType = "worker_type";
        String workTypeApiCode = upWorkerIn.getWorkType();
        UpDictReport workDict = UpDictReport.defineOf(HENAN_CODE, workType, workTypeApiCode);
        workDict = upDictReportService.getDictReportByApiCode(workDict);
        if(workDict!=null&&!StringUtils.isEmpty(workDict.getCode())){
            dataMap.put("WORKCLASSES",workDict.getCode()); //从事岗位。数据字典
        }else{
            dataMap.put("WORKCLASSES","210401"); //从事岗位。数据字典
        }
        dataMap.put("BIRTHDAY",sdf.format(upWorkerInfo.getBirthday())); //生日yyyy-MM-dd
        dataMap.put("STARTTIME",sdf.format(upWorkerInfo.getCreateDate())); //进场时间yyyy-MM-dd
        dataMap.put("SIGNDATE",sdf.format(upWorkerIn.getContractSignDate())); //合同签订时间yyyy-MM-dd
        String contractType = "contract_type";
        String contractTypeApiCode = upWorkerIn.getContractLimitType();
        UpDictReport contractDict = UpDictReport.defineOf(HENAN_CODE, contractType, contractTypeApiCode);
        contractDict = upDictReportService.getDictReportByApiCode(contractDict);
        if(contractDict!=null&&!StringUtils.isEmpty(contractDict.getCode())){
            dataMap.put("TYPEID",contractDict.getCode()); //合同分类。数据字典
        }else{
            dataMap.put("TYPEID","88"); //合同分类。数据字典
        }
        if(upWorkerIn.getSalaryMoney()!=null){
            dataMap.put("WAGE",upWorkerIn.getSalaryMoney().toString());
        }else{
            dataMap.put("WAGE","200.00"); //工价, 整数部分最多9位数字,小数部分最多2位数字
        }

        String payWay = "salary_way";
        String payWayApiCode = upWorkerIn.getWorkType();
        UpDictReport payDict = UpDictReport.defineOf(HENAN_CODE, payWay, payWayApiCode);
        payDict = upDictReportService.getDictReportByApiCode(payDict);
        if(payDict!=null&&!StringUtils.isEmpty(payDict.getCode())){
            dataMap.put("PAYCALCULATIONWAY",payDict.getCode()); //工资计算方式。数据字典  salaryWay
        }else{
            dataMap.put("PAYCALCULATIONWAY","43"); //工资计算方式。数据字典  salaryWay
        }
        dataMap.put("PAYDATE","15"); //每月发薪日（数字1到31）
        String educationWay = "education_type";
        String educationWayApiCode = upWorkerInfo.getEducationDegree();
        UpDictReport educationDict = UpDictReport.defineOf(HENAN_CODE, educationWay, educationWayApiCode);
        educationDict = upDictReportService.getDictReportByApiCode(educationDict);
        if(educationDict!=null&&!StringUtils.isEmpty(educationDict.getCode())){
            dataMap.put("POLITICALSTATUS",educationDict.getCode()); //文化程度
        }else{
            dataMap.put("EDUCATIONLEVELS","120"); //文化程度
        }
        String politicalWay = "political_type";
        String politicalWayApiCode = upWorkerInfo.getPolitical();
        UpDictReport politicalDict = UpDictReport.defineOf(HENAN_CODE, politicalWay, politicalWayApiCode);
        politicalDict = upDictReportService.getDictReportByApiCode(politicalDict);
        if(politicalDict!=null&&!StringUtils.isEmpty(politicalDict.getCode())){
            dataMap.put("POLITICALSTATUS",politicalDict.getCode()); //政治面貌。数据字典
        }else{
            dataMap.put("POLITICALSTATUS","117"); //政治面貌。数据字典
        }
        dataMap.put("ADDRESS",upWorkerInfo.getCurrentAddress()); //住址
        String nation = "nation";
        String nationApiCode = upWorkerInfo.getNation();
        UpDictReport nationDict = UpDictReport.defineOf("330700", nation, nationApiCode);
        nationDict = upDictReportService.getDictReportByApiCode(nationDict);
        dataMap.put("NATION", nationDict.getCode()); //民族。此处参数的值传中文,附2
        dataMap.put("PHONE",upWorkerInfo.getMobile()); //联系电话
        UpFileExample fileExample = new UpFileExample();
        fileExample.createCriteria().andTableNameEqualTo(ReportConstant.UP_WORKER_INFO).andTableIdEqualTo(upWorkerInfo.getId());
        List<UpFile> upFiles = upFileMapper.selectByExample(fileExample);
        for (UpFile upFile : upFiles) {
            // 身份证头像
            if ("3".equals(upFile.getTableModule())) {
                String encodeImage = ImageUtils.encodePicture(upFile.getFilePath());

                dataMap.put("PIC", "data:image/jpeg;base64," + encodeImage);
            } else if ("4".equals(upFile.getTableModule())) {
                // 身份证正面
                String encodeImage = ImageUtils.encodePicture(upFile.getFilePath());
                dataMap.put("PICFRONT", "data:image/jpeg;base64," + encodeImage);
            } else if ("5".equals(upFile.getTableModule())) {
                // 身份证反面
                String encodeImage = ImageUtils.encodePicture(upFile.getFilePath());
                dataMap.put("PICBACK", "data:image/jpeg;base64," + encodeImage);
            }
        }
        return dataMap;
    }
    /***
     * 组装上报班组
     * @param upGroupInfo
     * @return
     */
    public Map<String,Object> assembleGroupData(UpGroupInfo upGroupInfo){
        UpCompanyInfo companyInfo =  upCompanyInfoMapper.selectByPrimaryKey(upGroupInfo.getCompanyId());
        UpCompanyIn upCompanyIn = upCompanyInMapper.selectByCompanyId(companyInfo.getId());
        UpProjectInfo upProjectInfo =  upProjectInfoService.selectByPrimaryKey(upCompanyIn.getProjId());
        Map<String,Object> groupData = new HashMap<>();
        //判断是新增还是更新(查询上报记录表)
        if(!StringUtils.isEmpty(upGroupInfo.getReportCode())){
            groupData.put("TEAMSYSNO",upCompanyIn.getReportCode());//项目名称
        }
        groupData.put("PROJECTCODE",upProjectInfo.getReportCode());
        groupData.put("SOCIALCREDITCODE",companyInfo.getCorpCode());
        groupData.put("MONITORNAME",upGroupInfo.getGroupName());
        return groupData;
    }

    /**
     * 组装上报合同数据
     * @param upCompanyInfo
     * @return
     */
    public Map<String,Object> assembleContractorData(UpCompanyInfo upCompanyInfo,UpCompanyIn upCompanyIn,UpProjectInfo upProjectInfo){
        Map<String,Object> contractorData = new HashMap<>();
        contractorData.put("SOCIALCREDITCODE",upCompanyInfo.getCorpCode());
        contractorData.put("NAME",upCompanyInfo.getName());
        contractorData.put("PROJECTCODE",upProjectInfo.getReportCode());
        contractorData.put("REGISTEREDAREA",upCompanyInfo.getAreaCode());
        String workType = "build_type";
        String workTypeApiCode = upCompanyIn.getBuildType();
        UpDictReport workDict = UpDictReport.defineOf(HENAN_CODE, workType, workTypeApiCode);
        contractorData.put("BUILDERTYPE", workDict.getCode());
        return contractorData;
    }

    /**
     * 组装上报企业数据
     * @param upCompanyInfo
     * @param upCompanyIn
     * @return
     */
    public Map<String,Object> assembleCompanyData(UpCompanyInfo upCompanyInfo,UpCompanyIn upCompanyIn,UpProjectInfo upProjectInfo){
        Map<String,Object> upCompanyData = new HashMap<>();
        upCompanyData.put("SOCIALCREDITCODE",upCompanyInfo.getCorpCode());//企业统一社会信用代码或者组织机构代码
        upCompanyData.put("NAME",upCompanyInfo.getName());//企业名称
        upCompanyData.put("PROJECTCODE",upProjectInfo.getReportCode());//项目编码(上传项目返回)
        upCompanyData.put("REGISTEREDAREA",upCompanyInfo.getAreaCode());//企业注册地区编码。全国行政区划code
        upCompanyData.put("BUILDERTYPE","97");//承建类型。数据字典
        upCompanyData.put("COMPANYTYPE","2");
        upCompanyData.put("PROJECTMANAGER",upCompanyIn.getLabourName());
        upCompanyData.put("PROJECTMANAGERPHONE",upCompanyIn.getLabourPhone());
        return upCompanyData;
    }

    /**
     * 组装上报项目数据
     * @param upProjectInfo
     * @return
     */
    public Map<String,Object> assembleProjectData(UpProjectInfo upProjectInfo){
        Map<String,Object> upProjectData = new HashMap<>();
        //判断是新增还是更新(查询上报记录表)
        if(!StringUtils.isEmpty(upProjectInfo.getReportCode())){
            upProjectData.put("PROJECTCODE",upProjectInfo.getReportCode());//项目名称
        }

        upProjectData.put("NAME",upProjectInfo.getName());//项目名称
        upProjectData.put("BUILDERLICENSENUM",upProjectInfo.getSafetyNo());//施工许可证号safetyNo
        upProjectData.put("MONEYSOURCE","2");//资金来源。数据字典
        upProjectData.put("PROJECTTYPE","14");//项目类型。数据字典
        upProjectData.put("GCPROCESS","1");//工程进度。1：在建，2：完工，3：停工
        upProjectData.put("DISTRICTCCODE", upProjectInfo.getAreaCode());//项目所属区域。全国行政区划code
        return upProjectData;
    }

    /**
     * 处理河南上报查询结果
     * @param
     * @return
     */
/*    @Override
    public String dealWithFindResult(HeNanMessage heNanMessage) {
        JSONObject json = null;
        switch (heNanMessage.getReportType()){
            //上报项目
            case HeNanConfig.ADD_PROJECT_METHOD:
                json = HeNanHttpUtils.findResult(heNanMessage.getRequestCode());
                if(HeNanConfig.SUCCESS_CODE==json.getJSONObject("data").getJSONObject("DEALRESULT").getInteger("code")){
                    String responseCode = json.getJSONObject("data").getJSONObject("DEALRESULT").getJSONObject("data").getString("PROJECTCODE");
                    UpProjectInfo record = new UpProjectInfo();
                    record.setId(Integer.parseInt(heNanMessage.getDataId()));
                    record.setReportCode(responseCode);
                    upProjectInfoService.updateByPrimaryKey(record);
                }
                break;
            //上报企业
            case HeNanConfig.ADD_COMPANY_METHOD:
                json = HeNanHttpUtils.findResult(heNanMessage.getRequestCode());
                break;
            //上报参建单位
            case HeNanConfig.ADD_CONTRACTOR_METHOD:
                json = HeNanHttpUtils.findResult(heNanMessage.getRequestCode());
                break;
            //上报班组
            case HeNanConfig.ADD_GROUP_METHOD:
                json = HeNanHttpUtils.findResult(heNanMessage.getRequestCode());
                if(HeNanConfig.SUCCESS_CODE==json.getJSONObject("data").getJSONObject("DEALRESULT").getInteger("code")){
                    String responseCode = json.getJSONObject("data").getJSONObject("DEALRESULT").getJSONObject("data").getString("TEAMSYSNO");
                    UpGroupInfo groupInfo = new UpGroupInfo();
                    groupInfo.setId(Integer.parseInt(heNanMessage.getDataId()));
                    groupInfo.setReportCode(responseCode);
                    upGroupInfoMapper.updateByPrimaryKeySelective(groupInfo);
                }
                break;
            //上报劳工合同
            case HeNanConfig.ADD_WORKER_METHOD:
                json = HeNanHttpUtils.findResult(heNanMessage.getRequestCode());
                if(HeNanConfig.SUCCESS_CODE==json.getJSONObject("data").getJSONObject("DEALRESULT").getInteger("code")){
                    log.info("===========================》上报劳工成功！！！");
                    String responseCode = json.getJSONObject("data").getJSONObject("DEALRESULT").getJSONObject("data").getString("CONTRACTTYPE");
                    upWorkerInMapper.updateResponseCodeByWorkerId(Integer.parseInt(heNanMessage.getDataId()),responseCode);
                }
                break;
            //上报考勤
            case HeNanConfig.ADD_WORKER_ATTENDANCE_METHOD:
                json = HeNanHttpUtils.findResult("2020072210093198534998221759");
                if(HeNanConfig.SUCCESS_CODE==json.getJSONObject("data").getJSONObject("DEALRESULT").getInteger("code")){
                    log.info("===========================》上报劳工考勤成功！！！");
                    UpWorkerSignlogInfo upWorkerSignlogInfo = new UpWorkerSignlogInfo();
                    upWorkerSignlogInfo.setId(Integer.parseInt(heNanMessage.getDataId()));
                    upWorkerSignlogInfo.setReportFlag("1");
                    upWorkerSignlogInfoMapper.updateByPrimaryKeySelective(upWorkerSignlogInfo);
                }
                break;
        }
        return null;
    }*/

    public void reportFail(HeNanResult heNanResult,String message){
        heNanResult.setCode("1");
        heNanResult.setMessage(message);
    };
}
