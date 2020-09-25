package com.quanroon.atten.reports.service.impl;

import com.google.common.collect.Maps;
import com.quanroon.atten.commons.enums.RepReturnCodeEnum;
import com.quanroon.atten.commons.exception.BusinessException;
import com.quanroon.atten.reports.common.ReportConstant;
import com.quanroon.atten.reports.common.ReportType;
import com.quanroon.atten.reports.dao.UpGroupInfoMapper;
import com.quanroon.atten.reports.dao.UpWorkerInMapper;
import com.quanroon.atten.reports.dao.UpWorkerInfoMapper;
import com.quanroon.atten.reports.entity.UpFile;
import com.quanroon.atten.reports.entity.UpGroupInfo;
import com.quanroon.atten.reports.entity.UpWorkerIn;
import com.quanroon.atten.reports.entity.UpWorkerInfo;
import com.quanroon.atten.reports.entity.dto.UpWorkerInDTO;
import com.quanroon.atten.reports.entity.example.UpWorkerInExample;
import com.quanroon.atten.reports.service.UpFileService;
import com.quanroon.atten.reports.service.UpRecordService;
import com.quanroon.atten.reports.service.UpWorkerInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author quanroon.ysq
 * @version 1.0.0
 * @content
 * @date 2020/7/1 16:57
 */
@Service
@Transactional(readOnly = true)
public class UpWorkerInServiceImpl implements UpWorkerInService {
    @Autowired
    private UpGroupInfoMapper upGroupInfoMapper;
    @Autowired
    private UpWorkerInfoMapper upWorkerInfoMapper;
    @Autowired
    private UpWorkerInMapper upWorkerInMapper;
    @Autowired
    private UpRecordService upRecordServiceImpl;
    @Autowired
    private UpFileService upFileServiceImpl;
    /**
     * 保存劳工进退场
     * @param upWorkerIn
     */
    @Override
    @Transactional(readOnly = false)
    public Map<String, Object> saveWorkerInOut(UpWorkerInDTO workerInDTO, UpWorkerIn upWorkerIn) {
        Map<String, Object> hashMap = Maps.newHashMap();
        ReportType reportType = ReportType.worker_enter;
        String requestCode = null;
        //判断劳工信息是否有上报
        UpWorkerInfo upWorkerInfo = upWorkerInfoMapper.selectByPrimaryKey(upWorkerIn.getWorkerId());
        if(Objects.isNull(upWorkerInfo))
            throw new BusinessException(RepReturnCodeEnum.REPORT_RETURN_330001);
        //判断劳工是否有进场
        UpWorkerInExample workerInExample = new UpWorkerInExample();
        workerInExample.createCriteria()
                .andProjIdEqualTo(upWorkerIn.getProjId())
                .andWorkerIdEqualTo(upWorkerIn.getWorkerId());
        List<UpWorkerIn> upWorkerIns = upWorkerInMapper.selectByExample(workerInExample);
        //判断是否是离场操作
        if(Objects.nonNull(upWorkerIn.getLeaveDate()) && Objects.isNull(upWorkerIn.getEntryDate())){
            if(CollectionUtils.isEmpty(upWorkerIns))
                throw new BusinessException(RepReturnCodeEnum.REPORT_RETURN_331002);

            //不允许重复离场
            //是否允许重复离场，根据住建局给出的上报结果reportCode来判断
            if("1".equals(upWorkerIns.get(0).getStatus()))
                throw new BusinessException(RepReturnCodeEnum.REPORT_RETURN_332001);

            Integer upWorkerInId = upWorkerIns.get(0).getId();
            upWorkerIn.setId(upWorkerInId);
            //离场操作
            upWorkerIn.setStatus("1");//离场状态
            upWorkerInMapper.updateByExampleSelective(upWorkerIn,workerInExample);
            reportType = ReportType.worker_leave;
        }else{
            //进场操作
            upWorkerIn.setStatus("0");//进场状态
            //判断班组是否有上报
            UpGroupInfo upGroupInfo = upGroupInfoMapper.selectByPrimaryKey(upWorkerIn.getGroupId());
            if(Objects.isNull(upGroupInfo))
                throw new BusinessException(RepReturnCodeEnum.REPORT_RETURN_320001);
            //班组是否有离场,班组离场了，劳工不能进场
            if("1".equals(upGroupInfo.getStatus()))
                throw new BusinessException((RepReturnCodeEnum.REPORT_RETURN_320003));

            //判断进场信息是新增或更新
            if(CollectionUtils.isEmpty(upWorkerIns)){
                upWorkerInMapper.insertSelective(upWorkerIn);
            }else{
                //已入场的劳工不能重复入场
                //是否允许重复离场，根据住建局给出的上报结果reportCode来判断
                /*if("0".equals(upWorkerIns.get(0).getStatus()))
                    throw new BusinessException(RepReturnCodeEnum.REPORT_RETURN_331001);*/

                Integer upWorkerInId = upWorkerIns.get(0).getId();
                upWorkerIn.setId(upWorkerInId);

                upWorkerInMapper.updateByExample(upWorkerIn,workerInExample);
            }
        }
        //进行附件上传
        try {
            if(Objects.nonNull(workerInDTO)){
                //上传合同附件
                if(Objects.nonNull(workerInDTO.getContractFile())){
                    upFileServiceImpl.handleModelFile(workerInDTO.getContractFile(), upWorkerIn.getId(),
                            ReportConstant.UP_WORKER_IN, ReportConstant.MODULE_WORKER_CONTRACT);
                }
                //上传照片附件
                Map<String, Object> filePaths = Maps.newHashMap();
                if(Objects.nonNull(workerInDTO.getInsurancePicture())){
                    filePaths.put(ReportConstant.MODULE_WORKER_INSURANCE, workerInDTO.getInsurancePicture());
                }
                if(Objects.nonNull(workerInDTO.getCertificatePicture())){
                    filePaths.put(ReportConstant.MODULE_WORKER_CERTIFICATE, workerInDTO.getCertificatePicture());
                }
                if(Objects.nonNull(workerInDTO.getBankPicture())){
                    filePaths.put(ReportConstant.MODULE_WORKER_BANK, workerInDTO.getBankPicture());
                }
                if(CollectionUtils.isEmpty(filePaths)){
                    upFileServiceImpl.bantchHandleFiles(filePaths,upWorkerIn.getId(), ReportConstant.UP_WORKER_IN);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(RepReturnCodeEnum.REPORT_RETURN_362001);
        }
        requestCode = upRecordServiceImpl.initRequestCode(upWorkerIn.getCodeEntity(), reportType);
        hashMap.put("requestCode",requestCode);
        return hashMap;
    }
}
