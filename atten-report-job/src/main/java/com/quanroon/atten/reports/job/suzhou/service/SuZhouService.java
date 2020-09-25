package com.quanroon.atten.reports.job.suzhou.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.quanroon.atten.commons.utils.StringUtils;
import com.quanroon.atten.reports.common.ReportConstant;
import com.quanroon.atten.reports.common.ReportType;
import com.quanroon.atten.reports.dao.*;
import com.quanroon.atten.reports.entity.*;
import com.quanroon.atten.reports.entity.example.*;
import com.quanroon.atten.reports.job.suzhou.SuZhouClient;
import com.quanroon.atten.reports.job.suzhou.common.SuZhouReportConstant;
import com.quanroon.atten.reports.job.suzhou.config.SuZhouConfig;
import com.quanroon.atten.reports.job.suzhou.entity.SuZhouResult;
import com.quanroon.atten.reports.job.suzhou.utils.SuZhouException;
import com.quanroon.atten.reports.job.suzhou.utils.SuZhouUtil;
import com.quanroon.atten.reports.report.annotation.City;
import com.quanroon.atten.reports.report.annotation.ReportMethod;
import com.quanroon.atten.reports.report.constant.ReportCityCode;
import com.quanroon.atten.reports.report.entity.ReportResult;
import com.quanroon.atten.reports.report.entity.ReportService;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;

/**
* 宿州上报业务层
* 
* @Author: ysx
* @Date: 2020/8/4
*/
@City(ReportCityCode.SUZHOU)
@Component
public class SuZhouService implements ReportService {

    @Autowired
    private UpGroupInfoMapper upGroupInfoMapper;
    @Autowired
    private UpParamsMapper upParamsMapper;
    @Autowired
    private UpWorkerInfoMapper upWorkerInfoMapper;
    @Autowired
    private UpWorkerInMapper upWorkerInMapper;
    @Autowired
    private UpWorkerSignlogInfoMapper upWorkerSignlogInfoMapper;
    @Autowired
    private UpFileMapper upFileMapper;
    @Autowired
    private NettyClientService nettyClientServiceImpl;
    @Autowired
    private UpCompanyInfoMapper upCompanyInfoMapper;
    @Autowired
    private UpCompanyInMapper upCompanyInMapper;
    @Autowired
    private SuZhouClient suZhouClient;
    @Autowired
    private UpDictInfoMapper upDictInfoMapper;

    private static Logger logger = LoggerFactory.getLogger(SuZhouService.class);
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
    * @Description: 获取token
    * @Param: [upParams]
    * @return: java.lang.String
    * @Author: ysx
    * @Date: 2020/8/4
    */
    public String getToken(UpParams upParams) throws Exception {

        //封装获取token所需的参数
        String method = SuZhouConfig.TOKEN_GET;
        JSONObject jsonObject = this.getUpLoadInfo(method);
        jsonObject.put("args", this.getTokenArgs(upParams));
        ByteBuf byteBuf = this.getByteBuf(jsonObject);

        //从netty服务端获取token
        JSONObject object = nettyClientServiceImpl.sendSyncMsg(byteBuf, method);

        //如果netty服务端没有返回值，则判定为netty通道关闭，重新连接netty服务端，确保此次上报的消息不会丢失
        if (Objects.isNull(object)) {
            logger.info("通道关闭，鉴权获取失败，准备重新连接通道");
            //重新链接netty服务端，重新获取token
            suZhouClient.runClient();
            return this.getToken(upParams);
        }

        //返回token
        JSONObject data = object.getJSONObject("data").getJSONObject("data");
        String token = data.getString("token");
        return token;
    }

    /**
    * @Description: 封装获取token所需的参数
    * @Param: [upParams]
    * @return: com.alibaba.fastjson.JSONArray
    * @Author: ysx
    * @Date: 2020/8/4
    */
    private JSONArray getTokenArgs(UpParams upParams) {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("app_id", upParams.getKey());
        jsonObject.put("app_secret", upParams.getSecret());
        jsonArray.add(jsonObject);
        return jsonArray;
    }

