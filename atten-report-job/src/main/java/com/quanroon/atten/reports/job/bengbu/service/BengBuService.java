package com.quanroon.atten.reports.job.bengbu.service;

import com.alibaba.fastjson.JSONObject;
import com.quanroon.atten.commons.utils.StringUtils;
import com.quanroon.atten.reports.common.ReportType;
import com.quanroon.atten.reports.dao.*;
import com.quanroon.atten.reports.entity.*;
import com.quanroon.atten.reports.entity.dto.*;
import com.quanroon.atten.reports.entity.example.UpCompanyInExample;
import com.quanroon.atten.reports.entity.example.UpWorkerInExample;
import com.quanroon.atten.reports.job.bengbu.config.BengBuCongig;
import com.quanroon.atten.reports.job.bengbu.entity.BengBuResult;
import com.quanroon.atten.reports.job.bengbu.utils.BengBuDict;
import com.quanroon.atten.reports.job.bengbu.utils.BengBuHttpClient;
import com.quanroon.atten.reports.job.bengbu.utils.PackageUtil;
import com.quanroon.atten.reports.report.annotation.City;
import com.quanroon.atten.reports.report.annotation.ReportMethod;
import com.quanroon.atten.reports.report.constant.ReportCityCode;
import com.quanroon.atten.reports.report.entity.ReportResult;
import com.quanroon.atten.reports.report.entity.ReportService;
import com.quanroon.atten.reports.service.UpProjectInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author 罗森林
 * @Auther:
 * @Date: 2020-08-27 15:10
 * @Description:
 */
@City(ReportCityCode.BENGBU)
@Slf4j
public class BengBuService implements ReportService {
    @Autowired
    private UpCompanyInMapper upCompanyInMapper;

    @Autowired
    private UpParamsMapper upParamsMapper;

    @Autowired
    private UpCompanyInfoMapper upCompanyInfoMapper;

    @Autowired
    private UpProjectInfoService upProjectInfoService;

    @Autowired
    private UpDictReportMapper upDictReportMapper;

    @Autowired
    private UpGroupInfoMapper upGroupInfoMapper;
    @Autowired
    private UpWorkerInfoMapper upWorkerInfoMapper;

    @Autowired
    private UpWorkerInMapper upWorkerInMapper;

    @Autowired
    private UpWorkerSignlogInfoMapper upWorkerSignlogInfoMapper;

    @Autowired
    private UpSalaryInfoMapper upSalaryInfoMapper;

    @Autowired
    private UpPayrollDetailInfoMapper upPayrollDetailInfoMapper;

    @Autowired
    private UpPayrollInfoMapper upPayrollInfoMapper;

    @Autowired
    private UpWorkerSignlogInfoMapper signlogInfoMapper;

    @Autowired
    private UpDictInfoMapper upDictInfoMapper;

    @Value("${bengbu.url}")
    public String URL;

