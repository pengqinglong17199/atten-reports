package com.quanroon.atten.reports.service.impl;

import com.google.common.collect.Maps;
import com.quanroon.atten.commons.enums.RepReturnCodeEnum;
import com.quanroon.atten.commons.exception.BusinessException;
import com.quanroon.atten.commons.utils.StringUtils;
import com.quanroon.atten.reports.common.ReportConstant;
import com.quanroon.atten.reports.common.ReportType;
import com.quanroon.atten.reports.dao.UpCompanyInfoMapper;
import com.quanroon.atten.reports.entity.UpCompanyInfo;
import com.quanroon.atten.reports.entity.UpProjectInfo;
import com.quanroon.atten.reports.entity.example.UpCompanyInfoExample;
import com.quanroon.atten.reports.service.UpCompanyService;
import com.quanroon.atten.reports.service.UpProjectInfoService;
import com.quanroon.atten.reports.service.UpRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author quanroon.ysq
 * @version 1.0.0
 * @content
 * @date 2020/6/29 20:03
 */
@Service
@Transactional(readOnly = true)
public class UpCompanyServiceImpl implements UpCompanyService {

    @Autowired
    private UpProjectInfoService upProjectInfoServiceImpl;
    @Autowired
    private UpCompanyInfoMapper upCompanyInfoMapper;
    @Autowired
    private UpRecordService upRecordServiceImpl;
    /**
     * 保存/更新企业（参见单位）信息
     * @param upCompanyInfo
     */
    @Override
    @Transactional(readOnly = false)
    public Map<String, Object> saveCompanyInfo(UpCompanyInfo upCompanyInfo) {
        Map<String, Object> hashMap = Maps.newHashMap();
        try {
            //检查项目是否有上报过
            UpProjectInfo upProjectInfo = upProjectInfoServiceImpl.selectByPrimaryKey(upCompanyInfo.getProjId());
            if(Objects.isNull(upProjectInfo)){
                throw new BusinessException(RepReturnCodeEnum.REPORT_RETURN_200002);
            }

            //根据企业的统一社会信用代码进行判断
            UpCompanyInfo company = this.getCompanyByCodeName(upCompanyInfo);
            if(Objects.isNull(company)){
                //新增
                upCompanyInfo.setCreateDate(new Date());
                upCompanyInfoMapper.insert(upCompanyInfo);
                hashMap.put("companyId",upCompanyInfo.getId());//用于班组上报时使用
                //暂时先不产生消息，等有具体写在进行优化
            }else{
                upCompanyInfo.setId(company.getId());
                //修改
                upCompanyInfo.setUpdateDate(new Date());
                upCompanyInfoMapper.updateByPrimaryKeySelective(upCompanyInfo);
                //保存上报记录
                String requestCode = upRecordServiceImpl.initRequestCode(upCompanyInfo.getCodeEntity(), ReportType.company_update);
                hashMap.put("requestCode",requestCode);//这里是用于查询修改后，上报结果，并返回业务id
            }
            return hashMap;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("保存/更新企业（参见单位）信息发生异常");
        }
    }

    /**
     * 根据项目Id+统一社会信用代码,或企业名称查询，是否已存在信息
     * @param upCompanyInfo
     * @return
     */
    @Override
    public UpCompanyInfo getCompanyByCodeName(UpCompanyInfo upCompanyInfo) {
        UpCompanyInfoExample companyInfoExample = new UpCompanyInfoExample();
        companyInfoExample.createCriteria()
                .andCorpCodeEqualTo(upCompanyInfo.getCorpCode())
                .andProjIdEqualTo(upCompanyInfo.getProjId());
        if(Objects.nonNull(upCompanyInfo.getName()))
            companyInfoExample.createCriteria().andNameEqualTo(upCompanyInfo.getName());

        List<UpCompanyInfo> upCompanyInfos = upCompanyInfoMapper.selectByExample(companyInfoExample);
        if(upCompanyInfos != null && upCompanyInfos.size() > 0)
            return upCompanyInfos.get(0);
        return null;
    }
}
