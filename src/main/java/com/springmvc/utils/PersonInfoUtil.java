package com.springmvc.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 判断身份证
 */
public class PersonInfoUtil {

    private static final int FIRST_IDCARD_LENGTH = 15;
    private static final int SECOND_IDCARD_LENGTH = 18;
    private static final String AGE = "age";
    private static final String GENDER = "gender";
    private static final String BIRTHDAT = "birthday";
    private static final String AREA = "area";
    private static final String[][] CODEEANDCITY = {
        {"11", "北京"},{"12", "天津"},{"13", "河北"},{"14", "山西"},
        {"15", "内蒙古"},{"21", "辽宁"},{"22", "吉林"},{"23", "黑龙江"},
        {"31", "上海"},{"32", "江苏"},{"33", "浙江"},{"34", "安徽"},
        {"35", "福建"},{"36", "江西"},{"37", "山东"},{"41", "河南"},{"42", "湖北"},
        {"43", "湖南"},{"44", "广东"},{"45", "广西"},{"46", "海南"},{"50", "重庆"},{"51", "四川"},
        {"52", "贵州"},{"53", "云南"},{"54", "西藏"},{"61", "陕西"},{"62", "甘肃"},{"63", "青海"},
        {"64", "宁夏"}, {"65", "新疆"},{"71", "台湾"},{"81", "香港"},{"82", "澳门"},{"91", "国外"}
    };

    public static Map<String, Object> getInfoIdentityCardNum(String id) throws Exception{
        if("".equals(id) || null == id){
            throw new Exception("参数错误");
        }

        IdcardValidator iv = new IdcardValidator();
        if(!iv.isValidatedAllIdCard(id)){
            throw new Exception("身份证号码格式错误");
        }
        Map<String, Object> info = new HashMap<String, Object>();
        if(FIRST_IDCARD_LENGTH == id.length()){
            info = getInfoByFirstIdentityCardNum(id);
        } else if(SECOND_IDCARD_LENGTH == id.length()){
            info = getInfoBySecoundIdentityCardNum(id);
        }
        return info;
    }

    /**
     * 根据一代身份证获取信息
     * @param id
     * @return
     */
    public static Map<String, Object> getInfoByFirstIdentityCardNum(String id){
        Map<String, Object> info = new HashMap<String, Object>();
        String areaNum = id.substring(0, 2);
        String areaStr = getAreaByAreaNum(areaNum);
        info.put(AREA, areaStr);

        int genderNum = (Integer.parseInt(id.substring(14)));
        String genderStr = ((genderNum & 1) != 0 ) ? "1" : "2";
        info.put(GENDER, genderStr);
        String birthdayNum = id.substring(6, 12);
        try {
            Date birthday = new SimpleDateFormat("yyMMdd").parse(birthdayNum);
            String birthdayStr = new SimpleDateFormat("yyyy-MM-dd").format(birthday);
            info.put(BIRTHDAT, birthdayStr);
        } catch (ParseException e){
            e.printStackTrace();
        }
        String ageStr = getCurrentByBirthdate(birthdayNum, "yyMMdd");
        if(!"-1".equals(ageStr)){
            info.put(AGE, ageStr);
        }
        return info;
    }

    /**
     * 根据二代身份证
     * @param id
     * @return
     */
    private static Map<String, Object> getInfoBySecoundIdentityCardNum(String id){
        Map<String, Object> info = new HashMap<String, Object>();
        String areaNum = id.substring(0, 2);
        String areaStr = getAreaByAreaNum(areaNum);
        info.put(AREA, areaStr);
        int genderNum = (Integer.parseInt(id.substring(16, 17)));
        String genderStr = ((genderNum & 1) != 0 ) ? "1" : "2";
        info.put(GENDER, genderStr);
        String birthdayNum = id.substring(6, 14);
        info.put(BIRTHDAT, birthdayNum);

        String ageStr = getCurrentByBirthdate(birthdayNum, "yyyyMMdd");
        if(!"-1".equals(ageStr)){
            info.put(AGE, ageStr);
        }
        return info;
    }

    public static String getAreaByAreaNum(String areaNum){
        String areaCode = "";
        boolean flag = true;
        for(int i = 0; (i < CODEEANDCITY.length - 1) && flag; i++){
            for(int j = 0; j < CODEEANDCITY[i].length; j++){
                if(CODEEANDCITY[i][0].equals(areaNum)){
                    areaCode = CODEEANDCITY[i][1];
                    flag = false;
                    break;
                }
            }
        }
        return areaCode;
    }

    @SuppressWarnings("deprecation")
    public static String getCurrentByBirthdate(String birthday, String formateDateStr){
        try {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat format = new SimpleDateFormat(formateDateStr);
            String currentTime = format.format(calendar.getTime());
            Date today = format.parse(currentTime);
            Date birthDay = format.parse(birthday);
            int age = today.getYear() - birthDay.getYear();
            return String.valueOf(age);
        } catch (ParseException e){
            return "-1";
        }
    }

