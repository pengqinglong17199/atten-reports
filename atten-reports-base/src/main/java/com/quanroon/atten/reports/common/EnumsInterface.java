package com.quanroon.atten.reports.common;

/**
 * @author quanroon.ysq
 * @version 1.0.0
 * @content 枚举基础接口
 * @date 2020/8/1 16:11
 */
public interface EnumsInterface {

    /**
     * 枚举key
     * @return
     */
    String value();

    /**
     * 枚举value
     * @return
     */
    String message();

    /**
     * 根据枚举种类，获取指定值得枚举
     * @param kind
     * @param enumValue
     * @return
     */
    static EnumsInterface getEnums(Class<? extends EnumsInterface> kind, Object enumValue){
        EnumsInterface enumResult = null;
        EnumsInterface[] enumConstants = kind.getEnumConstants();

        for(EnumsInterface enumsInterface : enumConstants){
            if(enumsInterface.value().equals(enumValue)){
                enumResult = enumsInterface;
                break;
            }
        }
        return enumResult;
    }

    /**
     * 根据枚举种类，获取指定key对应的value
     * @param kind
     * @param enumValue
     * @return
     */
    static Object getEnumsMessage(Class<? extends EnumsInterface> kind, Object enumValue){
        EnumsInterface enumResult = null;
        EnumsInterface[] enumConstants = kind.getEnumConstants();

        for(EnumsInterface enumsInterface : enumConstants){
            if(enumsInterface.value().equals(enumValue)){
                enumResult = enumsInterface;
                return enumResult.message();
            }
        }
        return "--";
    }
    /**
    * @Description:
    * @Author: quanroon.yaosq
    * @Date: 2020/9/7 11:59
    * @Param: [kind, enumValue, enumMessage]
    * @Return: java.lang.Object
    */
    static Object getReportEnumsMessage(Class<? extends EnumsInterface> kind, Object enumValue, ReportType enumMessage){
        EnumsInterface enumResult = null;
        EnumsInterface[] enumConstants = kind.getEnumConstants();

        for(EnumsInterface enumsInterface : enumConstants){
            if(enumsInterface.value().equals(enumValue)){
                enumResult = enumsInterface;
                return enumMessage.getMessage() + enumResult.message();
            }
        }
        return enumMessage.getMessage()+"--";
    }
}
