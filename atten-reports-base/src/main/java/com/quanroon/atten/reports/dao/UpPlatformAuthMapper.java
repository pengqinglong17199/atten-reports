package com.quanroon.atten.reports.dao;

import com.quanroon.atten.reports.entity.UpPlatformAuth;
import com.quanroon.atten.reports.entity.example.UpPlatformAuthExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface UpPlatformAuthMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table up_platform_auth
     *
     * @mbggenerated Tue Jun 30 15:12:47 CST 2020
     */
    int countByExample(UpPlatformAuthExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table up_platform_auth
     *
     * @mbggenerated Tue Jun 30 15:12:47 CST 2020
     */
    int deleteByExample(UpPlatformAuthExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table up_platform_auth
     *
     * @mbggenerated Tue Jun 30 15:12:47 CST 2020
     */
    int deleteByPrimaryKey(Integer companyId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table up_platform_auth
     *
     * @mbggenerated Tue Jun 30 15:12:47 CST 2020
     */
    int insert(UpPlatformAuth record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table up_platform_auth
     *
     * @mbggenerated Tue Jun 30 15:12:47 CST 2020
     */
    int insertSelective(UpPlatformAuth record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table up_platform_auth
     *
     * @mbggenerated Tue Jun 30 15:12:47 CST 2020
     */
    List<UpPlatformAuth> selectByExampleWithRowbounds(UpPlatformAuthExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table up_platform_auth
     *
     * @mbggenerated Tue Jun 30 15:12:47 CST 2020
     */
    List<UpPlatformAuth> selectByExample(UpPlatformAuthExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table up_platform_auth
     *
     * @mbggenerated Tue Jun 30 15:12:47 CST 2020
     */
    UpPlatformAuth selectByPrimaryKey(Integer companyId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table up_platform_auth
     *
     * @mbggenerated Tue Jun 30 15:12:47 CST 2020
     */
    int updateByExampleSelective(@Param("record") UpPlatformAuth record, @Param("example") UpPlatformAuthExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table up_platform_auth
     *
     * @mbggenerated Tue Jun 30 15:12:47 CST 2020
     */
    int updateByExample(@Param("record") UpPlatformAuth record, @Param("example") UpPlatformAuthExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table up_platform_auth
     *
     * @mbggenerated Tue Jun 30 15:12:47 CST 2020
     */
    int updateByPrimaryKeySelective(UpPlatformAuth record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table up_platform_auth
     *
     * @mbggenerated Tue Jun 30 15:12:47 CST 2020
     */
    int updateByPrimaryKey(UpPlatformAuth record);
}