    /**
    * @Description: 上报班组或修改班组
    * @Param: [reportMessage]
    * @return: com.quanroon.atten.reports.report.entity.ReportResult
    * @Author: ysx
    * @Date: 2020/8/4
    */
    @ReportMethod({ReportType.group_report, ReportType.group_update})
    public ReportResult reportGroup(ReportMessage reportMessage) {

        //上报班组或修改班组返回的结果
        SuZhouResult result = new SuZhouResult();

        try {
            //获取班组id
            Integer groupId = Integer.valueOf(reportMessage.getDataId());

            //查询班组
            UpGroupInfo upGroupInfo = upGroupInfoMapper.selectByPrimaryKey(groupId);

            //查询配置
            UpParams upParams = upParamsMapper.selectByProjId(upGroupInfo.getProjId());

            //根据班组信息表中的reportCode，判断是否已经上报过
            String method = SuZhouConfig.GROUP_ADD;
            if (StringUtils.isNotEmpty(upGroupInfo.getReportCode())) {
                //更新
                method = SuZhouConfig.GROUP_UPDATE;
            }

            //封装要上报的班组信息
            JSONObject jsonObject = this.getUpLoadInfo(method);
            jsonObject.put("args", this.getGroupInfo(upParams, upGroupInfo, method));
            ByteBuf byteBuf = this.getByteBuf(jsonObject);

            //向netty服务端上报班组信息
            JSONObject json = nettyClientServiceImpl.sendSyncMsg(byteBuf, method);
            logger.info("班组上报结果：", json.toJSONString());

            //根据返回信息判断班组上报成功失败
            if (SuZhouConfig.SUCCESS_FLAG.equals(json.getString("state"))) {
                //上报新增班组则保存住建局返回的班组id
                if (method.equals(SuZhouConfig.GROUP_ADD)) {
                    //住建局返回的班组id
                    String reportCode = json.getJSONObject("data").getString("data");

                    //保存住建局返回的班组id
                    upGroupInfo.setReportCode(reportCode);
                    UpGroupInfoExample example = new UpGroupInfoExample();
                    example.createCriteria().andIdEqualTo(upGroupInfo.getId());
                    upGroupInfoMapper.updateByExample(upGroupInfo, example);

                    //班组上报成功
                    result.setCode(SuZhouConfig.SUCCESS_FLAG);
                    result.setMessage(SuZhouReportConstant.GROUP_ADD_SUCCESS);
                } else {
                    //班组修改成功
                    result.setCode(SuZhouConfig.SUCCESS_FLAG);
                    result.setMessage(SuZhouReportConstant.GROUP_UPDATE_SUCCESS);
                }
            } else {
                //住建局返回的上报失败详情
                String message = json.getJSONObject("data").getJSONObject("message").getString("message");

                //班组上报或修改失败
                result.setCode(SuZhouConfig.FAILURE_FLAG);
                result.setMessage(message);
            }
        } catch (Exception e) {
            //上报班组服务异常
            result.setCode(SuZhouConfig.FAILURE_FLAG);
            result.setMessage(SuZhouReportConstant.GROUP_REPORT_ERROR);
            logger.error(SuZhouReportConstant.GROUP_REPORT_ERROR + ":", e);
        }
        return result;
    }

    /**
    * @Description: 班组删除（离场）
    * @Param: [reportMessage]
    * @return: com.quanroon.atten.reports.report.entity.ReportResult
    * @Author: ysx
    * @Date: 2020/8/4
    */
    //@ReportMethod({ReportType.group_leave})
    public ReportResult deleteGroup(ReportMessage reportMessage) {

        //上报班组或修改班组返回的结果
        SuZhouResult result = new SuZhouResult();

        try {
            //获取班组id
            Integer groupId = Integer.valueOf(reportMessage.getDataId());

            //查询班组
            UpGroupInfo upGroupInfo = upGroupInfoMapper.selectByPrimaryKey(groupId);

            //查询配置
            UpParams upParams = upParamsMapper.selectByProjId(upGroupInfo.getProjId());

            //封装要离场的班组信息
            String method = SuZhouConfig.GROUP_DELETE;
            JSONObject jsonObject = this.getUpLoadInfo(method);
            jsonObject.put("args", this.getGroupInfo(upParams, upGroupInfo, method));
            ByteBuf byteBuf = this.getByteBuf(jsonObject);

            //向netty服务端发送班组删除（离场）信息
            JSONObject json = nettyClientServiceImpl.sendSyncMsg(byteBuf, method);
            logger.info("班组删除（离场）结果：", json.toJSONString());

            //根据返回信息判断离场成功失败
            if (SuZhouConfig.SUCCESS_FLAG.equals(json.getString("state"))) {
                //班组离场成功，更新班组信息，将其设置成离场状态
                upGroupInfo.setStatus(SuZhouReportConstant.GROUP_DELETE_FLAG);
                UpGroupInfoExample example = new UpGroupInfoExample();
                example.createCriteria().andIdEqualTo(upGroupInfo.getId());
                upGroupInfoMapper.updateByExample(upGroupInfo, example);

                //返回班组离场结果
                result.setCode(SuZhouConfig.SUCCESS_FLAG);
                result.setMessage(SuZhouReportConstant.GROUP_DELETE_SUCCESS);
            } else {
                //住建局返回的上报失败详情
                String message = json.getJSONObject("data").getJSONObject("message").getString("message");

                //返回离场失败结果
                result.setCode(SuZhouConfig.FAILURE_FLAG);
                result.setMessage(message);
            }
        } catch (Exception e) {
            //班组离场服务异常
            result.setCode(SuZhouConfig.FAILURE_FLAG);
            result.setMessage(SuZhouReportConstant.GROUP_DELETE_ERROR);
            logger.error(SuZhouReportConstant.GROUP_DELETE_ERROR + ":", e);
        }
        return result;
    }

