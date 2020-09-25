/*
 * Copyright (c) 2020, QUANRONG TECHNOLOGY LTD. All rights reserved.
 */
package com.quanroon.atten.reports.job.jinhua.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.quanroon.atten.reports.common.ReportConstant;
import com.quanroon.atten.reports.common.ReportType;
import com.quanroon.atten.reports.entity.example.UpGroupInfoExample;
import com.quanroon.atten.reports.entity.example.UpWorkerInExample;
import com.quanroon.atten.reports.entity.example.UpWorkerSignlogInfoExample;
import com.quanroon.atten.reports.job.jinhua.config.*;
import com.quanroon.atten.reports.dao.*;
import com.quanroon.atten.reports.entity.*;
import com.quanroon.atten.reports.job.jinhua.entity.JinHuaResult;
import com.quanroon.atten.reports.job.jinhua.utils.JinHuaException;
import com.quanroon.atten.reports.job.jinhua.utils.ReportHttpUtils;
import com.quanroon.atten.reports.report.annotation.City;
import com.quanroon.atten.reports.report.annotation.ReportMethod;
import com.quanroon.atten.reports.report.constant.ReportCityCode;
import com.quanroon.atten.reports.report.entity.ReportResult;
import com.quanroon.atten.reports.report.entity.ReportService;
import com.quanroon.atten.reports.service.UpDictReportService;
import com.quanroon.atten.reports.service.UpRecordService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Auther: raojun
 * @Date: 2020/6/29 09:37
 * @Description:
 */
@City(ReportCityCode.JINHUA)
@Slf4j
public class JinHuaService implements ReportService {

    @Autowired private UpWorkerInfoMapper upWorkerInfoMapper;
    @Autowired private UpWorkerInMapper upWorkerInMapper;
    @Autowired private UpParamsMapper upParamsMapper;
    @Autowired private UpGroupInfoMapper upGroupInfoMapper;
    @Autowired private UpRecordMapper upRecordMapper;
    @Autowired private UpWorkerSignlogInfoMapper upWorkerSignlogInfoMapper;
    @Autowired private UpFileMapper upFileMapper;
    @Autowired private UpRecordService upRecordServiceImpl;
    @Autowired private UpDictReportService upDictReportServiceImpl;

    @Autowired
    private JinHuaAddress jinHuaAddress;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHssmm");
    private SimpleDateFormat sdfYMD = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat sdfYMDHSM = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
    private SimpleDateFormat sdfymd = new SimpleDateFormat("yyyy年MM月dd日");

    private static final String JINHUA_SUCCESS = "0";   //结果成功
    private static final String JINHUA_FAILURE = "1";   //结果失败