    @SuppressWarnings({"unchecked", "unused", "all"})
    static class IdcardValidator{

        // 每位加权因子
        private int power[] = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};

        /**
         * 验证身份
         * @param idCard
         * @return
         */
        public boolean isValidatedAllIdCard(String idCard){
            if(idCard.length() == 15){
                idCard = this.canverIdCard15Bit(idCard);
            }
            return this.isValidated18IdCard(idCard);
        }

        /**
         * 将15位身份证转换成18位
         * @param idCard
         * @return
         */
        public String canverIdCard15Bit(String idCard){
            String idCard17 = null;
            if(idCard.length() != 15){
                return null;
            }
            if(isDigital(idCard)){
                String birthday = idCard.substring(6, 12);
                Date birthdate = null;
                try{
                    birthdate = new SimpleDateFormat("yyMMdd").parse(birthday);
                } catch (ParseException e){
                    e.printStackTrace();
                }
                Calendar cday = Calendar.getInstance();
                cday.setTime(birthdate);
                String year = String.valueOf(cday.get(Calendar.YEAR));
                idCard17 = idCard.substring(0, 6) + year + idCard.substring(8);
                char c[] = idCard17.toCharArray();
                String checkCode = "";
                if(null != c){
                    int bit[] = new int[idCard17.length()];
                    // 将字符数组转换为整型数组
                    bit = converCharToInt(c);
                    int sum17 = 0;
                    sum17 = getPowerSum(bit);
                    // 获取和值于11取模得到余数进行校验码
                    checkCode = getCheckCodeBySum(sum17);
                    if(null == checkCode){
                        return null;
                    }
                    idCard17 += checkCode;
                }
            } else {
                return null;
            }
            return idCard17;
        }

        /**
         * 判断18位身份证
         * @param idCard
         * @return
         */
        public boolean isValidated18IdCard(String idCard){
            if(idCard.length() != 18){
                return false;
            }

            // 获取前17位
            String idCard17 = idCard.substring(0, 17);
            // 获取第18位
            String idCard18Code = idCard.substring(17, 18);
            char c[] = null;
            String checkCode = "";
            // 是否都位数字
            if(!isDigital(idCard17)){
                c = idCard17.toCharArray();
            } else {
                return false;
            }
            if(null != c){
                int bit[] = new int[idCard17.length()];
                bit = converCharToInt(c);
                int sum17 = 0;
                sum17 = getPowerSum(bit);
                // 将和值与11取模得到余数进行校验码判断
                checkCode = getCheckCodeBySum(sum17);
                if(null == checkCode){
                    return false;
                }
                // 将身份证的第18位于算出来的校验码进行匹配，不相等就是假
                if(!idCard18Code.equalsIgnoreCase(checkCode)){
                    return false;
                }
            }
            return true;
        }

        /**
         * 将和值与11取模得到余数进行校验码判断
         * @param sum17
         * @return
         */
        public String getCheckCodeBySum(int sum17){
            String checkCode = null;
            switch (sum17 % 11){
                case 10:
                    checkCode = "2";
                    break;
                case 9:
                    checkCode = "3";
                    break;
                case 8:
                    checkCode = "4";
                    break;
                case 7:
                    checkCode = "5";
                    break;
                case 6:
                    checkCode = "6";
                    break;
                case 5:
                    checkCode = "7";
                    break;
                case 4:
                    checkCode = "8";
                    break;
                case 3:
                    checkCode = "9";
                    break;
                case 2:
                    checkCode = "x";
                    break;
                case 1:
                    checkCode = "0";
                    break;
                case 0:
                    checkCode = "1";
                    break;
            }
            return checkCode;
        }

        /**
         * 数字验证
         * @param str
         * @return
         */
        public boolean  isDigital(String str){
            return str == null || "".equals(str) ? false : str.matches("^[0-9]]*$");
        }

        /**
         * 将字符串数组转换为整型数组
         * @param c
         * @return
         * @throws NumberFormatException
         */
        public int[] converCharToInt(char[] c) throws NumberFormatException {
            int[] i = new int[c.length];
            int j = 0;
            for(char temp : c){
                i[j++] = Integer.parseInt(String.valueOf(temp));
            }
            return i;
        }

        /**
         * 将身份证的每位和加权因子相乘在得到和值
         * @param bit
         * @return
         */
        public int getPowerSum(int[] bit){
            int sum = 0;
            if(power.length != bit.length){
                return sum;
            }
            for(int i = 0; i < bit.length; i++){
                for(int j = 0; j < power.length; j++){
                    if(i == j){
                        sum = sum + bit[i]*power[j];
                    }
                }
            }
            return sum;
        }
    }
}
