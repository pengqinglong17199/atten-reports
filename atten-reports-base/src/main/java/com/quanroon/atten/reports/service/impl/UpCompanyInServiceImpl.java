package com.quanroon.atten.reports.service.impl;

import com.google.common.collect.Maps;
import com.quanroon.atten.commons.enums.RepReturnCodeEnum;
import com.quanroon.atten.commons.exception.BusinessException;
import com.quanroon.atten.reports.common.ReportConstant;
import com.quanroon.atten.reports.common.ReportType;
import com.quanroon.atten.reports.dao.UpCompanyInMapper;
import com.quanroon.atten.reports.entity.UpCompanyIn;
import com.quanroon.atten.reports.entity.UpCompanyInfo;
import com.quanroon.atten.reports.entity.UpProjectInfo;
import com.quanroon.atten.reports.entity.dto.UpCompanyInDTO;
import com.quanroon.atten.reports.entity.example.UpCompanyInExample;
import com.quanroon.atten.reports.service.UpCompanyInService;
import com.quanroon.atten.reports.service.UpProjectInfoService;
import com.quanroon.atten.reports.service.UpRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author quanroon.ysq
 * @version 1.0.0
 * @content
 * @date 2020/6/30 20:04
 */
@Service
@Transactional(readOnly = true)
public class UpCompanyInServiceImpl implements UpCompanyInService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UpCompanyInMapper upCompanyInMapper;
    @Autowired
    private UpCompanyServiceImpl upCompanyInfoServiceImpl;
    @Autowired
    private UpRecordService upRecordServiceImpl;
    @Autowired
    private UpProjectInfoService upProjectInfoServiceImpl;

    /**
     * 保存企业（参建单位）进退场
     * @param upCompanyInDTO
     * @param upCompanyIn
     */
    @Override
    @Transactional(readOnly = false)
    public Map<String, Object> saveCompanyInOut(UpCompanyInDTO upCompanyInDTO, UpCompanyIn upCompanyIn) {
        Map<String, Object> hashMap = Maps.newHashMap();
        BeanUtils.copyProperties(upCompanyInDTO, upCompanyIn);

        //根据项目id+查询项目信息
        UpProjectInfo upProjectInfo = upProjectInfoServiceImpl.selectByPrimaryKey(upCompanyIn.getProjId());
        if(Objects.isNull(upProjectInfo))
            throw new BusinessException(RepReturnCodeEnum.REPORT_RETURN_200002);

        //根据企业社会信用代码查询是否存在进场前的企业信息
        UpCompanyInfo upCompanyInfo = new UpCompanyInfo();
        upCompanyInfo.setProjId(upCompanyIn.getProjId());
        upCompanyInfo.setCorpCode(upCompanyInDTO.getCorpCode());
        UpCompanyInfo companyInfo = upCompanyInfoServiceImpl.getCompanyByCodeName(upCompanyInfo);

        if(Objects.isNull(companyInfo))
            throw new BusinessException(RepReturnCodeEnum.REPORT_RETURN_310001);

        String requestCode = null;
        ReportType requestType = ReportType.company_enter;//默认进场
        //判断是否有进出场信息
        Integer companyId = companyInfo.getId();//企业信息id
        upCompanyIn.setCompanyId(companyId);

        UpCompanyInExample upCompanyInExample = new UpCompanyInExample();
        upCompanyInExample.createCriteria()
                .andCompanyIdEqualTo(companyId)
                .andProjIdEqualTo(upCompanyIn.getProjId());
        List<UpCompanyIn> upCompanyIns = upCompanyInMapper.selectByExample(upCompanyInExample);

        //用进场时间来判断是进场接口,还是离场接口
        if(Objects.isNull(upCompanyIn.getInDate())){
            //执行离场操作,如果未进场或已经离场,则退出
            if(CollectionUtils.isEmpty(upCompanyIns))
                throw new BusinessException(RepReturnCodeEnum.REPORT_RETURN_311002);

            //不允许重复离场
            if("1".equals(upCompanyIns.get(0).getStatus()))
                throw new BusinessException(RepReturnCodeEnum.REPORT_RETURN_312001);

            upCompanyIn.setId(upCompanyIns.get(0).getId());
            upCompanyIn.setStatus("1");//离场状态
            upCompanyInMapper.updateByExampleSelective(upCompanyIn,upCompanyInExample);
            logger.info("==> 企业(参建单位)离场成功");
            requestType = ReportType.company_leave;
        }else{
            //执行进场操作,如果未进场,则新增,如果已进场,则更新
            upCompanyIn.setStatus("0");

            if(CollectionUtils.isEmpty(upCompanyIns)){
                upCompanyInMapper.insertSelective(upCompanyIn);
                logger.info("==> 企业(参建单位)进场成功");
            }else {
                upCompanyIn.setId(upCompanyIns.get(0).getId());
                //不允许重复进场
                /*if("0".equals(upCompanyIns.get(0).getStatus()))
                    throw new BusinessException(RepReturnCodeEnum.REPORT_RETURN_311001);*/

                upCompanyInMapper.updateByExampleSelective(upCompanyIn,upCompanyInExample);
                logger.info("==> 企业(参建单位)修改进场成功");
            }
        }
        //保存上报记录
        requestCode = upRecordServiceImpl.initRequestCode(upCompanyIn.getCodeEntity(), requestType);
        hashMap.put("requestCode", requestCode);
        return hashMap;
    }
}