    /**
    * @Description: 劳工上报、班组负责人上报、合同上报
    * @Param: [reportMessage]
    * @return: com.quanroon.atten.reports.report.entity.ReportResult
    * @Author: ysx
    * @Date: 2020/8/4
    */
    @ReportMethod({ReportType.worker_enter})
    public ReportResult reportWorkerInfo(ReportMessage reportMessage) {

        //上报劳工返回的结果
        SuZhouResult result = new SuZhouResult();

        try {
            //获取劳工id
            Integer workerInId = Integer.valueOf(reportMessage.getDataId());

            //根据劳工id，查询劳工进场信息
            UpWorkerIn upWorkerIn = upWorkerInMapper.selectByPrimaryKey(workerInId);

            //查询劳工信息
            UpWorkerInfo upWorkerInfo = upWorkerInfoMapper.selectByPrimaryKey(upWorkerIn.getWorkerId());

            //查询班组信息
            UpGroupInfo upGroupInfo = upGroupInfoMapper.selectByPrimaryKey(upWorkerIn.getGroupId());

            //查询配置
            UpParams upParams = upParamsMapper.selectByProjId(upWorkerIn.getProjId());

            //封装上报劳工信息
            String method = SuZhouConfig.WORKER_ADD;
            JSONObject jsonObject = this.getUpLoadInfo(method);
            jsonObject.put("args", this.getWorkerInfo(upParams, upGroupInfo, upWorkerInfo));
            ByteBuf byteBuf = this.getByteBuf(jsonObject);

            //根据进场表reportCode判断是否已经上报过，上报过不再进行上报
            if (StringUtils.isEmpty(upWorkerIn.getReportCode())) {
                //向netty服务端上报劳工信息
                JSONObject workerResponse = nettyClientServiceImpl.sendSyncMsg(byteBuf, method);
                logger.info("劳工上报结果：" + workerResponse.toJSONString());

                //根据返回信息判断劳工上报成功失败
                if (SuZhouConfig.SUCCESS_FLAG.equals(workerResponse.getString("state"))) {
                    //劳工上报成功，保存从住建局返回的自然人id:user_id和劳工id:labour_id
                    String userId = workerResponse.getJSONObject("data").getJSONObject("data").getString("user_id");
                    String labourId = workerResponse.getJSONObject("data").getJSONObject("data").getString("labour_id");
                    //将两者合在一起用逗号隔开
                    String reportCode = userId + "," + labourId;
                    upWorkerIn.setReportCode(reportCode);

                    //保存住建局返回的user_id和labour_id用于修改和其它上报
                    UpWorkerInExample upWorkerInExample = new UpWorkerInExample();
                    upWorkerInExample.createCriteria().andWorkerIdEqualTo(upWorkerInfo.getId());
                    upWorkerInMapper.updateByExample(upWorkerIn, upWorkerInExample);
                } else {
                    //住建局返回的上报失败详情
                    String message = workerResponse.getJSONObject("data").getJSONObject("message").getString("message");

                    //返回上报失败结果，并且结束此次上报
                    result.setCode(SuZhouConfig.FAILURE_FLAG);
                    result.setMessage(message);
                    return result;
                }
            }

            //当上报劳工为班组长时，同时上报班组负责人
            if (upWorkerIn.getIsForeman().equals(SuZhouReportConstant.GROUP_LEADER_FLAG)) {
                //封装要上报的班组负责人信息
                method = SuZhouConfig.GROUP_LEADER;
                JSONObject groupInfo = this.getUpLoadInfo(method);
                groupInfo.put("args", this.getGroupLeader(upParams, upWorkerIn, upGroupInfo));
                ByteBuf buf = this.getByteBuf(groupInfo);

                //上报班组负责人
                JSONObject groupLeader = nettyClientServiceImpl.sendSyncMsg(buf, method);
                logger.info("班组负责人上报结果：" + groupLeader.toJSONString());

                //根据返回信息判断班组负责人上报成功失败
                if (SuZhouConfig.FAILURE_FLAG.equals(groupLeader.getString("state"))) {
                    //获取班组负责人上报失败详情
                    String message = groupLeader.getJSONObject("data").getJSONObject("message").getString("message");

                    //返回班组负责人上报失败结果，并且结束此次上报
                    result.setCode(SuZhouConfig.FAILURE_FLAG);
                    result.setMessage(message);
                    return result;
                }
            }

            //上报劳工同时上报劳工合同
            UpFileExample fileExample = new UpFileExample();
            fileExample.createCriteria().andTableIdEqualTo(upWorkerIn.getId()).andTableModuleEqualTo(ReportConstant.MODULE_WORKER_CONTRACT);
            List<UpFile> upFileList = upFileMapper.selectByExample(fileExample);

            method = SuZhouConfig.WORKER_CONTRACT;
            //封装劳工合同信息
            JSONObject contract = this.getUpLoadInfo(method);
            contract.put("args", this.getContract(upParams, upWorkerIn, upFileList.get(0)));
            ByteBuf msg = this.getByteBuf(contract);

            //上报劳工合同
            JSONObject workerContract = nettyClientServiceImpl.sendSyncMsg(msg, method);
            logger.info("合同上报结果：" + workerContract.toJSONString());

            //根据返回信息判断合同上报成功失败
            if (SuZhouConfig.FAILURE_FLAG.equals(workerContract.getString("state"))) {
                //获取合同上报失败详情
                String message = workerContract.getJSONObject("data").getJSONObject("message").getString("message");

                //返回合同上报失败结果，并结束此次上报
                result.setCode(SuZhouConfig.FAILURE_FLAG);
                result.setMessage(message);
                return result;
            }

            //返回此次上报成功结果
            result.setCode(SuZhouConfig.SUCCESS_FLAG);
            result.setMessage(SuZhouReportConstant.WORKER_REPORT_SUCCESS);
        } catch (Exception e) {
            //上报劳工服务异常
            result.setCode(SuZhouConfig.FAILURE_FLAG);
            result.setMessage(SuZhouReportConstant.WORKER_REPORT_ERROR);
            logger.error(SuZhouReportConstant.WORKER_REPORT_ERROR + ":", e);
        }
        return result;
    }