    @ReportMethod({ReportType.group_report, ReportType.group_update})
    public ReportResult reportGroup(ReportMessage reportMessage){
        JinHuaResult jinHuaResult = new JinHuaResult();
        String resultCode = "";
        String resultMessage = "";
        try {
            Integer id = Integer.valueOf(reportMessage.getDataId());
            String requestCode = reportMessage.getRequestCode();
            String reportType = reportMessage.getReportType().toString();

            //查询班组
            UpGroupInfo upGroupInfo = upGroupInfoMapper.selectByPrimaryKey(id);
            Integer projId = upGroupInfo.getProjId();
            //查询配置
            UpParams upParams = upParamsMapper.selectByProjId(projId);
            String uploadType = JinHuaConfig.UPLOAD_ADD;
            String method = JinHuaConfig.ADD_GROUP_METHOD;

            Date now = new Date();
            String currentTime = sdf.format(now);
            if (!StringUtils.isEmpty(upGroupInfo.getReportCode())) {
                uploadType = JinHuaConfig.UPLOAD_UPDATE;
                method = JinHuaConfig.UPDATE_GROUP_METHOD;
            }
            //封装上报数据
            String data = getGroupInfo(upGroupInfo, uploadType);
            String urlParams = getUrlParams(data, method, upParams, (int)((Math.random()*9+1)*100000), currentTime);
            //上报班组
            String url = jinHuaAddress.UPLOAD_URL;
            JSONObject jsonObject = ReportHttpUtils.httpPost(url, null, null, urlParams, null, JinHuaConfig.CONTENT_TYPE);
            String code = jsonObject.getString("code");

            if (!StringUtils.isEmpty(code)) {
                if ("0".equals(code)) {     // 请求成功
                    if (JinHuaConfig.UPLOAD_ADD.equals(uploadType)) {
                        //更新唯一码
                        String requestSerialCode = jsonObject.getJSONObject("data").getString("requestSerialCode");
                        JSONObject jo = new JSONObject();
                        jo.put("requestSerialCode", requestSerialCode);

                        //循环去查询结果接口10秒一次
                        String teamSysNo = "";
                        for (int i = 0; i < 5; i++) {
                            Thread.sleep(5000);

                            //查询班组唯一码
                            String queryUrlParams = getUrlParams(jo.toJSONString(), JinHuaConfig.QUERY_RESULT, upParams, (int)((Math.random()*9+1)*100000), currentTime);
                            JSONObject object = ReportHttpUtils.httpPost(url, null, null, queryUrlParams, null, JinHuaConfig.CONTENT_TYPE);
                            String httpResultCode = object.getString("code");
                            if ("0".equals(httpResultCode)) {
                                String httpStatus = object.getJSONObject("data").getString("status");
                                if ("20".equals(httpStatus)) {//处理成功
                                    teamSysNo = object.getJSONObject("data").getJSONObject("result").getString("teamSysNo");
                                } else {
                                    resultMessage = object.getJSONObject("data").getString("result");
                                }
                            }
                            if (!StringUtils.isEmpty(teamSysNo) || !StringUtils.isEmpty(resultMessage)) {
                                break;
                            }
                        }

                        if (StringUtils.isEmpty(teamSysNo)) {   //上报失败，没有上报结果
                            resultCode = JINHUA_FAILURE;
                            if (StringUtils.isEmpty(resultMessage)) {
                                resultMessage = "住建局班组上报查询接口请求失败,没有上报结果";
                            }
                        } else {
                            //按照ID去更新
                            UpGroupInfoExample upGroupInfoExample = new UpGroupInfoExample();
                            upGroupInfoExample.createCriteria().andIdEqualTo(upGroupInfo.getId());
                            upGroupInfo.setReportCode(teamSysNo);
                            upGroupInfoMapper.updateByExample(upGroupInfo, upGroupInfoExample);

                            resultCode = JINHUA_SUCCESS;
                            resultMessage = "住建局班组上报接口请求成功";
                        }
                    } else {
                        resultCode = JINHUA_SUCCESS;
                        resultMessage = "住建局班组上报接口请求成功";
                    }
                } else {//请求失败
                    String errorMessage = JinHuaConfig.httpStatusMap.get(code);
                    resultCode = JINHUA_FAILURE;
                    resultMessage = "住建局班组上报接口请求失败," + errorMessage;
                }
            } else {//请求失败,打印日志
                resultCode = JINHUA_FAILURE;
                resultMessage = "住建局班组上报接口请求失败";
            }
        } catch (Exception e) {
            resultCode = JINHUA_FAILURE;
            resultMessage = "住建局班组上报异常,请联系技术";
            log.error("JinHuaService reportGroup error" , e);
            e.printStackTrace();
        }

        jinHuaResult.setCode(resultCode);
        jinHuaResult.setMessage(resultMessage);
        return jinHuaResult;
    }

