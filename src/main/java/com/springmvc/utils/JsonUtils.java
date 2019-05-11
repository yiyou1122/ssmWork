package com.springmvc.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.parser.deserializer.JSONArrayDeserializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import org.apache.poi.ss.formula.functions.T;
import org.codehaus.jackson.map.JsonDeserializer;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonUtils {
    private static final SerializeConfig timeMapping = new SerializeConfig();
    static {
        timeMapping.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * list放入map然后转json
     * @param list
     * @return
     */
    public static String jsonList(List<?> list){
        Map<String, Object> map = new HashMap<>();
        map.put("jsonData", list);
        return JSON.toJSONString(map, timeMapping);
    }

    /**
     * menuList放入map然后转json
     * @param list
     * @return
     */
    public static String jsonMenuList(List<?> list){
        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("type", "single");
        map.put("maxlength", list.size());
        return JSON.toJSONString(map, timeMapping);
    }

    /**
     * Object转json
     * @param object
     * @return
     */
    public static String jsonObject(Object object){
        return JSON.toJSONString(object, timeMapping);
    }

    /**
     * 分页pageList放入map中转json，分页专用
     * @param list
     * @param totalCount
     * @param pageNo
     * @return
     */
    public static String jsonPageList(List<?> list, int totalCount, int pageNo){
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("rows", list);
            map.put("totalRows", totalCount);
            map.put("pageNo", pageNo);
            return JSON.toJSONString(map, timeMapping);
        } catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }

    /**
     * treeList放入map中转json，树专用
     * @param list
     * @return
     */
    public static String jsonTree(List<?> list){
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("treeNodes", list);
            return JSON.toJSONString(map, timeMapping);
        } catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }

    /**
     * optionList放入map中转json，下拉树专用
     * @param list
     * @return
     */
    public static String jsonOptionTree(List<?> list){
            Map<String, Object> map = new HashMap<>();
            map.put("list", list);
            return JSON.toJSONString(map, timeMapping);
    }

    /**
     * String放入map转json
     * @param str
     * @return
     */
    public static String jsonString(String str){
        Map<String, Object> map = new HashMap<>();
        map.put("jsonData", str);
        return JSON.toJSONString(map, timeMapping);
    }

    /**
     * 返回页面信息String放入map中转json，返回页面信息专用
     * @param status
     * @param msg
     * @return
     */
    public static String jsonReturnStr(boolean status, String msg){
        Map<String, Object> map = new HashMap<>();
        map.put("success", status);
        map.put("msg", msg);
        return JSON.toJSONString(map, timeMapping);
    }

    /**
     * 返回页面信息String放入map中转json，返回页面信息专用
     * @param status
     * @param msg
     * @return
     */
    public static String jsonReturnStr(boolean status, String msg, String content){
        Map<String, Object> map = new HashMap<>();
        map.put("success", status);
        map.put("msg", msg);
        map.put("content", content);
        return JSON.toJSONString(map, timeMapping);
    }

    /**
     * map转json
     * @param map
     * @return
     */
    public static String jsonMap(Map<String, Object> map){
        return JSON.toJSONString(map, timeMapping);
    }

    /**
     * json转map
     * @param obj
     * @return
     */
    public static String jsonToMap(String obj){
        return JSON.toJSONString(obj);
    }

    /**
     * 将对象转json
     * @param obj
     * @return
     */
    public static String getJsonStrFromObj(Object obj){
        return JSON.toJSONString(obj, timeMapping);
    }

    /**
     * 对象转json，匹配日期格式
     * @param obj
     * @param dataFormat
     * @return
     */
    public static String getJsonStrFromObj(Object obj, String dataFormat){
        return JSON.toJSONStringWithDateFormat(obj, dataFormat);
    }

    /**
     * 对象转json
     * @param obj
     * @return
     */
    public static String getJsonToObj(Object obj){
        return JSON.toJSONString(obj, timeMapping);
    }

    /**
     * json字符串转指定的javaBean
     * @param source
     * @param bean
     * @param <T>
     * @return
     */
    public static <T> T getObjFromJsonStr(String source, Class<T> bean){
        return JSON.parseObject(source, bean);
    }

    /**
     * 将json字符串转成对象，单条的，不是数组或集合
     * @param str
     * @return
     */
    public static Object getObjFromJsonStr(String str){
        return JSON.parseObject(str);
    }

//    public static <T> List<T> getListFromJsonStr(String source, Class<T> c){
//        return new JSONDeserializer<List<T>>().use("values", c).deserialize(source);
//    }

    /**
     * 将数组或集合的json字符串转成对象集合
     * @param source
     * @param bean
     * @param <T>
     * @return
     */
    public static <T> List<T> getListFromJsonStr(String source, Class<T> bean){
        return JSON.parseArray(source, bean);
    }

    /**
     * json字符串转List
     * @param json
     * @param classes
     * @return
     */
    public static List<T> getListJsonStr(String json, Class<T> classes){
        return JSONArray.parseArray(json, classes);
    }

}