    /**
    * @Description: 劳工删除（离场）
    * @Param: [reportMessage]
    * @return: com.quanroon.atten.reports.report.entity.ReportResult
    * @Author: ysx
    * @Date: 2020/8/4
    */
    @ReportMethod({ReportType.worker_leave})
    public ReportResult deleteWorker(ReportMessage reportMessage) {

        //劳工删除返回的结果
        SuZhouResult result = new SuZhouResult();

        try {
            //获取劳工进场业务id
            Integer workerInId = Integer.valueOf(reportMessage.getDataId());

            //根据劳工进场业务id，查询劳工进场信息
            UpWorkerIn upWorkerIn = upWorkerInMapper.selectByPrimaryKey(workerInId);

            //查询配置
            UpParams upParams = upParamsMapper.selectByProjId(upWorkerIn.getProjId());

            //封装上报劳工信息
            String method = SuZhouConfig.WORKER_DELETE;
            JSONObject jsonObject = this.getUpLoadInfo(method);
            jsonObject.put("args", this.getWorkerIn(upParams, upWorkerIn));
            ByteBuf byteBuf = this.getByteBuf(jsonObject);

            //劳工删除（离场）
            JSONObject json = nettyClientServiceImpl.sendSyncMsg(byteBuf, method);
            logger.info("劳工删除（离场）结果：" + json.toJSONString());

            //根据返回信息判断劳工删除成功失败
            if (SuZhouConfig.SUCCESS_FLAG.equals(json.getString("state"))) {
                //劳工离场成功，更新劳工进出场状态
                upWorkerIn.setStatus(SuZhouReportConstant.WORKER_ENTER_FLAG);
                UpWorkerInExample example = new UpWorkerInExample();
                example.createCriteria().andWorkerIdEqualTo(upWorkerIn.getWorkerId());
                upWorkerInMapper.updateByExample(upWorkerIn, example);

                //返回劳工删除成功结果
                result.setCode(SuZhouConfig.SUCCESS_FLAG);
                result.setMessage(SuZhouReportConstant.WORKER_DELETE_SUCCESS);
            } else {
                //获取劳工删除失败结果
                String message = json.getJSONObject("data").getJSONObject("message").getString("message");
                result.setCode(SuZhouConfig.FAILURE_FLAG);
                result.setMessage(message);
            }
        } catch (Exception e) {
            //劳工离场服务异常
            result.setCode(SuZhouConfig.FAILURE_FLAG);
            result.setMessage(SuZhouReportConstant.WORKER_DELETE_ERROR);
            logger.error(SuZhouReportConstant.WORKER_DELETE_ERROR + ":", e);
        }
        return result;
    }