    /**
     * 上报劳工
     * @param reportMessage
     * @return
     */
    @ReportMethod({ReportType.worker_enter})
    public JinHuaResult reportWorker(ReportMessage reportMessage){
        JinHuaResult jinHuaResult = new JinHuaResult();
        String resultCode = "";
        String resultMessage = "";
        try {
            Integer id = Integer.valueOf(reportMessage.getDataId());
            String requestCode = reportMessage.getRequestCode();
            String reportType = reportMessage.getReportType().toString();

            //查询劳工
            UpWorkerIn upWorkerIn = upWorkerInMapper.selectByPrimaryKey(id);
            Integer workerId = upWorkerIn.getWorkerId();
            UpWorkerInfo upWorkerInfo = upWorkerInfoMapper.selectByPrimaryKey(workerId);
            Integer projId = upWorkerIn.getProjId();
            //查询配置
            UpParams upParams = upParamsMapper.selectByProjId(projId);
            String workerReportCode = upWorkerIn.getReportCode();
            String uploadType = JinHuaConfig.UPLOAD_ADD;
            if (!StringUtils.isEmpty(workerReportCode)) {
                uploadType = JinHuaConfig.UPLOAD_UPDATE;
            }

            //查询当前劳工班组是否上报
            Integer groupId = upWorkerIn.getGroupId();
            //查询班组
            UpGroupInfo upGroupInfo = upGroupInfoMapper.selectByPrimaryKey(groupId);
            String reportCode = upGroupInfo.getReportCode();
            if (!StringUtils.isEmpty(reportCode)) {//班组已经上报，并且成功，有唯一码返回
                //查询附件
                List<UpFile> upFileList = upFileMapper.selectByTableNameAndTableId("up_worker_info", workerId);
                //上报劳工
                //封装上报数据
                String method = JinHuaConfig.ADD_WORKER_METHOD;
                String data = "";
                String errorMsg = "";
                try {
                    data = getWorkerInfo(upWorkerInfo, upWorkerIn, upGroupInfo, upFileList, upParams);
                } catch (Exception e) {
                    errorMsg = e.getMessage();
                }
                if (!StringUtils.isEmpty(errorMsg)) {
                    if (-1 != errorMsg.indexOf("系统找不到指定的路径")) {
                        errorMsg = "图片不存在";
                    }
                    resultCode = JINHUA_FAILURE;
                    resultMessage = errorMsg;
                    jinHuaResult.setCode(resultCode);
                    jinHuaResult.setMessage(resultMessage);
                    return jinHuaResult;
                }
                int nonce = (int)((Math.random()*9+1)*100000);
                String currentTime = sdf.format(new Date());
                String urlParams = getUrlParams(data, method, upParams, nonce, currentTime);
                String url = jinHuaAddress.UPLOAD_URL;
                String type = String.valueOf(ReportConstant.TYPE_WORKER_REPORT);
                JSONObject jsonObject = ReportHttpUtils.httpPost(url, null, null, urlParams, null, JinHuaConfig.CONTENT_TYPE);
                String code = jsonObject.getString("code");
                if (!StringUtils.isEmpty(code)) {
                    if ("0".equals(code)) {//请求成功
                        //更新唯一码
                        if (JinHuaConfig.UPLOAD_ADD.equals(uploadType)) {
                            //更新唯一码
                            String requestSerialCode = jsonObject.getJSONObject("data").getString("requestSerialCode");
                            JSONObject jo = new JSONObject();
                            jo.put("requestSerialCode", requestSerialCode);


                            //循环去查询结果接口5秒一次
                            boolean flag = false;
                            for (int i = 0; i < 10; i++) {
                                Thread.sleep(5000);

                                //查询班组唯一码
                                String queryUrlParams = getUrlParams(jo.toJSONString(), JinHuaConfig.QUERY_RESULT, upParams, (int)((Math.random()*9+1)*100000), currentTime);
                                JSONObject object = ReportHttpUtils.httpPost(url, null, null, queryUrlParams, null, JinHuaConfig.CONTENT_TYPE);
                                String httpResultCode = object.getString("code");
                                if ("0".equals(httpResultCode)) {
                                    String httpStatus = object.getJSONObject("data").getString("status");
                                    if ("20".equals(httpStatus)) {//上报处理成功
                                        flag = true;
                                        break;
                                    } else {
                                        resultMessage = object.getJSONObject("data").getString("result");
                                    }
                                }
                            }

                            if (!flag) {   //上报失败，没有上报结果
                                resultCode = JINHUA_FAILURE;
                                if (StringUtils.isEmpty(resultMessage)) {
                                    resultMessage = "住建局劳工上报查询接口请求失败,没有上报结果";
                                }
                            } else {
                                //按照ID去更新
                                upWorkerIn.setReportCode(requestSerialCode);
                                UpWorkerInExample upWorkerInExample = new UpWorkerInExample();
                                upWorkerInExample.createCriteria().andWorkerIdEqualTo(upWorkerIn.getWorkerId());
                                upWorkerInMapper.updateByExample(upWorkerIn, upWorkerInExample);

                                resultCode = JINHUA_SUCCESS;
                                resultMessage = "住建局劳工上报接口请求成功";
                            }
                        } else {
                            resultCode = JINHUA_SUCCESS;
                            resultMessage = "住建局劳工上报接口请求成功";
                        }
                    } else {//请求失败
                        String errorMessage = JinHuaConfig.httpStatusMap.get(code);
                        resultCode = JINHUA_FAILURE;
                        resultMessage = "住建局劳工上报接口请求失败," + errorMessage;
                    }
                } else {//请求失败
                    resultCode = JINHUA_FAILURE;
                    resultMessage = "住建局劳工上报接口请求失败";
                }
            }
        } catch (Exception e) {
            resultCode = JINHUA_FAILURE;
            resultMessage = "住建局劳工上报异常,请联系技术";
            log.error("JinHuaService reportWorker error" , e);
        }

        jinHuaResult.setCode(resultCode);
        jinHuaResult.setMessage(resultMessage);
        return jinHuaResult;
    }

