package com.quanroon.atten.reports.job.bengbu.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.quanroon.atten.reports.entity.*;
import com.quanroon.atten.reports.entity.dto.*;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang3.StringUtils;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 罗森林
 * @Auther:
 * @Date: 2020-08-27 15:51
 * @Description:
 */
public class PackageUtil {
    /**
     * 项目数据封装
     * @auther: 罗森林
     * @param:
     * @return:
     * @date: 2020-08-28 9:01
     */
    public static StringBuffer ProjectPackage(UpBengbuProjDTO projDTO, UpParams upParams){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        StringBuffer stringBuffer = new StringBuffer();
        //行政区划代码
        stringBuffer.append("aaa027="+projDTO.getCityCode()+"&");
        //施工单位编码(统一社会信用代码)
        stringBuffer.append("aab001="+projDTO.getConstructNo()+"&");
        //建设单位编码(统一社会信用代码)
        stringBuffer.append("aab001s="+projDTO.getBuildNo()+"&");
        //施工许可证号
        stringBuffer.append("aaa002="+projDTO.getBuilderLicenseNo()+"&");
        //项目名称
        stringBuffer.append("aaa003="+projDTO.getName()+"&");
        //项目备注名称
        stringBuffer.append("aaa004="+projDTO.getName()+"&");
        //建设地址
        stringBuffer.append("aaa005="+projDTO.getAddress()+"&");
        stringBuffer.append("aaa008="+dateFormat.format(projDTO.getStartDate())+"&");
        stringBuffer.append("aaa009="+dateFormat.format(projDTO.getEndDate())+"&");
        stringBuffer.append("aaa010="+projDTO.getProjScale()+"&");
        //工程状态（0 未竣工;1已竣工）
        String engStatus = "0";
        if(projDTO.getStatus().equals("004")){
            engStatus = "1";
        }
        stringBuffer.append("aaa011="+engStatus+"&");
        stringBuffer.append("aak010="+projDTO.getStatus()+"&");
        stringBuffer.append("aak011="+projDTO.getType()+"&");
        stringBuffer.append("uid="+upParams.getSecret()+"&");
        stringBuffer.append("userName="+upParams.getAccount());
        return stringBuffer;
    }


    /**
     * 参建单位数据封装
     * @auther: 罗森林
     * @param:
     * @return:
     * @date: 2020-08-27 15:51
     */
    public static String CompanyPackage(UpBengbuProjDTO projDTO,UpCompanyIn upCompanyIn,UpCompanyInfo upCompanyInfo,UpBengbuSalaryInfoDTO upSalaryInfo,UpParams upParams){
        JSONArray  array = new JSONArray();
        JSONObject  json = new JSONObject();
        //行政区划代码
        json.put("aaa027",projDTO.getCityCode());
        //单位名称
        json.put("aab004",upCompanyInfo.getName());
        //统一社会信用代码
        json.put("aab010",upCompanyInfo.getCorpCode());
        //法定代表人姓名
        json.put("aab013",upCompanyInfo.getLegalName());
        //单位类型
        json.put("aab019",upCompanyIn.getBuildType());
        if(upCompanyIn.getBuildType().equals(BengBuDict.GENERAL) && null != upSalaryInfo){
            //项目经理
            json.put("aak003",projDTO.getManagerName());
            //项目经理联系电话
            json.put("aak004",projDTO.getManagerPhone());

            //现场负责人
            json.put("aak005",upCompanyIn.getProjLeaderName());
            //负责人电话
            json.put("aak006",upCompanyIn.getProjLeaderPhone());

            //劳资专管员姓名
            json.put("aak007",upCompanyIn.getLabourName());
            //劳资专管员电话
            json.put("aak008",upCompanyIn.getLabourPhone());
            //劳资专管员身份证号码
            json.put("aac003",upCompanyIn.getLabourCard());
            //保证金缴存方式(1:现金;2:银行保函;3:现金+银行保函;11:全免;12:暂缓)
            json.put("aak009","1");
            //使用农民工人数
            json.put("aak015","55");
            //是否按月足额支付
            json.put("aak019","1");
            //农民工工资专户账号
            json.put("eaz001",upSalaryInfo.getBankAccount());
            //农民工工资专户户名
            json.put("eaz002",upSalaryInfo.getAccountName());
            //农民工工资专户开户行
            json.put("eaz003",upSalaryInfo.getBankName());
        }
        //所属行业大类
        json.put("aab024",upCompanyInfo.getType());
        //税号
        json.put("aab030",upCompanyInfo.getCorpCode());
        //联系人姓名
        json.put("aae004",upCompanyIn.getContactsName());
        //单位地址
        json.put("aae006",upCompanyInfo.getAddress());
        //项目状态
        json.put("aak010",projDTO.getStatus());
        //项目类型
        json.put("aak011",projDTO.getType());
        //有无维权告示牌(1 有;0 没有)
        json.put("aak012","1");
        //是否政府投资项目(1 是;0 不是)
        json.put("aak016","0");
        //是否实名制考勤(1 是;0 不是)
        json.put("aak017","1");
        //联系人电话
        json.put("eac154",upCompanyIn.getContactsPhone());
        //法定代表人电话
        json.put("eae280",upCompanyInfo.getLegalPhone());
        //用户uid
        json.put("uid",upParams.getSecret());
        //用户名account
        json.put("userName",upParams.getAccount());
        array.add(json);
        return array.toJSONString();
    }

