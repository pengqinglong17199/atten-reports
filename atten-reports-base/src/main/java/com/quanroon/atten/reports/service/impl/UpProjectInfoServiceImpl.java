package com.quanroon.atten.reports.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.quanroon.atten.commons.exception.BusinessException;
import com.quanroon.atten.commons.utils.DateUtils;
import com.quanroon.atten.commons.utils.StringUtils;
import com.quanroon.atten.reports.common.ReportConstant;
import com.quanroon.atten.reports.common.ReportType;
import com.quanroon.atten.reports.dao.UpProjectCertificateMapper;
import com.quanroon.atten.reports.dao.UpProjectCompanyMapper;
import com.quanroon.atten.reports.dao.UpProjectInfoMapper;
import com.quanroon.atten.reports.dao.UpSalaryInfoMapper;
import com.quanroon.atten.reports.entity.UpProjectCertificate;
import com.quanroon.atten.reports.entity.UpProjectCompany;
import com.quanroon.atten.reports.entity.UpProjectInfo;
import com.quanroon.atten.reports.entity.UpSalaryInfo;
import com.quanroon.atten.reports.entity.dto.UpBengbuProjDTO;
import com.quanroon.atten.reports.entity.dto.UpProjectCertificateDTO;
import com.quanroon.atten.reports.entity.dto.UpProjectInfoDTO;
import com.quanroon.atten.reports.entity.example.UpProjectCertificateExample;
import com.quanroon.atten.reports.entity.example.UpProjectInfoExample;
import com.quanroon.atten.reports.service.UpProjectInfoService;
import com.quanroon.atten.reports.service.UpRecordService;
import com.quanroon.atten.reports.service.UpSalaryInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class UpProjectInfoServiceImpl implements UpProjectInfoService {

    @Autowired private UpProjectInfoMapper upProjectInfoMapper;
    @Autowired
    private UpProjectCertificateMapper upProjectCertificateMapper;

    @Autowired
    private UpProjectCompanyMapper upProjectCompanyMapper;
    @Autowired
    private UpRecordService upRecordService;

    @Autowired
    private UpSalaryInfoMapper upSalaryInfoMapper;

    @Override
    public List<UpProjectInfo> selectByExample(UpProjectInfoExample example) {

        return upProjectInfoMapper.selectByExample(example);
    }

    @Override
    public UpProjectInfo selectByPrimaryKey(Integer id){
        return upProjectInfoMapper.selectByPrimaryKey(id);
    }

    /**
     * 保存或更新上报项目信息
     * @param upProjectInfo
     * @author quanroon.ysq
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public Map<String, Object> saveUpProjectInfo(UpProjectInfoDTO projectInfoDTO, UpProjectInfo upProjectInfo) {
        BeanUtils.copyProperties(projectInfoDTO, upProjectInfo);
        Map<String, Object> hashMap = Maps.newHashMap();
        try {
            // add
            if(Objects.isNull(upProjectInfo.getId())){
                upProjectInfo.setCreateDate(new Date());
                upProjectInfoMapper.insertSelective(upProjectInfo);
                hashMap.put("projId",upProjectInfo.getId());
            }else {
                // update
                upProjectInfo.setUpdateDate(new Date());
                upProjectInfoMapper.updateByPrimaryKeySelective(upProjectInfo);
                String requestCode = upRecordService.initRequestCode(upProjectInfo.getCodeEntity(), ReportType.proj_update);
                hashMap.put("requestCode",requestCode);
            }

            //因为是批量操作，因此先将当前项目下的附属信息删除，在统一保存
            UpProjectCertificateExample certificateExample = new UpProjectCertificateExample();
            certificateExample.createCriteria().andProjIdEqualTo(upProjectInfo.getId());
            upProjectCertificateMapper.deleteByExample(certificateExample);

            //批量保存施工许可信息
            List<UpProjectCertificateDTO> builderLicenses = projectInfoDTO.getBuilderLicenses();

            //判断是存在这个施工许可证集合(这个是非必填字段)
            if(!CollectionUtils.isEmpty(builderLicenses)){
                List<UpProjectCertificate> certificateList = Lists.newArrayList();
                builderLicenses.forEach(licenses ->{
                    UpProjectCertificate certificate = new UpProjectCertificate();
                    certificate.setProjId(upProjectInfo.getId());
                    certificate.setBuilderLicenseNo(licenses.getBuilderLicenseNo());
                    certificate.setBuilderLicenseOrgan(licenses.getBuilderLicenseOrgan());
                    if(StringUtils.isNoneEmpty(licenses.getBuilderLicenseDate())){
                        certificate.setBuilderLicenseDate(DateUtils.parseDate(licenses.getBuilderLicenseDate()));
                    }
                    certificateList.add(certificate);
                });
                if(certificateList.size() > 0)
                    upProjectCertificateMapper.batchInsertUpProjectCertificate(certificateList);
            }
            //批量删除关联的参建单位信息
            upProjectCompanyMapper.deleteByExample(upProjectInfo.getId());
            List<UpProjectCompany> projectCompanyList = projectInfoDTO.getProjCompanies();
            if(!CollectionUtils.isEmpty(projectCompanyList)){
                projectCompanyList.forEach(upProjectCompany -> upProjectCompany.setProjId(upProjectInfo.getId()));
                upProjectCompanyMapper.batchInsertUpProjectCompany(projectCompanyList);
            }
            UpSalaryInfo  salaryInfo = projectInfoDTO.getUpSalaryInfo();
            if(null != salaryInfo){
                //更新工资专户信息
                UpSalaryInfo upSalaryInfo = upSalaryInfoMapper.selectByProjIdPrimaryKey(upProjectInfo.getId());
                if(null == upSalaryInfo){
                    salaryInfo.setProjId(upProjectInfo.getId());
                    upSalaryInfoMapper.insert(salaryInfo);
                }
            }
            return hashMap;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("保存/更新上报项目信息报错");
        }
    }

    /**
     * 更新上报项目信息
     * @param record
     */
    @Override
    @Transactional(readOnly = false)
    public void updateByPrimaryKey(UpProjectInfo record) {
        upProjectInfoMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 查询蚌埠上报项目信息
     * @auther: 罗森林
     * @param:
     * @return:
     * @date: 2020-08-28 10:14
     */
    @Override
    public UpBengbuProjDTO selectBengbuProjInfo(Integer id){
       return upProjectInfoMapper.selectBengbuProjInfo(id);
    }
}
