package com.quanroon.atten.reports.job.shenzhen.shuiwuju;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.quanroon.atten.commons.utils.DateUtils;
import com.quanroon.atten.commons.utils.StringUtils;
import com.quanroon.atten.reports.common.ReportType;
import com.quanroon.atten.reports.dao.*;
import com.quanroon.atten.reports.entity.*;
import com.quanroon.atten.reports.entity.example.UpDictInfoExample;
import com.quanroon.atten.reports.entity.example.UpGroupInfoExample;
import com.quanroon.atten.reports.job.shenzhen.common.ShenZhenReportEnum;
import com.quanroon.atten.reports.job.shenzhen.common.ShenZhenResult;
import com.quanroon.atten.reports.job.shenzhen.shuiwuju.entity.ReportConstant;
import com.quanroon.atten.reports.job.shenzhen.shuiwuju.entity.ShuiWuJuConfig;
import com.quanroon.atten.reports.job.shenzhen.shuiwuju.utils.Base64ToMultipartFile;
import com.quanroon.atten.reports.job.shenzhen.shuiwuju.utils.ShuiWuJuUtils;
import com.quanroon.atten.reports.report.annotation.City;
import com.quanroon.atten.reports.report.annotation.ReportMethod;
import com.quanroon.atten.reports.report.constant.ReportCityCode;
import com.quanroon.atten.reports.report.entity.ReportResult;
import com.quanroon.atten.reports.report.entity.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 深圳水务局业务层
 *
 * @Author: ysx
 * @Date: 2020/8/12
 */

@Slf4j
@City(ReportCityCode.SHENZHEN)
public class ShenZhenSWJService implements ReportService {

    @Value("${shenzhen.shuiwuju.url:}")
    public String URL;

    @Autowired
    private UpCompanyInMapper upCompanyInMapper;
    @Autowired
    private UpCompanyInfoMapper upCompanyInfoMapper;
    @Autowired
    private UpParamsMapper upParamsMapper;
    @Autowired
    private UpGroupInfoMapper upGroupInfoMapper;
    @Autowired
    private UpWorkerInfoMapper upWorkerInfoMapper;
    @Autowired
    private UpWorkerInMapper upWorkerInMapper;
    @Autowired
    private UpWorkerSignlogInfoMapper upWorkerSignlogInfoMapper;
    @Autowired
    private UpFileMapper upFileMapper;
    @Autowired
    private UpDictReportMapper upDictReportMapper;
    @Autowired
    private UpDictInfoMapper upDictInfoMapper;

    /**
     * 时间戳格式
     */
    public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    /**
     * @Description: 上报参见单位及入场
     * @Param: [reportMessage]
     * @return: com.quanroon.atten.reports.report.common.ReportResult
     * @Author: ysx
     * @Date: 2020/8/12
     */
    @ReportMethod({ReportType.company_enter})
    public ReportResult reportCompany(ReportMessage reportMessage) {

        //深圳水务局上报结果
        ShenZhenResult shenZhenResult = new ShenZhenResult();

        try {
            //获取参建单位进场id
            Integer companyInId = Integer.parseInt(reportMessage.getDataId());

            //获取参见单位进场信息
            UpCompanyIn upCompanyIn = upCompanyInMapper.selectByPrimaryKey(companyInId);

            //获取参见单位信息
            UpCompanyInfo upCompanyInfo = upCompanyInfoMapper.selectByPrimaryKey(upCompanyIn.getCompanyId());

            //获取项目配置
            UpParams upParams = upParamsMapper.selectByProjId(upCompanyInfo.getProjId());

            //获取上报参见单位信息和入场信息
            String body = this.getUpCompayInfo(upParams, upCompanyInfo, upCompanyIn);

            //完整上报参数
            Map<String, Object> param = this.getParam(upParams, body);

            //参见单位入场
            String result = HttpUtil.post(URL + ShuiWuJuConfig.ADD_COMPANY, param);
            JSONObject object = JSON.parseObject(result);
            //code为00,则成功上报
            if (ShuiWuJuConfig.SUCCESS_CODE.equals(object.getString("code"))) {
                //返回上报成功结果
                shenZhenResult.setCode(ShuiWuJuConfig.SUCCESS_CODE);
                shenZhenResult.setMessage("参见单位上报成功");
                log.info("参见单位:" + upCompanyInfo.getName() + "上报成功");
            } else {
                //返回上报失败结果
                shenZhenResult.setCode(ShuiWuJuConfig.FAILURE_CODE);
                shenZhenResult.setMessage(object.getString("message"));
                log.info("参见单位:" + upCompanyInfo.getName() + "上报失败,失败原因:" + object.getString("message"));
            }

        } catch (Exception e) {
            shenZhenResult.setCode(ShuiWuJuConfig.FAILURE_CODE);
            shenZhenResult.setMessage("深圳水务局上报服务异常");
            log.error("深圳水务局上报服务异常", e);
        }

        return shenZhenResult;
    }

