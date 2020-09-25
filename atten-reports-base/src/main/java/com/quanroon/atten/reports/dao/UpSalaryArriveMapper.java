package com.quanroon.atten.reports.dao;

import com.quanroon.atten.reports.entity.UpSalaryArrive;
import com.quanroon.atten.reports.entity.example.UpSalaryArriveExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
public interface UpSalaryArriveMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table up_salary_arrive
     *
     * @mbggenerated Mon Jun 29 15:17:26 CST 2020
     */
    int countByExample(UpSalaryArriveExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table up_salary_arrive
     *
     * @mbggenerated Mon Jun 29 15:17:26 CST 2020
     */
    int deleteByExample(UpSalaryArriveExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table up_salary_arrive
     *
     * @mbggenerated Mon Jun 29 15:17:26 CST 2020
     */
    int insert(UpSalaryArrive record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table up_salary_arrive
     *
     * @mbggenerated Mon Jun 29 15:17:26 CST 2020
     */
    int insertSelective(UpSalaryArrive record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table up_salary_arrive
     *
     * @mbggenerated Mon Jun 29 15:17:26 CST 2020
     */
    List<UpSalaryArrive> selectByExampleWithRowbounds(UpSalaryArriveExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table up_salary_arrive
     *
     * @mbggenerated Mon Jun 29 15:17:26 CST 2020
     */
    List<UpSalaryArrive> selectByExample(UpSalaryArriveExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table up_salary_arrive
     *
     * @mbggenerated Mon Jun 29 15:17:26 CST 2020
     */
    int updateByExampleSelective(@Param("record") UpSalaryArrive record, @Param("example") UpSalaryArriveExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table up_salary_arrive
     *
     * @mbggenerated Mon Jun 29 15:17:26 CST 2020
     */
    int updateByExample(@Param("record") UpSalaryArrive record, @Param("example") UpSalaryArriveExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table up_salary_info
     *
     * @mbggenerated Mon Jun 29 15:17:49 CST 2020
     */
    int updateByPrimaryKey(UpSalaryArrive record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table up_salary_info
     *
     * @mbggenerated Mon Jun 29 15:17:49 CST 2020
     */
    UpSalaryArrive selectByPrimaryKey(Integer id);

}