    /**
     * 上报和创建项目
     * @auther: 罗森林
     * @param:
     * @return:
     * @date: 2020-08-28 8:55
     */
    @ReportMethod({ReportType.proj_report,ReportType.proj_update})
    public ReportResult reportProj(ReportMessage reportMessage) {
        BengBuResult buResult = new BengBuResult();
        Integer id = Integer.valueOf(reportMessage.getDataId());
        //获取项目信息
        UpBengbuProjDTO projDTO =  upProjectInfoService.selectBengbuProjInfo(id);
        projDTO.setReportCode("340300000000000087");
        //查询配置
        UpParams upParams = upParamsMapper.selectByProjId(id);
        StringBuffer data = PackageUtil.ProjectPackage(projDTO,upParams);
        log.info(">>项目数据="+data);
        String result = BengBuHttpClient.PostRequest(URL+BengBuCongig.UPLOAD_PROJ_ADD, data.toString(),BengBuCongig.REQUEST_QUERY);
        if(!StringUtils.isEmpty(result)){
            log.info(result);
            JSONObject  json = JSONObject.parseObject(result);
            log.info("================>"+json);
            if(json.getString("message").equals(BengBuDict.MESSAGE)){
                log.info("===========================》蚌埠上报项目成功！！！");
                JSONObject  dataJson = json.getJSONObject("data");
                UpProjectInfo info = new UpProjectInfo();
                info.setId(projDTO.getId());
                info.setReportCode(dataJson.getString("projectId"));
                upProjectInfoService.updateByPrimaryKey(info);
                buResult.setCode(BengBuCongig.RESULT_SUCCESS_CODE);
            }else if (json.getString("message").equals("该用户已存在项目")){
                if(StringUtils.isEmpty(projDTO.getReportCode())){
                    buResult.setMessage(json.getString("message"));
                    buResult.setCode(BengBuCongig.RESULT_FAIL_CODE);
                }else{
                    //测试环境 没办法
                    data = data.append("&projectId="+projDTO.getReportCode());
                    String result1 = BengBuHttpClient.PostRequest(URL+BengBuCongig.UPLOAD_PROJ_EDIT, data.toString(),BengBuCongig.REQUEST_QUERY);
                    if(!StringUtils.isEmpty(result1)){
                        JSONObject  object = JSONObject.parseObject(result1);
                        log.info("================>"+object);
                        if(object.getString("message").equals(BengBuDict.MESSAGE)){
                            JSONObject  dataObjectJson = object.getJSONObject("data");
                            UpProjectInfo info = new UpProjectInfo();
                            info.setId(projDTO.getId());
                            info.setReportCode(dataObjectJson.getString("projectId"));
                            upProjectInfoService.updateByPrimaryKey(info);
                            buResult.setCode(BengBuCongig.RESULT_SUCCESS_CODE);
                            buResult.setMessage(object.getString("message"));
                        }else{
                            buResult.setCode(BengBuCongig.RESULT_FAIL_CODE);
                            buResult.setMessage(object.getString("message"));
                        }
                    }
                }
            }else{
                buResult.setCode(BengBuCongig.RESULT_FAIL_CODE);
                buResult.setMessage(json.getString("message"));
            }
        }else{
            buResult.setCode(BengBuCongig.RESULT_FAIL_CODE);
            buResult.setMessage("没有上报结果");
        }
        return buResult;
    }


