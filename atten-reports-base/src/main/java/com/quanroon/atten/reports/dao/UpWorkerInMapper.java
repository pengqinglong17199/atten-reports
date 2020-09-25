package com.quanroon.atten.reports.dao;

import com.quanroon.atten.reports.entity.UpWorkerIn;
import com.quanroon.atten.reports.entity.example.UpWorkerInExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
public interface UpWorkerInMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table up_worker_in
     *
     * @mbggenerated Mon Jun 29 15:20:16 CST 2020
     */
    int countByExample(UpWorkerInExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table up_worker_in
     *
     * @mbggenerated Mon Jun 29 15:20:16 CST 2020
     */
    int deleteByExample(UpWorkerInExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table up_worker_in
     *
     * @mbggenerated Mon Jun 29 15:20:16 CST 2020
     */
    int insert(UpWorkerIn record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table up_worker_in
     *
     * @mbggenerated Mon Jun 29 15:20:16 CST 2020
     */
    int insertSelective(UpWorkerIn record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table up_worker_in
     *
     * @mbggenerated Mon Jun 29 15:20:16 CST 2020
     */
    List<UpWorkerIn> selectByExampleWithRowbounds(UpWorkerInExample example, RowBounds rowBounds);
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table up_worker_info
     *
     * @mbggenerated Mon Jun 29 15:20:45 CST 2020
     */
    UpWorkerIn selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table up_worker_in
     *
     * @mbggenerated Mon Jun 29 15:20:16 CST 2020
     */
    List<UpWorkerIn> selectByExample(UpWorkerInExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table up_worker_in
     *
     * @mbggenerated Mon Jun 29 15:20:16 CST 2020
     */
    int updateByExampleSelective(@Param("record") UpWorkerIn record, @Param("example") UpWorkerInExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table up_worker_in
     *
     * @mbggenerated Mon Jun 29 15:20:16 CST 2020
     */
    int updateByExample(@Param("record") UpWorkerIn record, @Param("example") UpWorkerInExample example);

    UpWorkerIn selectByWorkerId(@Param("workerId") Integer workerId);

    int updateResponseCodeByWorkerId(@Param("workerId") Integer workerId,@Param("reportCode") String reportCode);
}