    /**
    * @Description: 考勤上报
    * @Param: [reportMessage]
    * @return: com.quanroon.atten.reports.report.entity.ReportResult
    * @Author: ysx
    * @Date: 2020/8/4
    */
    @ReportMethod({ReportType.worker_signlog})
    public ReportResult reportSignlog(ReportMessage reportMessage) {

        //劳工考勤返回的结果
        SuZhouResult result = new SuZhouResult();

        try {
            //获取考勤id
            Integer signlogId = Integer.valueOf(reportMessage.getDataId());

            //根据考勤id获取考勤信息
            UpWorkerSignlogInfo upWorkerSignlogInfo = upWorkerSignlogInfoMapper.selectByPrimaryKey(signlogId);

            //查询配置
            UpParams upParams = upParamsMapper.selectByProjId(upWorkerSignlogInfo.getProjId());

            //查询劳工信息
            UpWorkerInfo upWorkerInfo = upWorkerInfoMapper.selectByPrimaryKey(upWorkerSignlogInfo.getWorkerId());

            //查询劳工进场信息
            UpWorkerInExample example = new UpWorkerInExample();
            example.createCriteria().andWorkerIdEqualTo(upWorkerSignlogInfo.getWorkerId());
            List<UpWorkerIn> upWorkerInList = upWorkerInMapper.selectByExample(example);

            //查询班组信息
            UpGroupInfoExample groupInfoExample = new UpGroupInfoExample();
            groupInfoExample.createCriteria().andIdEqualTo(upWorkerInList.get(0).getGroupId());
            List<UpGroupInfo> upGroupInfoList = upGroupInfoMapper.selectByExample(groupInfoExample);

            //封装上报考勤信息
            String method = SuZhouConfig.WORKER_SIGNLOG;
            JSONObject jsonObject = this.getUpLoadInfo(method);
            jsonObject.put("args", this.getWorkerSignlog(upParams, upWorkerSignlogInfo, upWorkerInList.get(0), upGroupInfoList.get(0), upWorkerInfo));
            ByteBuf byteBuf = this.getByteBuf(jsonObject);

            //上报考勤信息
            JSONObject json = nettyClientServiceImpl.sendSyncMsg(byteBuf, method);
            logger.info("劳工考勤上报结果：" + json.toJSONString());

            //根据返回信息判断上报成功失败
            if (SuZhouConfig.SUCCESS_FLAG.equals(json.getString("state"))) {
                //考勤上报成功，更新考勤上报状态
                upWorkerSignlogInfo.setReportFlag(SuZhouReportConstant.SIGNLOG_SUCCESS_FLAG);
                UpWorkerSignlogInfoExample upWorkerSignlogInfoExample = new UpWorkerSignlogInfoExample();
                upWorkerSignlogInfoExample.createCriteria().andIdEqualTo(upWorkerSignlogInfo.getId());
                upWorkerSignlogInfoMapper.updateByExample(upWorkerSignlogInfo, upWorkerSignlogInfoExample);

                //返回考勤上报成功结果
                result.setCode(SuZhouConfig.SUCCESS_FLAG);
                result.setMessage("考勤上报成功");
            } else {
                //考勤上报失败，更新考勤上报状态
                upWorkerSignlogInfo.setReportFlag(SuZhouReportConstant.SIGNLOG_FAILURE_FLAG);
                UpWorkerSignlogInfoExample upWorkerSignlogInfoExample = new UpWorkerSignlogInfoExample();
                upWorkerSignlogInfoExample.createCriteria().andIdEqualTo(upWorkerSignlogInfo.getId());
                upWorkerSignlogInfoMapper.updateByExample(upWorkerSignlogInfo, upWorkerSignlogInfoExample);

                //获取考勤上报失败结果
                String message = json.getJSONObject("data").getString("message");

                //返回考勤上报失败结果
                result.setCode(SuZhouConfig.FAILURE_FLAG);
                result.setMessage(message);
            }
        } catch (Exception e) {
            //考勤上报服务异常
            result.setCode(SuZhouConfig.FAILURE_FLAG);
            result.setMessage(SuZhouReportConstant.SIGNLOG_REPORT_ERROR);
            logger.error(SuZhouReportConstant.SIGNLOG_REPORT_ERROR + ":", e);
        }
        return result;
    }

