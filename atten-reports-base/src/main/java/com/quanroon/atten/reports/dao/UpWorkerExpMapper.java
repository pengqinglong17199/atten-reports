package com.quanroon.atten.reports.dao;

import com.quanroon.atten.reports.entity.UpProjectCertificate;
import com.quanroon.atten.reports.entity.UpWorkerExp;
import com.quanroon.atten.reports.entity.example.UpWorkerExpExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
public interface UpWorkerExpMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table up_worker_exp
     *
     * @mbggenerated Mon Jun 29 15:18:45 CST 2020
     */
    int countByExample(UpWorkerExpExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table up_worker_exp
     *
     * @mbggenerated Mon Jun 29 15:18:45 CST 2020
     */
    int deleteByExample(UpWorkerExpExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table up_worker_exp
     *
     * @mbggenerated Mon Jun 29 15:18:45 CST 2020
     */
    int insert(UpWorkerExp record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table up_worker_exp
     *
     * @mbggenerated Mon Jun 29 15:18:45 CST 2020
     */
    int insertSelective(UpWorkerExp record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table up_worker_exp
     *
     * @mbggenerated Mon Jun 29 15:18:45 CST 2020
     */
    List<UpWorkerExp> selectByExampleWithRowbounds(UpWorkerExpExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table up_worker_exp
     *
     * @mbggenerated Mon Jun 29 15:18:45 CST 2020
     */
    List<UpWorkerExp> selectByExample(UpWorkerExpExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table up_worker_exp
     *
     * @mbggenerated Mon Jun 29 15:18:45 CST 2020
     */
    int updateByExampleSelective(@Param("record") UpWorkerExp record, @Param("example") UpWorkerExpExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table up_worker_exp
     *
     * @mbggenerated Mon Jun 29 15:18:45 CST 2020
     */
    int updateByExample(@Param("record") UpWorkerExp record, @Param("example") UpWorkerExpExample example);

    /**
     * 批量保存劳工附属信息
     * @param upWorkerExpList
     * @return
     */
    int batchInsertUpWorkerExp(@Param("list") List<UpWorkerExp> upWorkerExpList);
}