    /**
     * @Description: 上报班组及进场
     * @Param: [reportMessage]
     * @return: com.quanroon.atten.reports.report.entity.ReportResult
     * @Author: ysx
     * @Date: 2020/8/14
     */
    @ReportMethod(ReportType.group_report)
    public ReportResult reportGroup(ReportMessage reportMessage) {

        //深圳水务局上报结果
        ShenZhenResult shenZhenResult = new ShenZhenResult();

        try {
            //获取班组id
            Integer groupId = Integer.parseInt(reportMessage.getDataId());

            //获取班组基本信息
            UpGroupInfo upGroupInfo = upGroupInfoMapper.selectByPrimaryKey(groupId);

            //获取班组所在参见单位信息
            UpCompanyInfo upCompanyInfo = upCompanyInfoMapper.selectByPrimaryKey(upGroupInfo.getCompanyId());

            //获取项目配置
            UpParams upParams = upParamsMapper.selectByProjId(upGroupInfo.getProjId());

            //获取上报班组信息
            String body = this.getGroupInfo(upParams, upGroupInfo, upCompanyInfo);

            //完整上报参数
            Map<String, Object> param = this.getParam(upParams, body);

            //上报班组
            String result = HttpUtil.post(URL + ShuiWuJuConfig.ADD_GROUP, param);
            JSONObject object = JSON.parseObject(result);
            //code为00,则成功上报
            if (ShuiWuJuConfig.SUCCESS_CODE.equals(object.getString("code"))) {
                //深圳水务局上报班组返回的结果集
                JSONObject jsonObject = object.getJSONObject("result_data");
                //获取返回的班组id
                String teamId = jsonObject.getString("team_id");
                //获取返回的班组名称
                String teamName = jsonObject.getString("team_name");
                //teamId和teamName逗号分隔，保存在reportCode
                String reportCode = teamId + "," + teamName;

                //保存返回的班组id
                UpGroupInfoExample example = new UpGroupInfoExample();
                example.createCriteria().andIdEqualTo(upGroupInfo.getId());
                upGroupInfo.setReportCode(reportCode);
                upGroupInfoMapper.updateByExample(upGroupInfo, example);

                //返回上报成功结果
                shenZhenResult.setCode(ShuiWuJuConfig.SUCCESS_CODE);
                shenZhenResult.setMessage("班组上报成功");
                log.info("班组:" + upGroupInfo.getGroupName() + "上报成功");
            } else {
                //返回上报失败结果
                shenZhenResult.setCode(ShuiWuJuConfig.FAILURE_CODE);
                shenZhenResult.setMessage(object.getString("message"));
                log.info("班组:" + upGroupInfo.getGroupName() + "上报失败,失败原因:" + object.getString("message"));
            }
        } catch (Exception e) {
            shenZhenResult.setCode(ShuiWuJuConfig.FAILURE_CODE);
            shenZhenResult.setMessage("深圳水务局上报服务异常");
            log.error("深圳水务局上报服务异常", e);
        }

        return shenZhenResult;
    }

    /**
     * @Description: 上报劳工及进场
     * @Param: [reportMessage]
     * @return: com.quanroon.atten.reports.report.entity.ReportResult
     * @Author: ysx
     * @Date: 2020/8/14
     */
    @ReportMethod(ReportType.worker_enter)
    public ReportResult reportWorker(ReportMessage reportMessage) {

        //深圳水务局上报结果
        ShenZhenResult shenZhenResult = new ShenZhenResult();

        try {
            //获取劳工进场id
            Integer workerInId = Integer.parseInt(reportMessage.getDataId());

            //获取劳工进场信息
            UpWorkerIn upWorkerIn = upWorkerInMapper.selectByPrimaryKey(workerInId);

            //获取劳工信息
            UpWorkerInfo upWorkerInfo = upWorkerInfoMapper.selectByPrimaryKey(upWorkerIn.getWorkerId());

            //获取劳工所在班组信息
            UpGroupInfo upGroupInfo = upGroupInfoMapper.selectByPrimaryKey(upWorkerIn.getGroupId());

            //获取班组所在参见单位信息
            UpCompanyInfo upCompanyInfo = upCompanyInfoMapper.selectByPrimaryKey(upGroupInfo.getCompanyId());

            //获取项目配置
            UpParams upParams = upParamsMapper.selectByProjId(upWorkerInfo.getProjId());

            //获取上报劳工信息和进场信息
            String body = this.getWorkerInfo(upParams, upWorkerInfo, upWorkerIn, upGroupInfo, upCompanyInfo);

            //完整上报参数
            Map<String, Object> param = this.getParam(upParams, body);

            //上报劳工
            String result = HttpUtil.post(URL + ShuiWuJuConfig.ADD_WORKER, param);
            JSONObject object = JSON.parseObject(result);
            //code为00,则成功上报
            if (ShuiWuJuConfig.SUCCESS_CODE.equals(object.getString("code"))) {
                //todo 深圳水务局接口实际没有返回值
                /*
                //深圳水务局上报班组返回的结果集
                JSONObject jsonObject = object.getJSONObject("result_data");
                //返回的员工工号
                String empId = jsonObject.getString("emp_id");
                //返回的通行时段
                String passPeriod = jsonObject.getString("pass_period");
                //返回的修改时间
                String modifyTime = jsonObject.getString("modify_time");
                //将员工工号、通行时段、修改时间保存在reportCode,逗号隔开
                String reportCode = empId + "," + passPeriod + "," + modifyTime;

                //保存reportCode
                upWorkerIn.setReportCode(reportCode);
                upWorkerInMapper.updateResponseCodeByWorkerId(upWorkerInfo.getId(), reportCode);
                */

                //返回上报成功结果
                shenZhenResult.setCode(ShuiWuJuConfig.SUCCESS_CODE);
                shenZhenResult.setMessage("劳工上报成功");
                log.info("劳工:" + upWorkerInfo.getName() + "上报成功");
            } else {
                //返回上报失败结果
                shenZhenResult.setCode(ShuiWuJuConfig.FAILURE_CODE);
                shenZhenResult.setMessage(object.getString("message"));
                log.info("劳工:" + upWorkerInfo.getName() + "上报失败,失败原因:" + object.getString("message"));
            }
        } catch (Exception e) {
            shenZhenResult.setCode(ShuiWuJuConfig.FAILURE_CODE);
            shenZhenResult.setMessage("深圳水务局上报服务异常");
            log.error("深圳水务局上报服务异常", e);
        }

        return shenZhenResult;
    }