    /**
    * @Description: 封装合同信息
    * @Param: [upParams, upWorkerIn, upFile]
    * @return: com.alibaba.fastjson.JSONArray
    * @Author: ysx
    * @Date: 2020/8/4
    */
    public JSONArray getContract(UpParams upParams, UpWorkerIn upWorkerIn, UpFile upFile) throws Exception {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();

        //获取上报配置中的参数
        String token = this.getToken(upParams);

        jsonObject.put("token", token);
        jsonObject.put("app_id", upParams.getKey());
        jsonObject.put("app_secret", upParams.getSecret());

        //逗号分割上报劳工返回的reportCode,reportCode[0]为user_id,reportCode[1]是labour_id
        String[] reportCode = upWorkerIn.getReportCode().split(",");
        jsonObject.put("id", reportCode[1]);

        JSONObject contract = new JSONObject();
        contract.put("name", upFile.getFileName());
        contract.put("url", upFile.getFilePath());
        jsonObject.put("contract_json", contract.toJSONString());

        jsonArray.add(jsonObject);
        return jsonArray;
    }

    /**
    * @Description: 封装劳工考勤信息
    * @Param: [upParams, upWorkerSignlogInfo, upWorkerIn, upGroupInfo, upWorkerInfo]
    * @return: com.alibaba.fastjson.JSONArray
    * @Author: ysx
    * @Date: 2020/8/4
    */
    public JSONArray getWorkerSignlog(UpParams upParams, UpWorkerSignlogInfo upWorkerSignlogInfo, UpWorkerIn upWorkerIn,
                                      UpGroupInfo upGroupInfo, UpWorkerInfo upWorkerInfo) throws Exception {

        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();

        //获取上报配置中的参数
        this.getConfig(jsonObject, upParams);

        //参见单位名称，多个参见单位则选择其中之一
        UpCompanyInExample example = new UpCompanyInExample();
        example.createCriteria().andProjIdEqualTo(upParams.getProjId());
        List<UpCompanyIn> upCompanyInList = upCompanyInMapper.selectByExample(example);
        UpCompanyInfo upCompanyInfo = upCompanyInfoMapper.selectByPrimaryKey(upCompanyInList.get(0).getCompanyId());
        jsonObject.put("enterprise_name", upCompanyInfo.getName());

        jsonObject.put("group_id", upGroupInfo.getReportCode());
        jsonObject.put("group_name", upGroupInfo.getGroupName());

        //逗号分割上报劳工返回的reportCode,reportCode[0]为user_id,reportCode[1]是labour_id
        String[] reportCode = upWorkerIn.getReportCode().split(",");
        jsonObject.put("labour_id", reportCode[1]);
        jsonObject.put("user_id", reportCode[0]);

        String direction = upWorkerSignlogInfo.getDirection();
        //住建局那边0是进场，1是出场，只有两种状态，所以当不是进场和出场时，默认进场
        direction = direction.equals(SuZhouReportConstant.REPORT_DEVICE_DIRECTION_IN) ? SuZhouReportConstant.DEVICE_DIRECTION_IN : SuZhouReportConstant.DEVICE_DIRECTION_OUT;
        jsonObject.put("type", direction);

        jsonObject.put("collection_date", sdf.format(upWorkerSignlogInfo.getTime()));

        //设备编号，宿州住建局对设备编码长度有限制，这里我们采用项目id+设备编号前三位
        String machineNo = upParams.getProjId() + upWorkerSignlogInfo.getDeviceSn().substring(0,3);
        jsonObject.put("collection_machine_no", machineNo);

        jsonObject.put("base_user_name", upWorkerInfo.getName());
        jsonObject.put("base_user_no", upWorkerInfo.getCardNo());

        //住建局0女，1男
        String sex = upWorkerInfo.getSex().equals(SuZhouReportConstant.REPORT_SEX_FEMALE) ? SuZhouReportConstant.SEX_FEMALE : SuZhouReportConstant.SEX_MALE;
        jsonObject.put("gender", sex);

        //获取考勤图片
        List<UpFile> upFileList = upFileMapper.selectByTableNameAndTableId(ReportConstant.UP_WORKER_SIGNLOG_INFO, upWorkerSignlogInfo.getId());
        String filePath = upFileList.get(0).getFilePath();
        this.fileSolve(filePath, jsonObject, "collection_image");

        jsonArray.add(jsonObject);
        return jsonArray;
    }