    /**
     * 上报考勤
     * @param reportMessage
     * @return
     */
    @ReportMethod({ReportType.worker_signlog})
    public JinHuaResult reportWorkerAttendance(ReportMessage reportMessage){
        JinHuaResult jinHuaResult = new JinHuaResult();
        String resultCode = "";
        String resultMessage = "";
        try {
            //查询打卡记录
            UpWorkerSignlogInfo upWorkerSignlogInfo = upWorkerSignlogInfoMapper.selectByPrimaryKey(Integer.parseInt(reportMessage.getDataId()));
            Integer workerId = upWorkerSignlogInfo.getWorkerId();
            //查询劳工
            UpWorkerIn upWorkerIn = upWorkerInMapper.selectByWorkerId(workerId);
            Integer projId = upWorkerIn.getProjId();
            Integer groupId = upWorkerIn.getGroupId();
            //查询班组
            UpGroupInfo upGroupInfo = upGroupInfoMapper.selectByPrimaryKey(groupId);
            if (upWorkerIn.getReportCode() != null) {
                //查询劳工信息
                UpWorkerInfo upWorkerInfo = upWorkerInfoMapper.selectByPrimaryKey(workerId);
                //查询配置
                UpParams upParams = upParamsMapper.selectByProjId(projId);
                String reportCode = upGroupInfo.getReportCode();//班组上报成功
                if (!StringUtils.isEmpty(reportCode)) {
                    //上报考勤
                    String data = getWorkerAttendanceInfo(upWorkerSignlogInfo, upWorkerInfo, reportCode, upParams);

                    String method = JinHuaConfig.ADD_WORKER_ATTENDANCE_METHOD;
                    int nonce = (int)((Math.random()*9+1)*100000);
                    String currentTime = sdf.format(new Date());
                    String urlParams = getUrlParams(data, method, upParams, nonce, currentTime);
                    String url = jinHuaAddress.UPLOAD_URL;
                    JSONObject jsonObject = ReportHttpUtils.httpPost(url, null, null, urlParams, null, JinHuaConfig.CONTENT_TYPE);
                    String code = jsonObject.getString("code");
                    if (!StringUtils.isEmpty(code)) {
                        if ("0".equals(code)) {//请求成功
                            //更新唯一码
                            String requestSerialCode = jsonObject.getJSONObject("data").getString("requestSerialCode");
                            JSONObject jo = new JSONObject();
                            jo.put("requestSerialCode", requestSerialCode);


                            //循环去查询结果接口5秒一次
                            boolean flag = false;
                            for (int i = 0; i < 10; i++) {
                                Thread.sleep(5000);

                                //查询班组唯一码
                                String queryUrlParams = getUrlParams(jo.toJSONString(), JinHuaConfig.QUERY_RESULT, upParams, (int)((Math.random()*9+1)*100000), currentTime);
                                JSONObject object = ReportHttpUtils.httpPost(url, null, null, queryUrlParams, null, JinHuaConfig.CONTENT_TYPE);
                                String httpResultCode = object.getString("code");
                                if ("0".equals(httpResultCode)) {
                                    String httpStatus = object.getJSONObject("data").getString("status");
                                    if ("20".equals(httpStatus)) {//上报处理成功
                                        flag = true;
                                        break;
                                    }
                                }
                            }

                            if (!flag) {   //上报失败，没有上报结果
                                resultCode = JINHUA_FAILURE;
                                resultMessage = "住建局考勤上报接口请求失败,没有上报结果";
                            } else {
                                //按照ID去更新
                                upWorkerSignlogInfo.setReportFlag("0");//TODO成功
                                UpWorkerSignlogInfoExample upWorkerSignlogInfoExample = new UpWorkerSignlogInfoExample();
                                upWorkerSignlogInfoExample.createCriteria().andIdEqualTo(upWorkerSignlogInfo.getId());
                                upWorkerSignlogInfoMapper.updateByExample(upWorkerSignlogInfo, upWorkerSignlogInfoExample);
                                resultCode = JINHUA_SUCCESS;
                                resultMessage = "住建局考勤上报接口请求成功";
                            }
                        } else {
                            resultCode = JINHUA_FAILURE;
                            resultMessage = "住建局考勤上报接口请求失败";
                        }
                    } else {
                        resultCode = JINHUA_FAILURE;
                        resultMessage = "住建局考勤上报接口请求失败";
                    }
                }
            }
        } catch (Exception e) {
            resultCode = JINHUA_FAILURE;
            resultMessage = "住建局劳工上报异常,请联系技术";
            log.error("JinHuaService reportWorkerAttendance error" , e);
        }
        jinHuaResult.setCode(resultCode);
        jinHuaResult.setMessage(resultMessage);
        return jinHuaResult;
    }