    /**
     * 上报参建单位
     * @auther: 罗森林
     * @param:
     * @return:
     * @date: 2020-08-27 15:32
     */
    @ReportMethod({ReportType.company_enter})
    public ReportResult reportCompany(ReportMessage reportMessage){
        BengBuResult buResult = new BengBuResult();
        Integer id = Integer.valueOf(reportMessage.getDataId());
        try {
            //查询是否上报参建单位
            UpCompanyIn upCompanyIn = upCompanyInMapper.selectByPrimaryKey(id);
            //获取参建单位信息
            UpCompanyInfo upCompanyInfo = upCompanyInfoMapper.selectByPrimaryKey(upCompanyIn.getCompanyId());

            Integer projId = upCompanyInfo.getProjId();
            //获取项目信息
            UpBengbuProjDTO projDTO =  upProjectInfoService.selectBengbuProjInfo(projId);
            //参建单位类型匹配
            UpDictReport buildType = upDictReportMapper.selectDict(ReportCityCode.BENGBU.code(), BengBuDict.REPORT_TYPE,
                    BengBuDict.COMPANY_BUILD_TYPE, upCompanyIn.getBuildType());
            if (Objects.isNull(buildType)) {
                upCompanyIn.setBuildType(BengBuDict.OTHER_COMPANY_CODE);
            } else {
                upCompanyIn.setBuildType(buildType.getCode());
            }
            // 所属行业大类(企业类型) 国企，私企
            UpDictReport companyType = upDictReportMapper.selectDict(ReportCityCode.BENGBU.code(), BengBuDict.REPORT_TYPE,
                    BengBuDict.COMPANY_TYPE, upCompanyInfo.getType());
            if (Objects.isNull(companyType)) {
                upCompanyInfo.setType(BengBuDict.OTHER_INDUSTRY_CODE);
            } else {
                upCompanyInfo.setType(companyType.getCode());
            }
            //查询配置
            UpParams upParams = upParamsMapper.selectByProjId(projId);
            //工资专户信息
            UpBengbuSalaryInfoDTO  salaryDTO = upSalaryInfoMapper.selectCompanySalaryInfo(upCompanyInfo);
            String data = PackageUtil.CompanyPackage(projDTO,upCompanyIn,upCompanyInfo,salaryDTO,upParams);
            log.info(">>单位数据="+data);
            String result = BengBuHttpClient.PostRequest(URL+BengBuCongig.UPLOAD_COMPANY_ADD, data,BengBuCongig.REQUEST_BODY);
            if(!StringUtils.isEmpty(result)){
                    log.info(result);
                    JSONObject  json = JSONObject.parseObject(result);
                if(json.getString("message").equals(BengBuDict.MESSAGE)){
                    JSONObject  resultData = json.getJSONArray("data").getJSONObject(0);
                    UpCompanyInExample companyInExample = new UpCompanyInExample();
                    companyInExample.createCriteria().andIdEqualTo(upCompanyIn.getId());
                    UpCompanyIn in = new UpCompanyIn();
                    in.setId(upCompanyIn.getId());
                    in.setReportCode(resultData.getString("aaa001"));
                    upCompanyInMapper.updateByExampleSelective(in,companyInExample);
                    buResult.setCode(BengBuCongig.RESULT_SUCCESS_CODE);
                 }else if(json.getString("message").equals("该项目已存在总包单位")){
                    log.info(json.getString("message"));
                    buResult.setCode(BengBuCongig.RESULT_SUCCESS_CODE);
                } else{
                     log.info(json.getString("message"));
                     buResult.setCode(BengBuCongig.RESULT_FAIL_CODE);
                     buResult.setMessage(json.getString("message"));
                 }
             }else{
                buResult.setCode(BengBuCongig.RESULT_FAIL_CODE);
                buResult.setMessage("没有上报结果");
             }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return buResult;
    }

    /**
     * 上报班组
     * @auther: 罗森林
     * @param:
     * @return:
     * @date: 2020-08-28 14:52
     */
    @ReportMethod({ReportType.group_report,ReportType.group_update})
    public ReportResult reportGroup(ReportMessage reportMessage){
        BengBuResult buResult = new BengBuResult();
        Integer id = Integer.valueOf(reportMessage.getDataId());
        try {
            // 查询是否上报班组
            UpBengbuGroupDTO groupDTO =  upGroupInfoMapper.seletBengBuGroupInfo(id);
            //查询配置
            UpParams upParams = upParamsMapper.selectByProjId(groupDTO.getProjId());
            String data = PackageUtil.GroupPackage(groupDTO,upParams);
            log.info(">>>班组数据="+data);
            String result = BengBuHttpClient.PostRequest(URL+BengBuCongig.UPLOAD_GROUP_ADD, data,BengBuCongig.REQUEST_QUERY);
            if(!StringUtils.isEmpty(result)){
                JSONObject  json = JSONObject.parseObject(result);
                log.info("================>"+json);
                if(json.getString("message").equals(BengBuDict.MESSAGE)){
                    log.info("===========================》蚌埠上报班组成功！！！");
                    JSONObject  dataJson = json.getJSONObject("data");
                    UpGroupInfo upGroupInfo = new UpGroupInfo();
                    upGroupInfo.setId(id);
                    upGroupInfo.setReportCode(dataJson.getString("teamId"));
                    upGroupInfoMapper.updateByPrimaryKeySelective(upGroupInfo);
                    buResult.setCode(BengBuCongig.RESULT_SUCCESS_CODE);
                }else{
                    buResult.setCode(BengBuCongig.RESULT_FAIL_CODE);
                    buResult.setMessage(json.getString("message"));
                }
            }else{
                buResult.setCode(BengBuCongig.RESULT_FAIL_CODE);
                buResult.setMessage("没有上报结果");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buResult;
    }

    /**
     * 上报工资信息
     * @auther: 罗森林
     * @param:
     * @return:
     * @date: 2020-08-28 15:04
     */
    @ReportMethod({ReportType.payroll_detail})
    public ReportResult reportPayroll(ReportMessage reportMessage) {
        BengBuResult buResult = new BengBuResult();
        try {
            Integer id = Integer.valueOf(reportMessage.getDataId());
            UpBengbuPayRollDTO payRollDTO = upPayrollDetailInfoMapper.selectBengBuPayRollInfo(id);
            // 匹配银行
            UpDictReport salaryBank = upDictReportMapper.selectDict(ReportCityCode.BENGBU.code(), BengBuDict.REPORT_TYPE,
                    BengBuDict.DEPOSIT_BANK, payRollDTO.getSalaryBank());
            if (Objects.isNull(salaryBank)) {
                payRollDTO.setSalaryBank(BengBuDict.DEFAULT_DEPOSIT_BANK);
            } else {
                payRollDTO.setSalaryBank(salaryBank.getCode());
            }
            //查询配置
            UpParams upParams = upParamsMapper.selectByProjId(payRollDTO.getProjId());
            String data = PackageUtil.payrollPackage(payRollDTO,upParams);
            log.info(data);
            String result = BengBuHttpClient.PostRequest(URL+BengBuCongig.UPLOAD_SALARY_ADD, data,BengBuCongig.REQUEST_BODY);
            log.info("================>"+result);
            if(!StringUtils.isEmpty(result)){
                JSONObject  json = JSONObject.parseObject(result);
                if(json.getString("message").equals(BengBuDict.MESSAGE)) {
                    JSONObject  dataJson = json.getJSONArray("data").getJSONObject(0);
                    log.info("===========================》蚌埠上报劳工工资成功！！！");
                    buResult.setCode(BengBuCongig.RESULT_SUCCESS_CODE);
                    buResult.setMessage("工资上报成功");
                    UpPayrollInfo payrollInfo = new UpPayrollInfo();
                    payrollInfo.setId(payRollDTO.getPayId());
                    payrollInfo.setReportCode(dataJson.getString("id"));
                    upPayrollInfoMapper.updateByPrimaryKeySelective(payrollInfo);
                }else{
                    buResult.setCode(BengBuCongig.RESULT_FAIL_CODE);
                    buResult.setMessage(json.getString("message"));
                }
            }else{
                buResult.setCode(BengBuCongig.RESULT_FAIL_CODE);
                buResult.setMessage("没有上报结果");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buResult;
    }

    /**
     * 上报劳工花名册信息
     * @auther: 罗森林
     * @param:
     * @return:
     * @date: 2020-08-28 15:18
     */
    @ReportMethod({ReportType.worker_enter,ReportType.worker_leave})
    public ReportResult reportWorker(ReportMessage reportMessage) {
        BengBuResult buResult = new BengBuResult();
        try {
            Integer id = Integer.valueOf(reportMessage.getDataId());
            UpBengbuWorkerInDTO workerInDTO = upWorkerInfoMapper.selectBengBuWorkerInfo(id);
            // 学历匹配
            UpDictReport education = upDictReportMapper.selectDict(ReportCityCode.BENGBU.code(), BengBuDict.REPORT_TYPE,
                    BengBuDict.EDUCATION, workerInDTO.getEducationDegree());
            if (Objects.isNull(education)) {
                workerInDTO.setEducationDegree(BengBuDict.OTHER_INDUSTRY_CODE);
            } else {
                workerInDTO.setEducationDegree(education.getCode());
            }
            // 工种匹配
            UpDictReport workerType = upDictReportMapper.selectDict(ReportCityCode.BENGBU.code(), BengBuDict.REPORT_TYPE,
                    BengBuDict.WORKER_TYPE, workerInDTO.getWorkType());
            if (Objects.isNull(workerType)) {
                workerInDTO.setWorkType(BengBuDict.DEFAULT_WORKER_TYPE);
            } else {
                workerInDTO.setWorkType(workerType.getCode());
            }
            //查询配置
            UpParams upParams = upParamsMapper.selectByProjId(workerInDTO.getProjId());
            String data = PackageUtil.workerPackage(workerInDTO,upParams);
            log.info(data);
            String result = BengBuHttpClient.PostRequest(URL+BengBuCongig.UPLOAD_WORKER_ADD, data,BengBuCongig.REQUEST_BODY);
            if(!StringUtils.isEmpty(result)){
                log.info(result);
                JSONObject  json = JSONObject.parseObject(result);
                if(json.getString("message").equals(BengBuDict.MESSAGE)) {
                    log.info("===========================》蚌埠上报劳工花名册信息成功！！！");
                    JSONObject dataJson = json.getJSONArray("data").getJSONObject(0);
                    buResult.setCode(BengBuCongig.RESULT_SUCCESS_CODE);
                    UpWorkerIn  info = new UpWorkerIn();
                    info.setId(id);
                    info.setReportCode(dataJson.getString("id"));
                    UpWorkerInExample workerInExample = new UpWorkerInExample();
                    workerInExample.createCriteria().andWorkerIdEqualTo(workerInDTO.getWorkerId());
                    upWorkerInMapper.updateByExampleSelective(info,workerInExample);
                    if(BengBuCongig.PROJ_ENTER.equals(workerInDTO.getStatus())){
                        Map<String,String> contractMap = this.reportWorkerContract(workerInDTO,upParams);
                        if(!StringUtils.isEmpty(contractMap.get(BengBuCongig.RESULT_FAIL_CODE))){
                            buResult.setMessage(contractMap.get(BengBuCongig.RESULT_FAIL_CODE));
                            buResult.setCode(BengBuCongig.RESULT_FAIL_CODE);
                            return buResult;
                        }
                    }
                }else{
                    buResult.setCode(BengBuCongig.RESULT_FAIL_CODE);
                    buResult.setMessage(json.getString("message"));
                }
            }else{
                buResult.setCode(BengBuCongig.RESULT_FAIL_CODE);
                buResult.setMessage("没有上报结果");
            }
        } catch (Exception e) {
            e.printStackTrace();
            buResult.setCode(BengBuCongig.RESULT_FAIL_CODE);
        }
        log.info(buResult.toString());
        return buResult;
    }

    /**
     * 上报合同信息
     * @auther: 罗森林
     * @param:
     * @return:
     * @date: 2020-08-28 15:25
     */
    public Map<String,String> reportWorkerContract(UpBengbuWorkerInDTO workerInDTO,UpParams upParams) {
        Map<String,String>contractMap = new HashMap<>();
        try {
            // 计薪方式匹配
            UpDictReport salaryWay = upDictReportMapper.selectDict(ReportCityCode.BENGBU.code(), BengBuDict.REPORT_TYPE,
                    BengBuDict.SALARY_WAY, workerInDTO.getSalaryWay());
            if (null == salaryWay) {
                workerInDTO.setSalaryWay(BengBuDict.SALARY_CODE);
            } else {
                workerInDTO.setSalaryWay(salaryWay.getCode());
            }
            String data = PackageUtil.contractPackage(workerInDTO,upParams);
            log.info(data);
            String result = BengBuHttpClient.PostRequest(URL+BengBuCongig.UPLOAD_CONTRACT_ADD, data,BengBuCongig.REQUEST_BODY);
            if(!StringUtils.isEmpty(result)){
                log.info("================>"+result);
                JSONObject  json = JSONObject.parseObject(result);
                if(json.getString("message").equals(BengBuDict.MESSAGE)) {
                    log.info("===========================》蚌埠上报劳工合同成功！！！");
                }else{
                    contractMap.put(BengBuCongig.RESULT_FAIL_CODE,json.getString("message"));
                }
            }else{
                contractMap.put(BengBuCongig.RESULT_FAIL_CODE,"没有上报结果");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contractMap;
    }


    /**
     * 上报考勤信息
     * @auther: 罗森林
     * @param:
     * @return:
     * @date: 2020-08-28 15:25
     */
    @ReportMethod({ReportType.worker_signlog})
    public ReportResult reportSignLog(ReportMessage reportMessage) {
        BengBuResult buResult = new BengBuResult();
        try {
            Integer id = Integer.valueOf(reportMessage.getDataId());
            UpBengbuSignlogDTO signlogDTO = upWorkerSignlogInfoMapper.selectBengbuSignLogInfo(id);
            //查询配置
            UpParams upParams = upParamsMapper.selectByProjId(signlogDTO.getProjId());
            //进出场匹配
            UpDictReport direction = upDictReportMapper.selectDict(ReportCityCode.BENGBU.code(), BengBuDict.REPORT_TYPE,
                    BengBuDict.DIRECTION_TYPE, signlogDTO.getDirection());
            if (Objects.isNull(direction)) {
                signlogDTO.setDirection(BengBuDict.DIREC_DEFAULT);
            } else {
                signlogDTO.setDirection(direction.getCode());
            }
            String data = PackageUtil.signLogPackage(signlogDTO,upParams);
            String result = BengBuHttpClient.PostRequest(URL+BengBuCongig.UPLOAD_SIGN_ADD, data,BengBuCongig.REQUEST_BODY);
            if(!StringUtils.isEmpty(result)){
                JSONObject  json = JSONObject.parseObject(result);
                if(json.getString("message").equals(BengBuDict.MESSAGE)) {
                    log.info("===========================》蚌埠上报考勤数据成功！！！");
                    buResult.setCode(BengBuCongig.RESULT_SUCCESS_CODE);
                    buResult.setMessage("考勤上报成功");
                    UpWorkerSignlogInfo signLog = new UpWorkerSignlogInfo();
                    signLog.setId(id);
                    signLog.setReportFlag(BengBuCongig.RESULT_SUCCESS_CODE);
                    signlogInfoMapper.updateByPrimaryKeySelective(signLog);
                }else{
                    buResult.setCode(BengBuCongig.RESULT_FAIL_CODE);
                    buResult.setMessage(json.getString("message"));
                }
            }else {
                     buResult.setCode(BengBuCongig.RESULT_FAIL_CODE);
                    buResult.setMessage("没有上报结果");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buResult;
    }
}
