package com.quanroon.atten.reports.service.impl;

import com.quanroon.atten.commons.utils.Base64Utils;
import com.quanroon.atten.reports.common.ReportConstant;
import com.quanroon.atten.reports.common.ReportType;
import com.quanroon.atten.reports.dao.UpFileMapper;
import com.quanroon.atten.reports.dao.UpWorkerSignlogInfoMapper;
import com.quanroon.atten.reports.entity.UpFile;
import com.quanroon.atten.reports.entity.UpWorkerSignlogInfo;
import com.quanroon.atten.reports.entity.dto.UpWorkerSignlogInfoDTO;
import com.quanroon.atten.reports.service.UpRecordService;
import com.quanroon.atten.reports.service.UpWorkerSignlogInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UpWorkerSignlogInfoServiceImpl implements UpWorkerSignlogInfoService {

    @Autowired
    private UpWorkerSignlogInfoMapper upWorkerSignlogInfoMapper;
    @Autowired
    private UpRecordService upRecordServiceImpl;
    @Autowired
    private UpFileMapper upFileMapper;

    @Transactional(readOnly = false)
    public String save(UpWorkerSignlogInfo upWorkerSignlogInfo, UpWorkerSignlogInfoDTO dto){
        upWorkerSignlogInfoMapper.insert(upWorkerSignlogInfo);
        String requestCode = upRecordServiceImpl.initRequestCode(upWorkerSignlogInfo.getCodeEntity(), ReportType.worker_signlog);
        if(dto.getPhoto()!=null){
            String path = Base64Utils.base64ToFile(dto.getPhoto(),null,"photo");
            UpFile upFile = this.getUpFile(path,upWorkerSignlogInfo.getId(),ReportConstant.MODULE_SIGNLOG_IMAGE);
            upFileMapper.insert(upFile);
        }
        return requestCode;
    }

    public UpFile getUpFile(String path,Integer id,String module){
        UpFile upFile = UpFile.builder().build();
        upFile.setFilePath(path);
        upFile.setTableId(id);
        upFile.setTableModule(module);
        upFile.setTableName(ReportConstant.UP_WORKER_SIGNLOG_INFO);
        return upFile;
    }


}