    /**
     * 获取考勤参数
     * @param upWorkerSignlogInfo
     * @param upWorkerInfo
     * @param groupRequestCode
     * @param upParams
     * @return
     */
    private String getWorkerAttendanceInfo(UpWorkerSignlogInfo upWorkerSignlogInfo, UpWorkerInfo upWorkerInfo, String groupRequestCode, UpParams upParams){
        JSONObject jo = new JSONObject();
        jo.put("teamSysNo", groupRequestCode);

        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        String cardNo = upWorkerInfo.getCardNo();
        String cardNoAES = AESUtils.aesEncrypt(cardNo, upParams.getSecret());
        jsonObject.put("idCardNumber", cardNoAES);//必填   身份证号码。AES
        jsonObject.put("date", sdfYMDHSM.format(upWorkerSignlogInfo.getTime()));//必填   刷卡时间，yyyy-MM-dd HH:mm:ss
        jsonObject.put("direction", "01");//必填   刷卡进出方向。参考工人考勤方向字典表
        jsonArray.add(jsonObject);

        jo.put("dataList", jsonArray);
        return jo.toJSONString();
    }

    /**
     * 获取班组参数
     * @param upGroupInfo
     * @param uploadType
     * @return
     */
    private String getGroupInfo(UpGroupInfo upGroupInfo, String uploadType){
        JSONObject jo = new JSONObject();
        if (JinHuaConfig.UPLOAD_UPDATE.equals(uploadType)) {
            jo.put("teamSysNo", upGroupInfo.getReportCode());//班组编号
        }
        jo.put("teamName", upGroupInfo.getGroupName());//必填   班组名称，同一个项目下不能重复
        return jo.toJSONString();
    }