    /**
    * @Description: 封装删除劳工信息
    * @Param: [upParams, upWorkerIn]
    * @return: com.alibaba.fastjson.JSONArray
    * @Author: ysx
    * @Date: 2020/8/4
    */
    private JSONArray getWorkerIn(UpParams upParams, UpWorkerIn upWorkerIn) throws Exception {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();

        //获取上报配置中的参数
        this.getConfig(jsonObject, upParams);

        //逗号分割上报劳工返回的reportCode,reportCode[0]为user_id,reportCode[1]是labour_id
        String[] reportCode = upWorkerIn.getReportCode().split(",");

        //获取删除劳工信息
        jsonObject.put("labour_id", reportCode[1]);

        jsonArray.add(jsonObject);
        return jsonArray;
    }

    /**
    * @Description: 封装上报劳工信息
    * @Param: [upParams, upGroupInfo, upWorkerInfo]
    * @return: com.alibaba.fastjson.JSONArray
    * @Author: ysx
    * @Date: 2020/8/4
    */
    private JSONArray getWorkerInfo(UpParams upParams, UpGroupInfo upGroupInfo, UpWorkerInfo upWorkerInfo) throws Exception {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();

        //获取上报配置中的参数
        this.getConfig(jsonObject, upParams);

        //获取上报劳工信息
        jsonObject.put("group_id", upGroupInfo.getReportCode());
        jsonObject.put("id_no", upWorkerInfo.getCardNo());
        jsonObject.put("id_name", upWorkerInfo.getName());
        jsonObject.put("birthday", upWorkerInfo.getBirthday());
        jsonObject.put("mobile", upWorkerInfo.getMobile());

        //住建局0女，1男
        String sex = upWorkerInfo.getSex().equals(SuZhouReportConstant.REPORT_SEX_FEMALE) ? SuZhouReportConstant.SEX_FEMALE : SuZhouReportConstant.SEX_MALE;
        jsonObject.put("gender", sex);

        //民族
        UpDictInfoExample dictInfoExample = new UpDictInfoExample();
        dictInfoExample.createCriteria().andDictTypeEqualTo(SuZhouReportConstant.NATION_TYPE).andDictValueEqualTo(upWorkerInfo.getNation());
        List<UpDictInfo> upDictInfos = upDictInfoMapper.selectByExample(dictInfoExample);
        String nation = CollectionUtils.isEmpty(upDictInfos) ? SuZhouReportConstant.NATION_DEFAULT : upDictInfos.get(0).getName();
        jsonObject.put("nation", nation);

        jsonObject.put("residency", upWorkerInfo.getCardNoAddress());

        //获取劳工身份证图片
        UpFileExample example = new UpFileExample();
        example.createCriteria().andTableModuleEqualTo(ReportConstant.MODULE_IDCARD_FRONT).andTableIdEqualTo(upWorkerInfo.getId());
        List<UpFile> idCardImage = upFileMapper.selectByExample(example);
        String filePath = idCardImage.get(0).getFilePath();
        //限制图片大小50k
        this.fileSolve(filePath, jsonObject, "id_image");

        //获取劳工人脸图片
        example.clear();
        example.createCriteria().andTableModuleEqualTo(ReportConstant.MODULE_WORKER_IMAGE).andTableIdEqualTo(upWorkerInfo.getId());
        idCardImage = upFileMapper.selectByExample(example);
        filePath = idCardImage.get(0).getFilePath();
        //限制图片大小50k
        this.fileSolve(filePath, jsonObject, "collection_image");

        //采集人脸图片的设备序列号
        double deviceId = (Math.random() * 9 + 1) * 100000;
        jsonObject.put("collection_machine_no", deviceId);
        jsonArray.add(jsonObject);
        return jsonArray;
    }