    /**
     * 封装班组数据
     * @auther: 罗森林
     * @param:
     * @return:
     * @date: 2020-08-28 14:58
     */
    public static String GroupPackage(UpBengbuGroupDTO upGroupInfo,UpParams upParams){
        StringBuffer stringBuffer = new StringBuffer();
        //行政区划代码
        stringBuffer.append("aaa027="+upGroupInfo.getCityCode()+"&");
        //参建单位编码(统一社会信用代码)
        stringBuffer.append("aab001="+upGroupInfo.getCorpCode()+"&");
        //班组名称
        stringBuffer.append("abz002="+upGroupInfo.getGroupName()+"&");
        //班组负责人
        stringBuffer.append("abz003="+upGroupInfo.getGroupLeaderName()+"&");
        //联系电话
        stringBuffer.append("abz004="+upGroupInfo.getGroupLeaderPhone()+"&");
        //工种编码
        stringBuffer.append("abz005="+upGroupInfo.getWorkType()+"&");
        //工资类型
        stringBuffer.append("abz006="+upGroupInfo.getSalaryType()+"&");
        //工资
        stringBuffer.append("abz007="+upGroupInfo.getContractMoney()+"&");
        //项目编码(上传项目时返回的数据)
        stringBuffer.append("projectId="+upGroupInfo.getProjNo()+"&");
        stringBuffer.append("uid="+upParams.getSecret()+"&");
        stringBuffer.append("userName="+upParams.getAccount());
        return stringBuffer.toString();
    }

