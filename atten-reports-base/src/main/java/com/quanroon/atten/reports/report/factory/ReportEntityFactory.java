package com.quanroon.atten.reports.report.factory;



import com.quanroon.atten.reports.common.ReportType;
import com.quanroon.atten.reports.report.definition.BaseDefinition;
import com.quanroon.atten.reports.report.definition.ReportParamDefinition;
import com.quanroon.atten.reports.report.excepotion.NotInitMethodException;
import com.quanroon.atten.reports.report.excepotion.RepetitionCityException;
import com.quanroon.atten.reports.report.excepotion.ReportBeanNotFoundException;
import com.quanroon.atten.reports.report.entity.ReportEntity;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * 上报实体工厂
 * @author 彭清龙
 * @date 2020-05-06 20:53:11
 */
public abstract class ReportEntityFactory {

    protected BusinessFactory business;

    /**
     * key创建实体实例 使用实体的空构造方法
     * @param key
     * @return com.quanroon.saas.report.base.factory.entity.ReportEntity
     * @author 彭清龙
     * @date 2020/5/22 10:14
     */
    public abstract ReportEntity create(String key) throws IllegalAccessException, InstantiationException, InvocationTargetException;

    /**
     * 通过城市code创建实体实例 创建后使用init方法对其属性进行初始化
     * @param obj, objs
     * @return com.quanroon.saas.report.base.factory.entity.ReportEntity
     * @author 彭清龙
     * @date 2020/5/22 14:34
     */
    public abstract ReportEntity create(Object obj, BaseDefinition baseDefinition) throws IllegalAccessException, InstantiationException, NotInitMethodException, InvocationTargetException, ReportBeanNotFoundException;

    /**
     * 将数据加入缓存中
     * @param baseDefinition
     * @return void
     * @author 彭清龙
     * @date 2020/7/7 15:14
     */
    public abstract void join(BaseDefinition baseDefinition) throws RepetitionCityException;

    /**
     * 获取上报实体定义
     * @param key
     * @return com.quanroon.atten.reports.report.definition.BaseDefinition
     * @author 彭清龙
     * @date 2020/7/13 14:59
     */
    public abstract BaseDefinition getDefinition(String key);
}
