package com.quanroon.atten.reports.service.impl;

import com.quanroon.atten.reports.dao.UpFileMapper;
import com.quanroon.atten.reports.entity.UpFile;
import com.quanroon.atten.reports.entity.example.UpFileExample;
import com.quanroon.atten.reports.service.UpFileService;
import com.quanroon.atten.reports.service.UpRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.util.List;
import java.util.Map;


@Service
@Transactional(readOnly = true)
public class UpFileServiceImpl implements UpFileService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UpFileMapper upFileMapper;
    @Autowired
    private UpRecordService upRecordServiceImpl;

    @Value("${server.fileBasePath}")
    private String USER_FILE_PATH;
    /**
     * 处理上报的文件附件
     * @param filePath
     * @param tableId
     * @param tableName
     * @param moduleType
     */
    @Override
    @Transactional(readOnly = false)
    public void handleModelFile(String filePath, Integer tableId, String tableName, String moduleType) {

        //判断附件是否已经存在，如果存在，则更新
        UpFileExample upFileExample = new UpFileExample();
        upFileExample.createCriteria().andTableIdEqualTo(tableId)
                .andTableNameEqualTo(tableName)
                .andTableModuleEqualTo(moduleType);
        List<UpFile> upFiles = upFileMapper.selectByExample(upFileExample);
        //封装上报附件
        String fileNames = filePath.substring(filePath.lastIndexOf(File.separator)+1);

        UpFile upFile = UpFile.builder().filePath(filePath)
                .fileName(fileNames)
                .fileType(fileNames.split("\\.")[1])//注意这个转义字符
                .tableName(tableName)
                .tableId(tableId)
                .tableModule(moduleType).build();

        if(CollectionUtils.isEmpty(upFiles)){
            upFileMapper.insert(upFile);
        }else{
            upFile.setId(upFiles.get(0).getId());
            upFileMapper.updateByPrimaryKey(upFile);
        }
    }

    /**
     * 批量处理上报的附件
     * @param filePaths
     * @param tableId
     * @param tableName
     */
    @Override
    public void bantchHandleFiles(Map<String, Object> filePaths, Integer tableId, String tableName) {
        logger.info("附件即将进入批量上传："+filePaths.toString());
        //ArrayList<UpFile> arrayList = Lists.newArrayList();
        //先暂时这么写。
        for(Map.Entry<String, Object> entry : filePaths.entrySet() ){
            handleModelFile(entry.getValue().toString(),tableId,tableName,entry.getKey());
//            UpFile upFile = UpFile.builder().build();
//            arrayList.add(upFile);
        }
    }
//    /**
//    * @Description: base64还原文件
//    * @Author: ysx
//    * @Date: 2020/6/30
//    */
//    public void fileSolve(UpFile upFile){
//        //自定义生成附件存储路径
//        String path = USER_FILE_PATH + DateUtils.formatDate(new Date(),"yyyyMMdd") + "/" + upFile.getTableName()+ "_" + upFile.getTableId() + "/";
//        //保存的附件的文件名
//        String fileName = upFile.getFileName();
//        //附件在服务器的完整路径
//        String filePath = path + fileName;
//        //上传的base64字符串
//        String base64 = upFile.getFilePath();
//        try{
//            Files.write(Paths.get(filePath), Base64.decodeBase64(base64.substring(base64.indexOf(",") + 1)), StandardOpenOption.CREATE);
//            upFile.setFilePath(filePath);
//        }catch (IOException e){
//            e.printStackTrace();
//        }
//    }


}