    /**
     * 获取劳工参数
     * @param upWorkerInfo
     * @param upWorkerIn
     * @param upGroupInfo
     * @param upParams
     * @return
     */
    private String getWorkerInfo(UpWorkerInfo upWorkerInfo, UpWorkerIn upWorkerIn, UpGroupInfo upGroupInfo,
                                 List<UpFile> upFileList, UpParams upParams) throws Exception{
        String reportCityCode = "330700";

        JSONArray ja = new JSONArray();
        JSONObject jo = new JSONObject();
        jo.put("workerName", upWorkerInfo.getName());//必填   工人姓名
        jo.put("teamSysNo", upGroupInfo.getReportCode());//必填    班组编号（平台返回）
        jo.put("isTeamLeader", upWorkerIn.getIsForeman());//必填 是否班组长。参考是否字典表
        String cardNo = upWorkerInfo.getCardNo();
        String cardNoAES = AESUtils.aesEncrypt(cardNo, upParams.getSecret());
        //身份证转base64
        jo.put("idCardNumber", cardNoAES);//必填 身份证号码。AES

        String sexType = "sex";
        String sexTypeApiCode = upWorkerInfo.getSex();
        UpDictReport sexDict = UpDictReport.defineOf(reportCityCode, sexType, sexTypeApiCode);
        sexDict = upDictReportServiceImpl.getDictReportByApiCode(sexDict);
        jo.put("gender", sexDict.getCode());//必填   工人性别。参考性别字典表

        String workerType = "worker_type";
        String workerTypeApiCode = upWorkerIn.getWorkType();
        UpDictReport workerTypeDict = UpDictReport.defineOf(reportCityCode, workerType, workerTypeApiCode);
        workerTypeDict = upDictReportServiceImpl.getDictReportByApiCode(workerTypeDict);
        jo.put("workType", workerTypeDict.getCode());//必填 当前工种。参考工人工种字典表


        String workerRoleType = "worker_role";
        String workerRoleApiCode = upWorkerIn.getMemberRole();
        if(StringUtils.isEmpty(workerRoleApiCode)){
            workerRoleApiCode = upWorkerIn.getMemberType();
        }
        UpDictReport workRoleDict = UpDictReport.defineOf(reportCityCode, workerRoleType, workerRoleApiCode);
        workRoleDict = upDictReportServiceImpl.getDictReportByApiCode(workRoleDict);
        jo.put("workRole", workRoleDict.getCode());//必填 工人类型。参考工人类型字典表

        String nationDictType = "nation";
        String nationApiCode = upWorkerInfo.getNation();
        UpDictReport nationDict = UpDictReport.defineOf(reportCityCode, nationDictType, nationApiCode);
        nationDict = upDictReportServiceImpl.getDictReportByApiCode(nationDict);

        jo.put("nation", nationDict.getCode());//必填   民族。身份证上民族信息，如：汉，回，藏等
        jo.put("address", upWorkerInfo.getCurrentAddress());//必填  住址
        //获取头像转base64
        UpFile imageFile = UpFile.builder().build();
        if (null != upFileList && upFileList.size() > 0) {
            for (UpFile upFile : upFileList) {
                String tableModule = upFile.getTableModule();
                if ("3".equals(tableModule)) {//头像模块
                    imageFile = upFile;
                    break;
                }
            }
        }
        String filePath = imageFile.getFilePath();
//        String filePath = "D:\\tmp\\home\\tomcat\\resource\\20200723\\0723_013825.jpg";
        if (!StringUtils.isEmpty(filePath)) {
            int fileSize = JinHuaUtils.getFileSize(filePath);
            if (0 == fileSize || fileSize > 50*1024) {//不超过50KB
                log.info("图片大小:" + (fileSize/1024) + "KB,图片超过50KB");
                throw new JinHuaException("图片大小:" + (fileSize/1024) + "KB,图片超过50KB");
            }

            String imageBase64 = JinHuaUtils.getImageBase64String(filePath);
            jo.put("headImage", imageBase64);//必填    头像。不超过50KB的Base64字符串
        }

        jo.put("headImageSuffix", imageFile.getFileType());//必填  头像图片文件名后缀 如：jpg
//        jo.put("headImageSuffix", "jpg");//必填  头像图片文件名后缀 如：jpg

        String politicsType = "politics_status";
        String politicsTypeApiCode = upWorkerInfo.getPolitical();
        UpDictReport politicsDict = UpDictReport.defineOf(reportCityCode, politicsType, politicsTypeApiCode);
        politicsDict = upDictReportServiceImpl.getDictReportByApiCode(politicsDict);
        jo.put("politicsType", politicsDict.getCode());//必填 政治面貌。参考政治面貌字典表

        jo.put("cellPhone", upWorkerInfo.getMobile());//必填    手机号码

        String cultureType = "culture";
        String cultureTypeApiCode = upWorkerInfo.getEducationDegree();
        UpDictReport cultureDict = UpDictReport.defineOf(reportCityCode, cultureType, cultureTypeApiCode);
        cultureDict = upDictReportServiceImpl.getDictReportByApiCode(cultureDict);
        jo.put("cultureLevelType", cultureDict.getCode());//必填 文化程度。参考文化程度字典表

        jo.put("grantOrg", upWorkerInfo.getCardNoIssuing());//必填 发证机关
        Date cardNoStartDate = upWorkerInfo.getCardNoStartDate();
        Date cardNoEndDate = upWorkerInfo.getCardNoEndDate();

        String cardDate = "";
        if (null != cardNoStartDate && null != cardNoEndDate) {
            String startStr = sdfymd.format(cardNoStartDate);
            String endStr = sdfymd.format(cardNoEndDate);
            cardDate = startStr + "-" + endStr;
        }
        if (StringUtils.isEmpty(cardDate)) {
            return "";
        }
        jo.put("cardDate", cardDate);//非必填 证件有效期起止日期。格式2007年08月22日-2027年08月22日
        jo.put("inDate", sdfYMD.format(upWorkerIn.getEntryDate()));//必填   进场日期 格式：yyyy-MM-dd
        //jo.put("outDate", "2020-01-01");//非必填 离场日期 格式：yyyy-MM-dd
        ja.add(jo);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("workerList", ja);
        return jsonObject.toJSONString();
    }

