package com.springmvc.utils;
import java.math.BigDecimal;

/**
 * 将数字转化为汉语中人民币的大写
 */
public class NumberToCn {
    private static final String[] CN_UPPER_NUMBER = {"零","壹","贰","叁","肆","伍","陆","柒","捌","玖"};
    private static final String[] CN_UPPER_MONETRAY_UNIT = {"分","角","元",
            "拾","佰","仟","万","拾","佰","仟","亿","拾","佰","仟","兆","拾","佰","仟"};
    private static final String CN_FULL = "整";
    private static final String CN_NEGATIVE = "负";
    /** 金额精度，默认值2*/
    private static final int MONEY_PRECISION = 2;
    /** 零元整*/
    private static final String CN_ZEOR_FULL = "零元" + CN_FULL;
    public static String numberToCnUnit(BigDecimal numberOfMoney){
        StringBuffer stringBuffer = new StringBuffer();
        int signum = numberOfMoney.signum();
        // 零元整
        if(signum == 0){
            return CN_ZEOR_FULL;
        }
        // 进行四舍五入
        long number = numberOfMoney.movePointRight(MONEY_PRECISION).setScale(0, 4).abs().longValue();
        // 得到小数点后两位
        long scale = number % 100;
        int numUnit = 0;
        int numIndex = 0;
        boolean getZero = false;
        // 判断最后两位
        if(!(scale > 0)){
            numIndex = 2;
            number = number / 100;
            getZero = true;
        }
        if((scale > 0) && (!(scale % 10 > 0))){
            numIndex =1;
            number = number / 10;
            getZero = true;
        }
        int zeroSize = 0;
        while (true){
            if(number <= 0){
                break;
            }
            // 获取到最后一位数
            numUnit = (int)(number % 10);
            if(numUnit > 0){
                if((numIndex == 9) && (zeroSize >= 3)){
                    stringBuffer.insert(0, CN_UPPER_MONETRAY_UNIT[6]);
                }
                if((numIndex == 13) && (zeroSize >= 3)){
                    stringBuffer.insert(0, CN_UPPER_MONETRAY_UNIT[10]);
                }
                stringBuffer.insert(0,CN_UPPER_MONETRAY_UNIT[numIndex]);
                stringBuffer.insert(0,CN_UPPER_NUMBER[numUnit]);
                getZero = false;
                zeroSize = 0;
            } else {
                ++zeroSize;
                if(!(getZero)) {
                    stringBuffer.insert(0,CN_UPPER_NUMBER[numUnit]);
                }
                if(numIndex ==2){
                    if(number > 0){
                        stringBuffer.insert(0,CN_UPPER_MONETRAY_UNIT[numIndex]);
                    }
                } else if (((numIndex - 2) % 4 == 0) && (number % 1000 > 0)){
                    stringBuffer.insert(0,CN_UPPER_MONETRAY_UNIT[numIndex]);
                }
                getZero = true;
            }
            // 让number每次都去掉最后一个数
            number = number / 10;
            ++numIndex;
        }
        // 如果signum == -1，则说明输入的数字是负数，就在最前面追加特殊字符：负
        if(signum == -1){
            stringBuffer.insert(0, CN_NEGATIVE);
        }
        if(!(scale > 0)){
            stringBuffer.append(CN_FULL);
        }
        return stringBuffer.toString();
    }
}
