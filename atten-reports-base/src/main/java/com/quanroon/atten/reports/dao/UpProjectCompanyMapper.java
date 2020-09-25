package com.quanroon.atten.reports.dao;

import com.quanroon.atten.reports.entity.UpProjectCompany;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UpProjectCompanyMapper {
    /**
     * 批量删除项目关联的参建的单位信息
     * @auther: 罗森林
     * @param:
     * @return:
     * @date: 2020-09-09 16:33
     */
    int deleteByExample(Integer projId);
    /**
     * 批量保存施工许可信息
     * @param projectCompanyList
     * @return
     */
    int batchInsertUpProjectCompany(@Param("list") List<UpProjectCompany> projectCompanyList);

    /**
     * 批量修改施工许可信息
     * @param projectCompanyList
     * @return
     */
    int batchUpdateUpProjectCompany(@Param("list") List<UpProjectCompany> projectCompanyList);

}