package com.quanroon.atten.reports.service.impl;

import com.google.common.collect.Maps;
import com.quanroon.atten.commons.enums.RepReturnCodeEnum;
import com.quanroon.atten.commons.exception.BusinessException;
import com.quanroon.atten.reports.common.ReportConstant;
import com.quanroon.atten.reports.common.ReportType;
import com.quanroon.atten.reports.dao.UpWorkerExpMapper;
import com.quanroon.atten.reports.dao.UpWorkerInMapper;
import com.quanroon.atten.reports.dao.UpWorkerInfoMapper;
import com.quanroon.atten.reports.entity.UpWorkerExp;
import com.quanroon.atten.reports.entity.UpWorkerIn;
import com.quanroon.atten.reports.entity.UpWorkerInfo;
import com.quanroon.atten.reports.entity.dto.UpWorkerInfoDTO;
import com.quanroon.atten.reports.entity.example.UpWorkerExpExample;
import com.quanroon.atten.reports.entity.example.UpWorkerInExample;
import com.quanroon.atten.reports.entity.example.UpWorkerInfoExample;
import com.quanroon.atten.reports.service.UpFileService;
import com.quanroon.atten.reports.service.UpRecordService;
import com.quanroon.atten.reports.service.UpWorkerInfoService;
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
 * @date 2020/7/1 16:04
 */
@Service
@Transactional(readOnly = true)
public class UpWorkerInfoServiceImpl implements UpWorkerInfoService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UpWorkerInfoMapper upWorkerInfoMapper;
    @Autowired
    private UpWorkerExpMapper upWorkerExpMapper;
    @Autowired
    private UpRecordService upRecordServiceImpl;
    @Autowired
    private UpFileService upFileServiceImpl;
    @Autowired
    private UpWorkerInMapper upWorkerInMapper;

    /**
     * 保存或更新劳工信息
     * @param upWorkerInfoDTO
     * @param upWorkerInfo
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public Map<String, Object> saveWorkerInfo(UpWorkerInfoDTO upWorkerInfoDTO,UpWorkerInfo upWorkerInfo) {
        BeanUtils.copyProperties(upWorkerInfoDTO, upWorkerInfo);
        Map<String, Object> hashMap = Maps.newHashMap();
        ReportType reportType = ReportType.worker_report;//默认劳工信息上报
        UpWorkerInfoExample upWorkerInfoExample = new UpWorkerInfoExample();
        if(Objects.isNull(upWorkerInfo.getId())){
            /**
             * 1.根据劳工姓名+身份证号判断劳工信息是否已经存在,如果存在,则进一步劳工进出场信息
             */
            upWorkerInfoExample.createCriteria().andNameEqualTo(upWorkerInfo.getName())
                    .andCardNoEqualTo(upWorkerInfo.getCardNo())
                    .andProjIdEqualTo(upWorkerInfo.getProjId());
            List<UpWorkerInfo> upWorkerInfos = upWorkerInfoMapper.selectByExample(upWorkerInfoExample);
            if(!CollectionUtils.isEmpty(upWorkerInfos)) {

                //判断此劳工是否有进出场信息（重新上岗后，再上报会出现这种情况）
                UpWorkerInExample workerInExample = new UpWorkerInExample();
                workerInExample.createCriteria()
                        .andProjIdEqualTo(upWorkerInfos.get(0).getProjId())
                        .andWorkerIdEqualTo(upWorkerInfos.get(0).getId());
                List<UpWorkerIn> upWorkerIns = upWorkerInMapper.selectByExample(workerInExample);

                //存在并且处于进场状态，则直接退回
                if (!CollectionUtils.isEmpty(upWorkerIns) && "0".equals(upWorkerIns.get(0).getStatus())) {
                    throw new BusinessException(RepReturnCodeEnum.REPORT_RETURN_330002);
                }else{
                    reportType = ReportType.worker_update;//劳工信息修改
                    upWorkerInfo.setId(upWorkerInfos.get(0).getId());
                    upWorkerInfo.setUpdateDate(new Date());
                    upWorkerInfoMapper.updateByPrimaryKey(upWorkerInfo);
                }
            }else{
                //新增劳工信息
                upWorkerInfo.setCreateDate(new Date());
                upWorkerInfoMapper.insertSelective(upWorkerInfo);
            }
        }else{
            //修改劳工信息
            upWorkerInfoExample.clear();
            upWorkerInfoExample.createCriteria().andProjIdEqualTo(upWorkerInfo.getProjId())
                    .andIdEqualTo(upWorkerInfo.getId());
            List<UpWorkerInfo> upWorkerInfo1 = upWorkerInfoMapper.selectByExample(upWorkerInfoExample);
            if(CollectionUtils.isEmpty(upWorkerInfo1))
                throw new BusinessException(RepReturnCodeEnum.REPORT_RETURN_330001);

            //
            reportType = ReportType.worker_update;//劳工信息修改
            upWorkerInfo.setUpdateDate(new Date());
            upWorkerInfoMapper.updateByPrimaryKey(upWorkerInfo);
        }
        //因为是批量操作，因此先将当前项目下的附属信息删除，在统一保存
        UpWorkerExpExample upWorkerExpExample = new UpWorkerExpExample();
        upWorkerExpExample.createCriteria().andWorkerIdEqualTo(upWorkerInfo.getId());
        upWorkerExpMapper.deleteByExample(upWorkerExpExample);
        //批量保存劳工附属信息
        List<UpWorkerExp> certificatesList = upWorkerInfoDTO.getCertificates();
        if(!CollectionUtils.isEmpty(certificatesList)){
            certificatesList.forEach(certificates ->{
                certificates.setWorkerId(upWorkerInfo.getId());
            });
            if(certificatesList.size() > 0)
                upWorkerExpMapper.batchInsertUpWorkerExp(certificatesList);
        }

        //上传照片附件
        try {
            Map<String, Object> filePaths = Maps.newHashMap();
            if(Objects.nonNull(upWorkerInfoDTO.getWorkerPicture())) {
                filePaths.put(ReportConstant.MODULE_WORKER_IMAGE, upWorkerInfoDTO.getWorkerPicture());
            }
            if(Objects.nonNull(upWorkerInfoDTO.getCardNoPersonPicture())) {
                filePaths.put(ReportConstant.MODULE_IDCARD_HEAD, upWorkerInfoDTO.getCardNoPersonPicture());
            }
            if(Objects.nonNull(upWorkerInfoDTO.getCardNoHeadsPicture())) {
                filePaths.put(ReportConstant.MODULE_IDCARD_FRONT, upWorkerInfoDTO.getCardNoHeadsPicture());
            }
            if(Objects.nonNull(upWorkerInfoDTO.getCardNoTailsPicture())) {
                filePaths.put(ReportConstant.MODULE_IDCARD_BACK, upWorkerInfoDTO.getCardNoTailsPicture());
            }
            if(!CollectionUtils.isEmpty(filePaths)){
                upFileServiceImpl.bantchHandleFiles(filePaths,upWorkerInfo.getId(), ReportConstant.UP_WORKER_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(RepReturnCodeEnum.REPORT_RETURN_362001);
        }
        try {
            String requestCode = upRecordServiceImpl.initRequestCode(upWorkerInfo.getCodeEntity(), reportType);
            hashMap.put("requestCode",requestCode);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("生成requestCode失败");
        }
        return hashMap;

    }

    public UpWorkerInfo findWorkerInfoByWorkerId(Integer workerId){
        return upWorkerInfoMapper.selectByPrimaryKey(workerId);
    }


}
