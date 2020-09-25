package com.quanroon.atten.reports.api.web;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.quanroon.atten.commons.base.AjaxResult;
import com.quanroon.atten.commons.base.BaseApiController;
import com.quanroon.atten.commons.config.JWTConfiguration;
import com.quanroon.atten.commons.enums.RepReturnCodeEnum;
import com.quanroon.atten.commons.jwt.IJWTInfo;
import com.quanroon.atten.commons.jwt.JWTHelper;
import com.quanroon.atten.commons.jwt.JWTInfo;
import com.quanroon.atten.commons.utils.RedisUtils;
import com.quanroon.atten.commons.utils.StringUtils;
import com.quanroon.atten.reports.api.utils.BloomFilterRedis;
import com.quanroon.atten.reports.common.RecordStatus;
import com.quanroon.atten.reports.entity.*;
import com.quanroon.atten.reports.entity.dto.CityParamsDTO;
import com.quanroon.atten.reports.entity.dto.PlatformAuthDTO;
import com.quanroon.atten.reports.entity.dto.ProjAuthDTO;
import com.quanroon.atten.reports.entity.example.UpPlatformAuthExample;
import com.quanroon.atten.reports.entity.example.UpProjectAuthExample;
import com.quanroon.atten.reports.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 上报系统功能控制器
 * @author 彭清龙
 * @date 2020/6/29 10:13
 */
@RestController
@RequestMapping(value = "${server.adminPath}/sys")
public class ReportController extends BaseApiController {

    // 企业必填参数
    private String APP_ID = "appId";
    private String APP_KEY = "appKey";
    private String VERSION = "version";

    // 项目必填参数
    private String PROJ_ID = "projId";
    private String AUTH_KEY = "authKey";

    // token缓存前缀
    private String CACHE_TOKEN = ":token";

    @Autowired private JWTConfiguration jwtConfiguration;
    @Autowired private RedisUtils redisUtils;
    @Autowired private UpPlatformAuthService upPlatformAuthServiceImpl;
    @Autowired private UpProjectAuthService upProjectAuthServiceImpl;
    @Autowired private UpProjectInfoService upProjectInfoServiceImpl;
    @Autowired private UpRecordService upRecordServiceImpl;
    @Autowired private UpDynamicFieldService upDynamicFieldServiceImpl;
    @Autowired private BloomFilterRedis<String> bloomFilterRedis;

    /**
     * 获取企业token
     * @param platformAuthDTO
     * @return com.quanroon.atten.commons.base.AjaxResult
     * @author 彭清龙
     * @date 2020/6/30 15:04
     */
    @RequestMapping(value = "/token/company" ,method = RequestMethod.POST)
    public AjaxResult companyToken(@RequestBody PlatformAuthDTO platformAuthDTO){

        // 校验必填
        String paramEmpty = super.isParamEmpty(platformAuthDTO, APP_ID, APP_KEY, VERSION);
        if(StringUtils.isNotEmpty(paramEmpty)){
            return super.paramEmpty(paramEmpty);
        }
        try {
            //TODO 可以采用布隆过滤器进行校验密钥合法性
            String bfValue = platformAuthDTO.getAppId() + platformAuthDTO.getAppKey();
            if(!bloomFilterRedis.contains(bloomFilterRedis.BLOOM_SECRET_KEY, bfValue)){
                log.info("==> Bloom filter to valid of app key, it is not exist");
                return super.failed(RepReturnCodeEnum.REPORT_RETURN_200000);
            }
            // 进一步检验密钥的合法性
            UpPlatformAuthExample example = new UpPlatformAuthExample();
            example.createCriteria()
                    .andAppIdEqualTo(platformAuthDTO.getAppId())
                    .andAppKeyEqualTo(platformAuthDTO.getAppKey());
            List<UpPlatformAuth> platmeAuthList = upPlatformAuthServiceImpl.selectByExample(example);
            if (platmeAuthList.isEmpty()) {
                log.info("==> Database sql to valid of app key, it is error");
                return super.failed(RepReturnCodeEnum.REPORT_RETURN_200001);
            }

            // 校验企业是否停用
            UpPlatformAuth upPlatmeAuth = platmeAuthList.get(0);
            if(UpPlatformAuth.STATUS_STOP.equals(upPlatmeAuth.getStatus())){
                log.info("==> Auth key is stop use, please of contract seal");
                return super.failed(RepReturnCodeEnum.REPORT_RETURN_200011);
            }

            //缓存中取出token
            String key = UpPlatformAuth.CACHE_PREFIX + upPlatmeAuth.getCompanyId() +CACHE_TOKEN;
            String token = (String) redisUtils.get(key);
            long expire = redisUtils.getExpire(key);
            if (StringUtils.isEmpty(token)) {
                IJWTInfo jwtInfo = new JWTInfo(upPlatmeAuth.getCompanyName(), upPlatmeAuth.getCompanyId(), platformAuthDTO.getVersion());
                token = JWTHelper.generateToken(jwtInfo, jwtConfiguration.getPriKey(), jwtConfiguration.getCompanyExpire());
                expire = jwtConfiguration.getCompanyExpire();
                // 放入redis中缓存
                redisUtils.set(key, token, (jwtConfiguration.getCompanyExpire() - 1000));// 过期时间-1s 你懂的
            }
            // 封装返回数据
            Map map = Maps.newHashMap();
            map.put("token", token);
            map.put("expire", expire);

            return super.success(map);
        }catch (Exception e){
            log.error("生成token错误", e);
            return super.failed(RepReturnCodeEnum.REPORT_RETURN_999999);
        }
    }