    /**
     * 封装劳工信息
     * @auther: 罗森林
     * @param:
     * @return:
     * @date: 2020-08-28 15:21
     */
    public static String workerPackage(UpBengbuWorkerInDTO bengbuWorkerInDTO, UpParams upParams){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        JSONArray  array = new JSONArray();
        JSONObject  json = new JSONObject();
        //单位统一社会信用代码
        json.put("aab010",bengbuWorkerInDTO.getCorpCode());
        //证件号码
        json.put("aac002",bengbuWorkerInDTO.getCardNo());
        //姓名
        json.put("aac003",bengbuWorkerInDTO.getName());
        //性别(1 男;2 女)
        json.put("aac004",bengbuWorkerInDTO.getSex());
        //联系电话
        json.put("aac005",bengbuWorkerInDTO.getMobile());
        //工种
        json.put("aac009",bengbuWorkerInDTO.getWorkType());
        //住址
        json.put("aac006",bengbuWorkerInDTO.getCurrentAddress());
        //民族
        json.put("aac008",bengbuWorkerInDTO.getNation().substring(2,bengbuWorkerInDTO.getNation().length()));
        //文化程度10博士后;11博士;12硕士;21本科;31大专;40中专;50技校;61高中;62职高;63职专;70初中;80小学;90文盲;99 其他)
        json.put("aac010",bengbuWorkerInDTO.getEducationDegree());
        //进场日期(yyyy-MM-dd)
        json.put("entryDate",dateFormat.format(bengbuWorkerInDTO.getEntryDate()));
        //工人进退场标志(1:进场;0:退场)
        json.put("flag",bengbuWorkerInDTO.getStatus());
        //头像(Base64格式)
        json.put("headImage",compressImageByThumbnail(bengbuWorkerInDTO.getHeadImage(),200));
        //工资账户账号
        json.put("eae001",bengbuWorkerInDTO.getSalaryBankNo());
        //工资账户户名
        json.put("eae002",bengbuWorkerInDTO.getName());
        //工资银行（saas>report暂时没有做银行转换）
        //json.put("yhbm",bengbuWorkerInDTO.getSalaryBank());
        //项目编码(上传项目时返回的数据)
        json.put("projectId",bengbuWorkerInDTO.getProjNo());
        //班组编码(上传班组时返回的数据)
        json.put("teamId",bengbuWorkerInDTO.getGroupNo());
        json.put("uid",upParams.getSecret());
        json.put("userName",upParams.getAccount());
        array.add(json);
        return array.toJSONString();
    }