    /**
     * @Description: 上报劳工离场
     * @Param: [reportMessage]
     * @return: com.quanroon.atten.reports.report.entity.ReportResult
     * @Author: ysx
     * @Date: 2020/8/14
     */
    @ReportMethod(ReportType.worker_leave)
    public ReportResult workerLeave(ReportMessage reportMessage) {

        //深圳水务局上报结果
        ShenZhenResult shenZhenResult = new ShenZhenResult();

        try {
            //获取劳工进场id
            Integer workerInId = Integer.parseInt(reportMessage.getDataId());

            //获取劳工进场信息
            UpWorkerIn upWorkerIn = upWorkerInMapper.selectByPrimaryKey(workerInId);

            //获取劳工信息
            UpWorkerInfo upWorkerInfo = upWorkerInfoMapper.selectByPrimaryKey(upWorkerIn.getWorkerId());

            //获取项目配置
            UpParams upParams = upParamsMapper.selectByProjId(upWorkerIn.getProjId());

            //获取上报劳工离场信息
            String body = this.getWorkerLeave(upParams, upWorkerIn, upWorkerInfo);

            //完整上报参数
            Map<String, Object> param = this.getParam(upParams, body);

            //上报劳工离场
            String result = HttpUtil.post(URL + ShuiWuJuConfig.WORKER_LEAVE, param);
            JSONObject object = JSON.parseObject(result);
            //code为00,则成功上报
            if (ShuiWuJuConfig.SUCCESS_CODE.equals(object.getString("code"))) {
                //返回上报成功结果
                shenZhenResult.setCode(ShuiWuJuConfig.SUCCESS_CODE);
                shenZhenResult.setMessage("劳工离场成功");
                log.info("劳工:" + upWorkerInfo.getName() + "离场成功");
            } else {
                //返回上报失败结果
                shenZhenResult.setCode(ShuiWuJuConfig.FAILURE_CODE);
                shenZhenResult.setMessage(object.getString("message"));
                log.info("劳工:" + upWorkerInfo.getName() + "离场失败,失败原因:" + object.getString("message"));
            }
        } catch (Exception e) {
            shenZhenResult.setCode(ShuiWuJuConfig.FAILURE_CODE);
            shenZhenResult.setMessage("深圳水务局上报服务异常");
            log.error("深圳水务局上报服务异常", e);
        }

        return shenZhenResult;
    }

