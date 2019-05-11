package com.springmvc.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 生成20位和32位的主键id
 */
public class PrimaryKeyGeneratot {
    private static final Logger logger = LoggerFactory.getLogger(PrimaryKeyGeneratot.class);

    /**
     * 获取本机ip
     */
    private static String HOST_ADDRESS = "127.0.0.1";

    /**
     * 初始化五位随机数的初始值
     */
    private static int RANDOM_INT_FIVE = 1;
    private static int RANDOM_INT_THREE = 1;

    /**
     * 定义返回字符串STR_31长度
     */
    private static int STR_31 = 31;
    private static int STR_30 = 30;
    /**
     * 返回长度31时补0
     */
    private static int STR_31_0 = 0;
    private static int STR_30_00 = 00;

    /**
     * 时间格式
     */
    private static DateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    static {
        try {
            InetAddress addr = InetAddress.getLocalHost();
            HOST_ADDRESS = addr.getHostAddress().toString();
        } catch (UnknownHostException e) {
            logger.error("获取当前机器ip异常，异常信息[{}]",new Object[]{e});
        }
    }

    /**
     * 生成32位主键id
     * @return
     */
    public static String getThirtyTwoOfNextId(){
        String primaryKey;
        // 获取当前毫秒转化为字符串
        String currentTime = format.format(new Date());
        // 将ip转为十进制
        long ipAddress = ipToLong(HOST_ADDRESS);
        // 获取没毫秒发生的随机数5位
        String randomNum = randomNumForFive();
        // 组装 获得id
        primaryKey = currentTime + ipAddress + randomNum;
        // 判断长度
        if(primaryKey.length() == STR_31){
            primaryKey = currentTime + ipAddress + STR_31_0 + randomNum;
        }else if(primaryKey.length() == STR_30){
            primaryKey = currentTime + ipAddress + STR_30_00 + randomNum;
        }

        return primaryKey;
    }

    public static String getTwentyOfNextId(){
        String primaryKey;
        String currentTime = format.format(new Date());
        String randomNum = randomNumForThree();
        primaryKey = currentTime + randomNum;
        return primaryKey;
    }

    public static long ipToLong(String strIp){
        String[] ipArr = strIp.split(".");
        if(ipArr.length != 4){
            return 0;
        }
        long[] ip = new long[4];
        ip[0] = Long.parseLong(ipArr[0]);
        ip[1] = Long.parseLong(ipArr[1]);
        ip[2] = Long.parseLong(ipArr[2]);
        ip[3] = Long.parseLong(ipArr[3]);
        return (ip[0] << 24) + (ip[1] << 16) +(ip[2] << 8) + ip[3];
    }

    public synchronized static String randomNumForFive(){
        String retInt;
        String middlenInt = RANDOM_INT_FIVE++ + "";
        if(middlenInt.length() > 5){
            RANDOM_INT_FIVE = 1;
            middlenInt = RANDOM_INT_FIVE++ + "";
        }
        StringBuffer prefix = new StringBuffer("");
        for(int i = 0; i < 5-middlenInt.length(); i++){
            prefix.append("0");
        }
        retInt = prefix.append(middlenInt).toString();
        return  retInt;
    }

    public synchronized static String randomNumForThree(){
        String retInt;
        String middlenInt = RANDOM_INT_THREE++ + "";
        if(middlenInt.length() > 3){
            RANDOM_INT_THREE = 1;
            middlenInt = RANDOM_INT_THREE++ + "";
        }
        StringBuffer prefix = new StringBuffer("");
        for(int i = 0; i < 3-middlenInt.length(); i++){
            prefix.append("0");
        }
        retInt = prefix.append(middlenInt).toString();
        return  retInt;
    }

}
