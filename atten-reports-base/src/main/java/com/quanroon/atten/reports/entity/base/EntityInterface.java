package com.quanroon.atten.reports.entity.base;

/**
 * @author quanroon.ysq
 * @version 1.0.0
 * @content 实体接口
 * @date 2020/8/5 19:59
 */
public interface EntityInterface<T> {

    /**
     * 初始化消息生产前属性
     * @param projId
     * @author quanroon.ysq
     * @createTime 2020.08.06
     * @return
     */
    CodeEntity initCodeEntity(Integer projId);

    /**
     * 获取消息生产属性
     * @param args 项目id或表名class
     * @author quanroon.ysq
     * @createTime 2020.08.06
     * @return
     */
    default CodeEntity getCodeEntity(Object... args) {
        Integer projId = null;
        if(args.length > 0 && args[0] instanceof Integer){
            projId = (Integer) args[0];
        }
        TableEntity annotation = ((args.length > 0 && args[0] instanceof Class) ?
                (Class<T>) args[0] : this.getClass()).getAnnotation(TableEntity.class);
        CodeEntity codeEntity = initCodeEntity(projId);
        codeEntity.setTableName(annotation.value());
        return codeEntity;
    }
}