    /**
     * @Description: 上报考勤
     * @Param: [reportMessage]
     * @return: com.quanroon.atten.reports.report.entity.ReportResult
     * @Author: ysx
     * @Date: 2020/8/14
     */
    @ReportMethod(ReportType.worker_signlog)
    public ReportResult reportSignlog(ReportMessage reportMessage) {

        //深圳水务局上报结果
        ShenZhenResult shenZhenResult = new ShenZhenResult();

        try {
            //获取考勤id
            Integer signlogId = Integer.parseInt(reportMessage.getDataId());

            //获取考勤信息
            UpWorkerSignlogInfo upWorkerSignlogInfo = upWorkerSignlogInfoMapper.selectByPrimaryKey(signlogId);

            //获取劳工信息
            UpWorkerInfo upWorkerInfo = upWorkerInfoMapper.selectByPrimaryKey(upWorkerSignlogInfo.getWorkerId());

            //获取项目配置
            UpParams upParams = upParamsMapper.selectByProjId(upWorkerSignlogInfo.getProjId());

            //获取上报考勤数据
            String body = this.getSignlogInfo(upParams, upWorkerSignlogInfo, upWorkerInfo);

            //完整上报参数
            Map<String, Object> param = this.getParam(upParams, body);

            //上报考勤数据
            String result = HttpUtil.post(URL + ShuiWuJuConfig.ADD_SIGNLOG, param);
            JSONObject object = JSON.parseObject(result);
            //code为00,则成功上报
            if (ShuiWuJuConfig.SUCCESS_CODE.equals(object.getString("code"))) {
                //返回上报成功结果
                shenZhenResult.setCode(ShuiWuJuConfig.SUCCESS_CODE);
                shenZhenResult.setMessage("考勤上报成功");
                log.info("劳工:" + upWorkerInfo.getName() + "考勤上报成功");
            } else {
                //返回上报失败结果
                shenZhenResult.setCode(ShuiWuJuConfig.FAILURE_CODE);
                shenZhenResult.setMessage(object.getString("message"));
                log.info("劳工:" + upWorkerInfo.getName() + "考勤上报失败,失败原因:" + object.getString("message"));
            }
        } catch (Exception e) {
            shenZhenResult.setCode(ShuiWuJuConfig.FAILURE_CODE);
            shenZhenResult.setMessage("深圳水务局上报服务异常");
            log.error("深圳水务局上报服务异常", e);
        }

        return shenZhenResult;
    }

    /**
     * @Description: 上报参见单位离场
     * @Param: [reportMessage]
     * @return: com.quanroon.atten.reports.report.entity.ReportResult
     * @Author: ysx
     * @Date: 2020/8/14
     */
    @ReportMethod(ReportType.company_leave)
    public ReportResult companyLeave(ReportMessage reportMessage) {

        //深圳水务局上报结果
        ShenZhenResult shenZhenResult = new ShenZhenResult();

        try {
            //获取参见单位退场id
            Integer companyInId = Integer.parseInt(reportMessage.getDataId());

            //获取参见单位退场信息
            UpCompanyIn upCompanyIn = upCompanyInMapper.selectByPrimaryKey(companyInId);

            //获取项目配置
            UpParams upParams = upParamsMapper.selectByProjId(upCompanyIn.getProjId());

            //获取参见单位信息
            UpCompanyInfo upCompanyInfo = upCompanyInfoMapper.selectByPrimaryKey(upCompanyIn.getCompanyId());

            //获取上报参见单位离场信息
            String body = this.getCompanyLeave(upParams, upCompanyIn, upCompanyInfo);

            //完整上报参数
            Map<String, Object> param = this.getParam(upParams, body);

            //上报参见单位离场
            String result = HttpUtil.post(URL + ShuiWuJuConfig.COMPANY_LEAVE, param);
            JSONObject object = JSON.parseObject(result);
            //code为00,则成功上报
            if (ShuiWuJuConfig.SUCCESS_CODE.equals(object.getString("code"))) {
                //返回上报成功结果
                shenZhenResult.setCode(ShuiWuJuConfig.SUCCESS_CODE);
                shenZhenResult.setMessage("参见单位离场成功");
                log.info("参见单位:" + upCompanyInfo.getName() + "参见单位离场成功");
            } else {
                //返回上报失败结果
                shenZhenResult.setCode(ShuiWuJuConfig.FAILURE_CODE);
                shenZhenResult.setMessage(object.getString("message"));
                log.info("参见单位:" + upCompanyInfo.getName() + "参见单位离场失败,失败原因:" + object.getString("message"));
            }
        } catch (Exception e) {
            shenZhenResult.setCode(ShuiWuJuConfig.FAILURE_CODE);
            shenZhenResult.setMessage("深圳水务局上报服务异常");
            log.error("深圳水务局上报服务异常", e);
        }

        return shenZhenResult;
    }