    public static String compressImageByThumbnail(String imgFile, int kb){
        if(StringUtils.isEmpty(imgFile)){ return null; }
        if(StringUtils.indexOf(imgFile,"/zgz") != -1){
            imgFile = imgFile.replace("/zgz","");
        }
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(imgFile);
            byte[] bytes1 = new byte[inputStream.available()];
            inputStream.read(bytes1);
            byte[] bytes = commpressPicCycle(bytes1, kb, 0.5f);
            return new String(Base64.getEncoder().encode(bytes));
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    public static byte[] commpressPicCycle(byte[] bytes, long desFileSize, double accuracy) throws IOException {
        long fileSize = bytes.length;
        // 判断图片大小是否小于指定图片大小 转成64位编码会使内存变大33%
        double multiple = 1.34;
        if(fileSize  *  multiple <= desFileSize * 1024 ){
            return bytes;
        }
        //计算宽高
        BufferedImage bim = ImageIO.read(new ByteArrayInputStream(bytes));
        int imgWidth = bim.getWidth();
        int imgHeight = bim.getHeight();
        int desWidth = new BigDecimal(imgWidth).multiply( new BigDecimal(accuracy)).intValue();
        int desHeight = new BigDecimal(imgHeight).multiply( new BigDecimal(accuracy)).intValue();
        //字节输出流（写入到内存）
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Thumbnails.of(new ByteArrayInputStream(bytes)).size(desWidth, desHeight).outputQuality(accuracy).toOutputStream(baos);
        //如果不满足要求,递归直至满足要求
        return commpressPicCycle(baos.toByteArray(), desFileSize, accuracy);
    }

    /**
     * 封装合同数据
     * @auther: 罗森林
     * @param:
     * @return:
     * @date: 2020-08-28 15:36
     */
    public static String contractPackage(UpBengbuWorkerInDTO upBengbuWorkerDTO, UpParams upParams){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        JSONArray array = new JSONArray();
        JSONObject json  = new JSONObject();
        //所属单位编码(统一社会信用代码)
        json.put("aab001",upBengbuWorkerDTO.getCorpCode());
        //身份证号
        json.put("aac002",upBengbuWorkerDTO.getCardNo());
        //姓名
        json.put("aac003",upBengbuWorkerDTO.getName());
        //合同开始时间
        json.put("aae001",dateFormat.format(upBengbuWorkerDTO.getContractStartDate()));
        //合同结束时间
        json.put("aae002",dateFormat.format(upBengbuWorkerDTO.getContractEndDate()));
        //约定工资形式
        json.put("acb002",upBengbuWorkerDTO.getSalaryWay());
        //约定工资
        json.put("aic026",upBengbuWorkerDTO.getSalaryMoney());
        //项目编码
        json.put("projectId",upBengbuWorkerDTO.getProjNo());
        //班组编码
        json.put("teamId",upBengbuWorkerDTO.getGroupNo());
        //用户uid
        json.put("uid",upParams.getSecret());
        //用户名account
        json.put("userName",upParams.getAccount());
        array.add(json);
        return array.toJSONString();
    }

    /**
     * 封装劳工考勤数据
     * @auther: 罗森林
     * @param:
     * @return:
     * @date: 2020-08-28 15:53
     */
    public static String signLogPackage(UpBengbuSignlogDTO signlogDTO,UpParams upParams){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        JSONArray array = new JSONArray();
        JSONObject json  = new JSONObject();
        //所属单位编码(社会统一信用码)
        json.put("aab001",signlogDTO.getCorpCode());
        //证件号码
        json.put("aac002",signlogDTO.getCardNo());
        //姓名
        json.put("aac003",signlogDTO.getName());
        //打卡时间(yyyy-MM-dd HH:mm:ss)
        json.put("aac004",format.format(signlogDTO.getTime()));
        //项目编码(上传项目时返回的数据)
        json.put("projectId",signlogDTO.getProjNo());
        //班组编码(上传班组时返回的数据)
        json.put("teamId",signlogDTO.getGroupNo());
        //进出场类型(01:入场;02:出场)
        json.put("type",signlogDTO.getDirection());
        json.put("uid",upParams.getSecret());
        //用户名account
        json.put("userName",upParams.getAccount());
        array.add(json);
        return array.toJSONString();
    }

    /**
     * 封装工资数据
     * @auther: 罗森林
     * @param:
     * @return:
     * @date: 2020-08-28 16:04
     */
    public static String payrollPackage(UpBengbuPayRollDTO payRollDTO, UpParams upParams){
        SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
        JSONArray array = new JSONArray();
        JSONObject json  = new JSONObject();
        //所属单位编码(统一社会信息码)
        json.put("aab001",payRollDTO.getCorpCode());
        //证件号码
        json.put("aac002",payRollDTO.getCardNo());
        //姓名
        json.put("aac003",payRollDTO.getName());
        //业务年月(yyyyMM)
        json.put("aic021",format.format(payRollDTO.getBusinessDate()));
        //约定工资
        json.put("aic022",payRollDTO.getSalaryMoney());
        //出勤天数
        json.put("aic023",payRollDTO.getAttendanceDays());
        //当月出工开始时间(yyyy-MM-dd)
        json.put("aic024",getMinDateMonth(format.format(payRollDTO.getBusinessDate())));
        //当月出工结束时间(yyyy-MM-dd)
        json.put("aic025",getMaxDateMonth(format.format(payRollDTO.getBusinessDate())));
        //应发工资
        json.put("aic026",payRollDTO.getPayableMoney());
        //实发工资
        json.put("aic027",payRollDTO.getPaidMoney());
        //工资卡卡号
        json.put("eae001",payRollDTO.getSalaryBankNo());
        //工资卡开户名
        json.put("eae002",payRollDTO.getAccountName());
        //项目编码
        json.put("projectId",payRollDTO.getProjNo());
        //班组编码
        json.put("teamId",payRollDTO.getGroupNo());
        //开户银行
        json.put("yhbm",payRollDTO.getSalaryBank());
        json.put("uid",upParams.getSecret());
        //用户名account
        json.put("userName",upParams.getAccount());
        array.add(json);
        return array.toJSONString();
    }

    private static SimpleDateFormat sdf=new SimpleDateFormat("yyyyMM");
    private static SimpleDateFormat sdfs=new SimpleDateFormat("yyyy-MM-dd");
    private static Calendar calendar=Calendar.getInstance();

    public static String getMinDateMonth (String month) {
        try {
            Date nowDate=sdf.parse(month);
            calendar = Calendar.getInstance();
            calendar.setTime(nowDate);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
            return sdfs.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    //输入日期字符串，返回当月最后一天的Date
    public static String getMaxDateMonth(String month){
        try {
            Date nowDate=sdf.parse(month);
            calendar = Calendar.getInstance();
            calendar.setTime(nowDate);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            return sdfs.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