    /**
     * 获取签名
     * @param method
     * @param upParams
     * @param data
     * @param nonce
     * @param currentTime
     * @return
     */
    private String getSignInfo(String method, UpParams upParams, String data, int nonce, String currentTime){
        StringBuilder sb = new StringBuilder();
        String appId = upParams.getKey();
        String appsecret = upParams.getSecret();
        sb.append("appid=")
                .append(appId)
                .append("&data=")
                .append(data)
                .append("&format=json")
                .append("&method=")
                .append(method)
                .append("&nonce=")
                .append(nonce)
                .append("&timestamp=")
                .append(currentTime)
                .append("&version=")
                .append(JinHuaConfig.VERSION)
                .append("&appsecret=")
                .append(appsecret);

        String s = sb.toString();
        //字符串全部转小写
        String lowerCaseStr = s.toLowerCase();
        System.out.println(lowerCaseStr);
        //加密SHA256
        String result = Sha256.getSHA256(lowerCaseStr);
        System.out.println(result);
        return result;
    }

    /**
     * 获取URL请求参数
     * @param data
     * @param method
     * @param upParams
     * @param nonce
     * @param currentTime
     * @return
     */
    private String getUrlParams(String data, String method, UpParams upParams, int nonce, String currentTime) throws UnsupportedEncodingException {
        String signInfo = getSignInfo(method, upParams, data, nonce, currentTime);

        String appId = upParams.getKey();
        String appsecret = upParams.getSecret();

        StringBuilder sb = new StringBuilder();
        sb.append("method=")
                .append(method);
        sb.append("&format=json")
                .append("&version=")
                .append(JinHuaConfig.VERSION)
                .append("&appid=")
                .append(appId)
                .append("&timestamp=")
                .append(currentTime)
                .append("&nonce=")
                .append(nonce)
                .append("&sign=")
                .append(signInfo)
                .append("&data=")
                .append(URLEncoder.encode(data,"utf-8"));
        System.out.println(sb.toString());
        return sb.toString();
    }
}