    /**
     * @Description: 图片上传
     * @Param: [path]
     * @return: void
     * @Author: ysx
     * @Date: 2020/8/14
     */
    public String reportImage(String path, UpParams upParams) {
        String imageId = null;

        try {
            //上传图片请求地址
            String url = URL + ShuiWuJuConfig.UPLOAD_IMAGE;

            //需要上传的文件
            File file = new File(path);

            //上传图片
            String result = Base64ToMultipartFile.reportImage(url, file, upParams);
            JSONObject object = JSON.parseObject(result);
            //code为00,则成功上报
            if (ShuiWuJuConfig.SUCCESS_CODE.equals(object.getString("code"))) {
                //返回的图片Id
                imageId = object.getJSONObject("result_data").getString("image_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return imageId;
    }

    /**
     * @Description: 参见单位离场数据封装
     * @Param: [upParams, upCompanyIn, upCompanyInfo]
     * @return: java.lang.String
     * @Author: ysx
     * @Date: 2020/8/14
     */
    public String getCompanyLeave(UpParams upParams, UpCompanyIn upCompanyIn, UpCompanyInfo upCompanyInfo) {
        JSONObject object = new JSONObject();

        //工程编号
        object.put("Project_ID", upParams.getProjectCode());
        //社会统一信用代码
        object.put("SUID", upCompanyInfo.getCorpCode());
        //企业名称
        object.put("Company_Name", upCompanyInfo.getName());
        //离场时间
        object.put("exit_time", sdf.format(upCompanyIn.getOutDate()));

        return object.toJSONString();
    }

    /**
     * @Description: 上报考勤数据封装
     * @Param: [upParams, upWorkerSignlogInfo, upWorkerInfo]
     * @return: java.lang.String
     * @Author: ysx
     * @Date: 2020/8/14
     */
    public String getSignlogInfo(UpParams upParams, UpWorkerSignlogInfo upWorkerSignlogInfo, UpWorkerInfo upWorkerInfo) {
        JSONObject object = new JSONObject();

        //工程编号
        object.put("Project_ID", upParams.getProjectCode());
        //门禁设备序列号
        object.put("Device_ID", upWorkerSignlogInfo.getDeviceSn());

        //通行日志数组具体参数
        JSONArray array = new JSONArray();
        JSONObject signlog = new JSONObject();

        //数据的唯一id
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String dataId = upParams.getKey() + uuid;
        signlog.put("data_id", dataId);

        //身份证号
        signlog.put("person_id", upWorkerInfo.getCardNo());
        //人员姓名
        signlog.put("person_name", upWorkerInfo.getName());
        //通过时间
        signlog.put("passed_time", sdf.format(upWorkerSignlogInfo.getTime()));
        //通行方向
        String direction = upWorkerSignlogInfo.getDirection().equals(ReportConstant.DIRECTION_IN_REPORTAPI)
                ? ReportConstant.DIRECTION_IN : ReportConstant.DIRECTION_OUT;
        signlog.put("direction", direction);
        //通行方式
        signlog.put("way", ReportConstant.PASS_WAY);

        //获取考勤照片
        UpFile idPhotoFile = upFileMapper.selectByTableNameAndTableIdAndModule(ReportConstant.TABLE_SIGNLOG,
                upWorkerSignlogInfo.getId(), ReportConstant.MODULE_SIGNLOG_PHOTO);
        String sitePhoto = this.reportImage(idPhotoFile.getFilePath(), upParams);
        //图片id,接口返回
        signlog.put("site_photo", sitePhoto);
        array.add(signlog);

        //通行日志数组
        object.put("passedlog_list", array);

        return object.toJSONString();
    }

    /**
     * @Description: 上报劳工离场数据封装
     * @Param: [upParams, upWorkerIn, upWorkerInfo]
     * @return: java.lang.String
     * @Author: ysx
     * @Date: 2020/8/14
     */
    public String getWorkerLeave(UpParams upParams, UpWorkerIn upWorkerIn, UpWorkerInfo upWorkerInfo) {
        JSONObject object = new JSONObject();

        //工程编号
        object.put("Project_ID", upParams.getProjectCode());

        //用户离场数组具体参数
        JSONArray array = new JSONArray();
        JSONObject worker = new JSONObject();
        //身份证号码
        worker.put("id_code", upWorkerInfo.getCardNo());
        //离场时间
        worker.put("exit_time", sdf.format(upWorkerIn.getLeaveDate()));
        array.add(worker);
        //用户离场数组
        object.put("userLeaveProject_list", array);

        return object.toJSONString();
    }

    /**
     * @Description: 上报劳工数据封装
     * @Param: [upParams, upWorkerInfo, upWorkerIn]
     * @return: java.lang.String
     * @Author: ysx
     * @Date: 2020/8/14
     */
    public String getWorkerInfo(UpParams upParams, UpWorkerInfo upWorkerInfo, UpWorkerIn upWorkerIn, UpGroupInfo upGroupInfo, UpCompanyInfo upCompanyInfo) {
        JSONObject object = new JSONObject();

        //工程编号
        object.put("Project_ID", upParams.getProjectCode());
        //身份证号，劳工唯一标识
        object.put("id_code", upWorkerInfo.getCardNo());

        //获取身份证头像
        UpFile idPhotoFile = upFileMapper.selectByTableNameAndTableIdAndModule(ReportConstant.TABLE_WORKER,upWorkerInfo.getId(),
                ReportConstant.MODULE_ID_PHOTO);
        String idPhoto = this.reportImage(idPhotoFile.getFilePath(), upParams);
        //身份证头像id，接口返回
        object.put("id_photo", idPhoto);

        //员工姓名
        object.put("emp_name", upWorkerInfo.getName());
        //手机号
        object.put("emp_phone", upWorkerInfo.getMobile());
        //身份证地址
        object.put("emp_nativeplace", upWorkerInfo.getCardNoAddress());

        //民族
        UpDictInfoExample dictInfoExample = new UpDictInfoExample();
        dictInfoExample.createCriteria().andDictTypeEqualTo(ReportConstant.NATION_TYPE).andDictValueEqualTo(upWorkerInfo.getNation());
        List<UpDictInfo> upDictInfos = upDictInfoMapper.selectByExample(dictInfoExample);
        String nation = CollectionUtils.isEmpty(upDictInfos) ? ReportConstant.NATION_DEFAULT : upDictInfos.get(0).getName();
        object.put("emp_nation", nation);

        //入场时间
        object.put("pass_period", sdf.format(upWorkerIn.getEntryDate()));
        //匹配标识
        object.put("match_flag", ReportConstant.MATCH_SUCCESS_FLAG);

        //获取人脸照片
        UpFile facePhotoFile = upFileMapper.selectByTableNameAndTableIdAndModule(ReportConstant.TABLE_WORKER,upWorkerInfo.getId(),
                ReportConstant.MODULE_FACE_PHOTO);
        String facePhoto = this.reportImage(facePhotoFile.getFilePath(), upParams);
        //人员照片id，接口返回
        object.put("facephoto", facePhoto);

        //所属单位
        object.put("emp_company", upParams.getCollectSecret());
        //社会统一信用代码
        object.put("SUID", upCompanyInfo.getCorpCode());

        //班组id和班组名称在reportCode当中,逗号分隔
        String[] reportCode = upGroupInfo.getReportCode().split(",");
        String teamId = reportCode[0];
        String teamName = reportCode[1];
        //班组id
        object.put("team_id", teamId);
        //班组的名称
        object.put("team_name", teamName);

        UpDictReport workerType = upDictReportMapper.selectDict(ReportCityCode.SHENZHEN.code(), ShenZhenReportEnum.SHUIWUJU.code(),
                ReportConstant.WORKER_TYPE, upWorkerIn.getMemberType());
        if (Objects.isNull(workerType)) {
            //匹配不上，默认劳务工人
            object.put("emp_category", ReportConstant.WORKER_CODE);
        } else {
            //人员类型:接口获取
            object.put("emp_category", workerType.getCode());
        }

        //先获取当前班组的参见单位的参见类型
        UpCompanyIn upCompanyIn = upCompanyInMapper.selectByCompanyId(upCompanyInfo.getId());
        String companyCode;
        String companyValue;
        UpDictReport companyType = upDictReportMapper.selectDict(ReportCityCode.SHENZHEN.code(), ShenZhenReportEnum.SHUIWUJU.code(),
                ReportConstant.COMPANY_TYPE, upCompanyIn.getBuildType());
        if (Objects.isNull(companyType)) {
            companyCode = ReportConstant.OTHER_COMPANY_CODE;
            companyValue = ReportConstant.OTHER_COMPANY_VALUE;
        } else {
            companyCode = companyType.getCode();
            companyValue = companyType.getDictType();
        }

        //工种匹配，注意：深圳水务局是对应参见单位类型对应人员岗位类型的选择
        UpDictReport jobType = upDictReportMapper.selectDictByOther(ReportCityCode.SHENZHEN.code(), ShenZhenReportEnum.SHUIWUJU.code(),
                ReportConstant.JOB_TYPE, upWorkerIn.getWorkType(), companyCode, companyValue);
        //匹配不上则从对应参见单位类型选择其中一个岗位
        if (Objects.isNull(jobType)) {
            UpDictReport defaultJob = upDictReportMapper.selectDictByOtherAndValue(ReportCityCode.SHENZHEN.code(), ShenZhenReportEnum.SHUIWUJU.code(),
                    ReportConstant.JOB_TYPE, companyCode, companyValue);
            //当参见类型为其他时，默认其他
            if(Objects.isNull(defaultJob)){
                object.put("job_order", ReportConstant.OTHER_JOB_CODE);
                object.put("job_name", ReportConstant.OTHER_JOB);
            }else{
                object.put("job_order", defaultJob.getCode());
                object.put("job_name", defaultJob.getDictType());
            }
        } else {
            //工种编号:接口获取
            object.put("job_order", jobType.getCode());
            //工种名称:接口获取
            object.put("job_name", jobType.getDictType());
        }

        //人员和参加单位是否已经签合同，默认已签合同
        object.put("contract_status", ReportConstant.HAS_SIGN_CONTRACT);
        //签发机关
        object.put("id_agency", upWorkerInfo.getCardNoIssuing());

        //拼接身份证有效期限
        String cardNoStartDate = DateUtils.formatDate(upWorkerInfo.getCardNoStartDate(), "yyyy.MM.dd");
        String cardNoEndDate = DateUtils.formatDate(upWorkerInfo.getCardNoEndDate(), "yyyy.MM.dd");
        String idValidDate = cardNoStartDate + "-" + cardNoEndDate;
        //身份证有效期限
        object.put("id_validdate", idValidDate);

        return object.toJSONString();
    }

    /**
     * @Description: 上报班组数据封装
     * @Param: [upParams, upGroupInfo, upCompanyInfo]
     * @return: java.lang.String
     * @Author: ysx
     * @Date: 2020/8/14
     */
    public String getGroupInfo(UpParams upParams, UpGroupInfo upGroupInfo, UpCompanyInfo upCompanyInfo) {
        JSONObject object = new JSONObject();

        //工程编号
        object.put("Project_ID", upParams.getProjectCode());
        //班组名称
        object.put("team_name", upGroupInfo.getGroupName());

        //班组匹配
        UpDictReport groupType = upDictReportMapper.selectDict(ReportCityCode.SHENZHEN.code(), ShenZhenReportEnum.SHUIWUJU.code(),
                ReportConstant.GROUP_TYPE, upGroupInfo.getGroupType());
        if (Objects.isNull(groupType)) {
            //匹配不上，默认其它
            object.put("team_type_order", ReportConstant.OTHER_GROUP_CODE);
        } else {
            //班组类型:接口获取
            object.put("team_type_order", groupType.getCode());
        }

        //所属单位名称
        object.put("emp_company", upParams.getCollectSecret());
        //社会统一信用代码
        object.put("SUID", upCompanyInfo.getCorpCode());
        //班组类型,建筑工人班组:00,管理人员班组:01,默认建筑工人班组
        object.put("team_personnel_type", ReportConstant.WORKER_GROUP);

        return object.toJSONString();
    }

    /**
     * @Description: 上报参见单位数据封装
     * @Param: [upParams, upCompanyInfo, upCompanyIn]
     * @return: java.lang.String
     * @Author: ysx
     * @Date: 2020/8/14
     */
    public String getUpCompayInfo(UpParams upParams, UpCompanyInfo upCompanyInfo, UpCompanyIn upCompanyIn) {
        JSONObject object = new JSONObject();
        //工程编号
        object.put("Project_ID", upParams.getProjectCode());
        //企业名称
        object.put("Company_Name", upCompanyInfo.getName());
        //社会统一信用代码
        object.put("SUID", upCompanyInfo.getCorpCode());
        //法定代表人
        object.put("Legal_Person", upCompanyInfo.getLegalName());

        //参见单位类型匹配
        UpDictReport companyType = upDictReportMapper.selectDict(ReportCityCode.SHENZHEN.code(), ShenZhenReportEnum.SHUIWUJU.code(),
                ReportConstant.COMPANY_TYPE, upCompanyIn.getBuildType());
        if (Objects.isNull(companyType)) {
            //匹配不上，默认其它
            object.put("Type", ReportConstant.OTHER_COMPANY_CODE);
        } else {
            //参见单位类型:接口获取
            object.put("Type", companyType.getCode());
        }

        //入场时间
        object.put("entry_time", sdf.format(upCompanyIn.getInDate()));
        return object.toJSONString();
    }

    /**
     * @Description: 通用参数封装
     * @Param: [object]
     * @return: com.alibaba.fastjson.JSONObject
     * @Author: ysx
     * @Date: 2020/8/12
     */
    public Map<String, Object> getParam(UpParams upParams, String body) throws Exception {
        Map<String, Object> param = new HashMap<>();
        //授权账号
        param.put("api_key", upParams.getKey());
        //接口协议版本
        param.put("api_version", ShuiWuJuConfig.API_VERSION);
        //水务局兼容旧系统所需参数，与api_key值相同
        param.put("client_serial", upParams.getKey());
        //时间戳
        String timestamp = sdf.format(new Date());
        param.put("timestamp", timestamp);
        //签名
        String signature = ShuiWuJuUtils.getSignature(upParams.getSecret(), body, upParams.getKey(),
                ShuiWuJuConfig.API_VERSION, upParams.getKey(), timestamp);
        param.put("signature", signature);
        //应用参数
        if(StringUtils.isNotEmpty(body)){
            param.put("body", body);
        }

        //打印上报参数
        log.info(param.toString());
        return param;
    }

    /**
     * @Description: 获取深圳水务局工种字典
     * @Param: [upParams]
     * @return: void
     * @Author: ysx
     * @Date: 2020/8/14
     */
    public void getJobName(UpParams upParams) throws Exception {

        //封装获取字典参数
        String body = null;
        Map<String, Object> param = this.getParam(upParams, body);

        //获取工种
        String result = ShuiWuJuUtils.post(URL + ShuiWuJuConfig.JOB_NAME, param);
        JSONObject object = JSON.parseObject(result);
        //code为00,则成功上报
        if (ShuiWuJuConfig.SUCCESS_CODE.equals(object.getString("code"))) {
            //保存字典
            this.saveUpDictReport(object, ReportConstant.JOB_TYPE);
            log.info("工种字典获取成功");
        } else {
            log.info("工种字典获取失败,失败原因:" + object.getString("message"));
        }
    }

    /**
     * @Description: 获取深圳水务局单位类型字典
     * @Param: [upParams]
     * @return: void
     * @Author: ysx
     * @Date: 2020/8/15
     */
    public void getCompanyType(UpParams upParams) throws Exception {

        //封装获取字典参数
        String body = null;
        Map<String, Object> param = this.getParam(upParams, body);

        //获取单位类型
        String result = ShuiWuJuUtils.post(URL + ShuiWuJuConfig.COMPANY_TYPE, param);

        JSONObject object = JSON.parseObject(result);
        //code为00,则成功上报
        if (ShuiWuJuConfig.SUCCESS_CODE.equals(object.getString("code"))) {
            //保存字典
            this.saveUpDictReport(object, ReportConstant.COMPANY_TYPE);
            log.info("单位类型字典获取成功");
        } else {
            log.info("单位类型字典获取失败,失败原因:" + object.getString("message"));
        }
    }

    /**
     * @Description: 获取深圳水务局班组类型字典
     * @Param: [upParams]
     * @return: void
     * @Author: ysx
     * @Date: 2020/8/15
     */
    public void getGroupType(UpParams upParams) throws Exception {
        //封装获取字典参数
        String body = null;
        Map<String, Object> param = this.getParam(upParams, body);

        //获取班组类型
        String result = ShuiWuJuUtils.post(URL + ShuiWuJuConfig.GROUP_TYPE, param);

        JSONObject object = JSON.parseObject(result);
        //code为00,则成功上报
        if (ShuiWuJuConfig.SUCCESS_CODE.equals(object.getString("code"))) {
            //保存字典
            this.saveUpDictReport(object, ReportConstant.GROUP_TYPE);
            log.info("班组类型字典获取成功");
        } else {
            log.info("班组类型字典获取失败,失败原因:" + object.getString("message"));
        }
    }

    /**
     * @Description: 获取深圳水务局人员类型字典
     * @Param: [upParams]
     * @return: void
     * @Author: ysx
     * @Date: 2020/8/15
     */
    public void getWorkerType(UpParams upParams) throws Exception {
        //封装获取字典参数
        String body = null;
        Map<String, Object> param = this.getParam(upParams, body);

        //获取人员类型
        String result = ShuiWuJuUtils.post(URL + ShuiWuJuConfig.WORKER_TYPE, param);

        JSONObject object = JSON.parseObject(result);
        //code为00,则成功上报
        if (ShuiWuJuConfig.SUCCESS_CODE.equals(object.getString("code"))) {
            //保存字典
            this.saveUpDictReport(object, ReportConstant.WORKER_TYPE);
            log.info("人员类型字典获取成功");
        } else {
            log.info("人员类型字典获取失败,失败原因:" + object.getString("message"));
        }
    }

    /**
     * @Description: 保存字典
     * @Param: [object]
     * @return: void
     * @Author: ysx
     * @Date: 2020/8/17
     */
    public void saveUpDictReport(JSONObject object, String type) {
        JSONObject resultData = object.getJSONObject("result_data");
        JSONArray dictList = resultData.getJSONArray("dict_list");

        //遍历所有字典
        List<UpDictReport> upDictReports = new ArrayList<>();
        for (int i = 0; i < dictList.size(); i++) {
            //需要保存的字典
            String orders = dictList.getJSONObject(i).getString("orders");
            String value = dictList.getJSONObject(i).getString("value");
            String corpInfoType = StringUtils.isEmpty(dictList.getJSONObject(i).getString("corpInfoType")) ?
                    null : dictList.getJSONObject(i).getString("corpInfoType");
            String corpInfoTypeName = StringUtils.isEmpty(dictList.getJSONObject(i).getString("corpInfoTypeName")) ?
                    null : dictList.getJSONObject(i).getString("corpInfoTypeName");

            //封装字典
            UpDictReport upDictReport = this.getUpDictReport(orders, value, type, corpInfoType, corpInfoTypeName);
            upDictReports.add(upDictReport);
        }
        //保存字典
        if (!CollectionUtil.isEmpty(upDictReports)) {
            upDictReportMapper.insertDictUpReports(upDictReports);
        }
    }

    /**
     * @Description: 住建局字典数据封装
     * @Param: [orders, value, type]
     * @return: com.quanroon.atten.reports.entity.UpDictReport
     * @Author: ysx
     * @Date: 2020/8/17
     */
    UpDictReport getUpDictReport(String orders, String value, String type, String otherCode, String otherValue) {
        UpDictReport upDictReport = new UpDictReport();
        upDictReport.setCode(orders);
        upDictReport.setDictType(value);
        upDictReport.setApiType(type);
        upDictReport.setReportCityCode(ReportCityCode.SHENZHEN.code());
        upDictReport.setReportType(ShenZhenReportEnum.SHUIWUJU.code());
        upDictReport.setCreateDate(new Date());
        upDictReport.setOtherCode(otherCode);
        upDictReport.setOtherValue(otherValue);
        return upDictReport;
    }

}