    /**
    * @Description: 封装班组负责人信息
    * @Param: [upParams, upWorkerIn, upGroupInfo]
    * @return: com.alibaba.fastjson.JSONArray
    * @Author: ysx
    * @Date: 2020/8/4
    */
    private JSONArray getGroupLeader(UpParams upParams, UpWorkerIn upWorkerIn, UpGroupInfo upGroupInfo) throws Exception {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();

        //获取上报配置中的参数
        this.getConfig(jsonObject, upParams);

        //上报班组负责人需要封装的参数
        jsonObject.put("id", upGroupInfo.getReportCode());

        //逗号分割上报劳工返回的reportCode,reportCode[0]为user_id,reportCode[1]是labour_id
        String[] reportCode = upWorkerIn.getReportCode().split(",");
        jsonObject.put("user_id", reportCode[0]);

        jsonArray.add(jsonObject);
        return jsonArray;
    }

    /**
    * @Description: 封装班组信息
    * @Param: [upParams, upGroupInfo, method]
    * @return: com.alibaba.fastjson.JSONArray
    * @Author: ysx
    * @Date: 2020/8/4
    */
    private JSONArray getGroupInfo(UpParams upParams, UpGroupInfo upGroupInfo, String method) throws Exception {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();

        //获取上报配置中的参数
        this.getConfig(jsonObject, upParams);

        //上报或修改班组需要封装的参数
        if (method.equals(SuZhouConfig.GROUP_ADD) || method.equals(SuZhouConfig.GROUP_UPDATE)) {
            //班组类型匹配
            String groupCategory = upGroupInfo.getGroupType().equals(SuZhouReportConstant.GROUP_TYPE_ID_TECHNOLOGY)
                    ? SuZhouReportConstant.GROUP_TYPE_TECHNOLOGY : upGroupInfo.getGroupType().equals(SuZhouReportConstant.GROUP_TYPE_ID_COMMON)
                    ? SuZhouReportConstant.GROUP_TYPE_COMMON : SuZhouReportConstant.GROUP_TYPE_OTHER;
            jsonObject.put("group_category", groupCategory);
            jsonObject.put("group_name", upGroupInfo.getGroupName());
        }

        //修改或删除班组需要封装的参数
        if (method.equals(SuZhouConfig.GROUP_UPDATE) || method.equals(SuZhouConfig.GROUP_DELETE)) {
            //住建局返回的班组id
            jsonObject.put("id", upGroupInfo.getReportCode());
        }

        jsonArray.add(jsonObject);
        return jsonArray;
    }

    /**
    * @Description: 封装配置通用参数
    * @Param: [jsonObject, upParams]
    * @return: com.alibaba.fastjson.JSONObject
    * @Author: ysx
    * @Date: 2020/8/4
    */
    private JSONObject getConfig(JSONObject jsonObject, UpParams upParams) throws Exception {
        //获取token
        String token = this.getToken(upParams);

        jsonObject.put("token", token);
        jsonObject.put("app_id", upParams.getKey());
        jsonObject.put("app_secret", upParams.getSecret());

        //服务提供方提供的参建单位ID
        jsonObject.put("enterprise_id", upParams.getCollectKey());

        //服务提供方提供的项目ID
        jsonObject.put("project_id", upParams.getCollectSecret());

        //服务提供方提供的数据唯一标识
        jsonObject.put("data_partition_key", upParams.getAppToken());
        return jsonObject;
    }

    /**
    * @Description: 封装通用参数
    * @Param: [method]
    * @return: com.alibaba.fastjson.JSONObject
    * @Author: ysx
    * @Date: 2020/8/4
    */
    private JSONObject getUpLoadInfo(String method) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("state", SuZhouConfig.REPORT_STATE);
        jsonObject.put("useUrl", true);
        jsonObject.put("url", method);
        jsonObject.put("hash", SuZhouConfig.REPORT_HASH);
        jsonObject.put("block", true);
        return jsonObject;
    }

    /**
    * @Description: 通用方法，JSONObject转ByteBuf
    * @Param: [jsonObject]
    * @return: io.netty.buffer.ByteBuf
    * @Author: ysx
    * @Date: 2020/8/4
    */
    private ByteBuf getByteBuf(JSONObject jsonObject) {
        String data = jsonObject.toJSONString();
        byte[] bytes = data.getBytes();
        ByteBuf byteBuf = Unpooled.copiedBuffer(bytes);
        return byteBuf;
    }

    /**
    * @Description: 图片处理
    * @Param: [filePath, jsonObject, param]
    * @return: void
    * @Author: ysx
    * @Date: 2020/8/4
    */
    private void fileSolve(String filePath, JSONObject jsonObject, String param) throws Exception {
        if (!StringUtils.isEmpty(filePath)) {
            String idImage = SuZhouUtil.compressPicForScale(filePath,50);
            jsonObject.put(param, idImage);
        }
    }

}