    /**
     * 获取项目token
     * @param projAuthDTO, request
     * @return com.quanroon.atten.commons.base.AjaxResult
     * @author 彭清龙
     * @date 2020/6/30 15:11
     */
    @RequestMapping(value = "/token/proj", method = RequestMethod.POST)
    public AjaxResult projToken(@RequestBody ProjAuthDTO projAuthDTO, HttpServletRequest request){

        // 校验必填参数是否为空
        String paramEmpty = super.isParamEmpty(projAuthDTO, PROJ_ID, AUTH_KEY);
        if(StringUtils.isNotEmpty(paramEmpty)){
            return super.paramEmpty(paramEmpty);
        }

        // 设置token中携带的企业信息
        IJWTInfo info = (IJWTInfo) request.getAttribute(COMPANY_INFO);

        // 校验项目是否存在
        UpProjectInfo projectInfo = upProjectInfoServiceImpl.selectByPrimaryKey(projAuthDTO.getProjId());
        if(Objects.isNull(projectInfo))
            return super.failed(RepReturnCodeEnum.REPORT_RETURN_200002);

        //校验authKey密钥是否正确
        UpProjectAuthExample example = new UpProjectAuthExample();
        example.createCriteria()
                .andProjIdEqualTo(projAuthDTO.getProjId())
                .andAuthKeyEqualTo(projAuthDTO.getAuthKey());
        List<UpProjectAuth> authList = upProjectAuthServiceImpl.selectByExample(example);
        if(authList.isEmpty()){
            return super.failed(RepReturnCodeEnum.REPORT_RETURN_200003);
        }

        // 校验项目是否归属于该平台
        if(!projectInfo.getPlatformId().equals(info.getId())){
            return super.failed(RepReturnCodeEnum.REPORT_RETURN_200010);
        }

        try {
            // 从redis获取token
            String key = UpPlatformAuth.CACHE_PREFIX + projectInfo.getPlatformId() + UpProjectInfo.CACHE_PREFIX + projectInfo.getId() + CACHE_TOKEN;
            String token = (String) redisUtils.get(key);
            long expire = redisUtils.getExpire(key);
            // redis没有取到token就生成一个
            if (StringUtils.isEmpty(token)) {
                IJWTInfo jwtInfo = new JWTInfo(projectInfo.getName(), projectInfo.getId(), projAuthDTO.getVersion());
                token = JWTHelper.generateToken(jwtInfo, jwtConfiguration.getPriKey(), jwtConfiguration.getCompanyExpire());
                expire = jwtConfiguration.getCompanyExpire();
                // 放入redis中缓存
                redisUtils.set(key, token, (jwtConfiguration.getCompanyExpire() - 1000));// 过期时间-1s 你懂的
            }
            // 封装返回数据
            Map map = Maps.newHashMap();
            map.put("token", token);
            map.put("expire", expire);
            return super.success(map);
        }catch (Exception e){
            log.error("生成项目token错误", e);
            return super.failed(RepReturnCodeEnum.REPORT_RETURN_999999);
        }
    }

    /**
     * 通过上报唯一编码对上报结果进行查询
     * @param json
     * @return com.quanroon.atten.commons.base.AjaxResult
     * @author 彭清龙
     * @date 2020/6/30 19:55
     */
    @RequestMapping(value = "/result/query", method = RequestMethod.POST)
    public AjaxResult reportResult(@RequestBody JSONObject json){
        // 必填校验
        if (json == null || json.isEmpty() || StringUtils.isEmpty(json.getString("requestCode"))) {
            return super.reportParamEmpty("requestCode");
        }
        // 获取上报记录
        UpRecord record = upRecordServiceImpl.getRecord(json.getString("requestCode"));
        if(record ==null){
            return super.failed(RepReturnCodeEnum.REPORT_RETURN_300001);
        }
        // 封装上报结果
        Map map = Maps.newHashMap();

        // 请求requestCode对应的上报结果描述
        map.put("msg", record.getResult());

        // 请求requestCode对应的上报业务值
        map.put("dataId", record.getTableId());

        map.put("status", record.getStatus());



//        if(RecordStatus.SUCCESS.val().equals(record.getStatus())){
//            map.put("dataId", record.getResult());
//        }else if(RecordStatus.FAIL.val().equals(record.getStatus())){
//            map.put("msg", record.getResult());
//        }
        return super.success(map);
    }

    /**
     * 获取城市上报住建局的必填字段
     * @param cityParamsDto
     * @return com.quanroon.atten.commons.base.AjaxResult
     * @author 彭清龙
     * @date 2020/7/1 20:03
     */
    @RequestMapping(value = "/city/params", method = RequestMethod.POST)
    public AjaxResult cityParams(@RequestBody CityParamsDTO cityParamsDto, HttpServletRequest request){
        // 必填校验
        ArrayList<Field> fields = Lists.newArrayList(CityParamsDTO.class.getDeclaredFields());
        super.isReportParamEmpty(cityParamsDto,
                fields.stream().map(field -> field.getName()).collect(Collectors.toList()));

        IJWTInfo ijwtInfo = (IJWTInfo) request.getAttribute(COMPANY_INFO);//平台信息
        return super.success(upDynamicFieldServiceImpl.getDynamicFieldList(cityParamsDto,ijwtInfo));
    }
}
