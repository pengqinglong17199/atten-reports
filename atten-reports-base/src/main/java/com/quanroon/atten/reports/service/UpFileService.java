package com.quanroon.atten.reports.service;

import com.quanroon.atten.reports.entity.UpFile;
import com.quanroon.atten.reports.entity.dto.UpFileDTO;

import java.util.Map;

public interface UpFileService {
    /**
     * 处理上报的附件
     * @param filePath
     * @param tableId
     * @param tableName
     * @param moduleType
     */
    void handleModelFile(String filePath, Integer tableId, String tableName, String moduleType);

    /**
     * 批量处理上报的附件
     * @param filePaths
     * @param tableId
     * @param tableName
     */
    void bantchHandleFiles(Map<String, Object> filePaths, Integer tableId, String tableName);
}
