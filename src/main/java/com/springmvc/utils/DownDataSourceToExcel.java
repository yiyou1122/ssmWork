package com.springmvc.utils;


import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

public class DownDataSourceToExcel {
    public static final Logger logger = LoggerFactory.getLogger(DownDataSourceToExcel.class.getName());
    // 文件后缀
    private static final String XLS = "XLS";

    /**
     * 数据写入Excel文件
     * @param path 文件路径，包含文件名，如：D://file//demo.xls
     * @param name sheet名称
     * @param titles 行标题
     * @param values 数据集合，key为标题，value为数据
     * @param titleCnName Excel文件表头的中文名称描述
     * @return
     */
    public static boolean writerExcel(String path, String name, List<String> titles,
                                      List<Map<String, Object>> values, List<String> titleCnName){
        if(StringUtils.isNotEmpty(path)){
            // 从文件路径中获取文件的类型
            if(CollectionUtils.isNotEmpty(values)){
                String style = path.substring(path.lastIndexOf("."), path.length()).toUpperCase();
                // 下载文件路径处理
                String newFileDir = path.substring(0, path.lastIndexOf("/"));
                File file = new File(newFileDir);
                if(logger.isInfoEnabled()){
                    logger.info("导出文件的下载路径：[{}]", path);
                }
                if(!file.isDirectory()){
                    file.mkdirs();
                }
                return generateWorkbook(path, name, style, titles, values, titleCnName);
            }
        }
        if(logger.isInfoEnabled()){
            logger.info("导出数据路径[{}], 内容为[{}]", new Object[]{path, String.valueOf(values)});
        }
        return false;
    }

    /**
     * 将数据斜土指定path下的Excel文件中
     * @param path 文件存储路径
     * @param name sheet名
     * @param style Excel类型
     * @param titles 标题
     * @param values 数据
     * @param titleCnName
     * @return
     */
    private static final boolean generateWorkbook(String path, String name, String style, List<String> titles,
                                                  List<Map<String, Object>> values, List<String> titleCnName){
        Workbook workbook;
        if(XLS.equals(style.toUpperCase())){
            workbook = new HSSFWorkbook();
        } else {
            workbook = new HSSFWorkbook();
        }
        // 生成一个表格
        Sheet sheet;
        if(StringUtils.isEmpty(name)){
            // name 为空则使用默认值
            sheet = workbook.createSheet();
        } else {
            sheet = workbook.createSheet(name);
        }
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth((short) 15);
        // 生成样式
        Map<String, CellStyle> styles = createStyle(workbook);
        // 创建标题行
        Row row = sheet.createRow(0);
        // 存储标题在Excel文件中的序号
        Map<String, Integer> titleOrder = Maps.newHashMap();
        for(int i = 0; i < titleCnName.size(); i++){
            Cell cell = row.createCell(i);
            // 设置表格中文名称
            cell.setCellStyle(styles.get("header"));
            String title = titleCnName.get(i);
            String enTitle = titles.get(i);
            cell.setCellValue(title);
            // 将表格英文名称作为key值传入
            titleOrder.put(enTitle, i);
        }
        // 写入正文
        Iterator<Map<String, Object>> iterator = values.iterator();
        // 行号
        int index = 0;
        while (iterator.hasNext()){
            // 除去标题行，从第一行开始写
            index ++;
            row = sheet.createRow(index);
            Map<String, Object> value = iterator.next();
            for(Map.Entry<String, Object> map : value.entrySet()){
                // 获取列明
                String enTitle = map.getKey();
                // 根据列明获取序列号
                int i = titleOrder.get(enTitle);
                // 在指定序列出创建cell
                Cell cell = row.createCell(i);
                // 设置cell的样式
                if(index % 2 == 1){
                    cell.setCellStyle(styles.get("cellA"));
                } else {
                    cell.setCellStyle(styles.get("cellB"));
                }
                // 获取列的值
                Object object = map.getValue();
                // 判断object类型
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                if(object instanceof Double){
                    cell.setCellValue((Double) object);
                } else if (object instanceof Date){
                    String time = simpleDateFormat.format((Date) object);
                    cell.setCellValue(time);
                } else  if (object instanceof Calendar){
                    Calendar calendar = (Calendar) object;
                    String time = simpleDateFormat.format(calendar.getTime());
                    cell.setCellValue(time);
                } else if (object instanceof Boolean){
                    cell.setCellValue((Boolean) object);
                } else if (object == null){
                    cell.setCellValue("");
                } else {
                    cell.setCellValue(object.toString());
                }
            }
        }
        // 写入到文件中
        boolean isCorrect;
        OutputStream outputStream = null;
        try {
            File file = new File(path);
            outputStream = new FileOutputStream(file);
            workbook.write(outputStream);
            isCorrect = true;
        } catch (IOException e){
            isCorrect = false;
            logger.error("写入失败{}", e.getMessage());
        } finally {
            try {
                workbook.close();
                outputStream.close();
            } catch (IOException e){
                isCorrect = false;
                logger.error("关闭失败：{}", e.getMessage());
            }
        }
        return isCorrect;
    }

    /**
     * 创建单元格样式
     * @param workbook
     * @return
     */
    private static Map<String, CellStyle> createStyle(Workbook workbook){
        Map<String, CellStyle> styles = Maps.newHashMap();
        // 标题样式
        CellStyle titleStyle = workbook.createCellStyle();
        // 水平对齐
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 样式锁定
        titleStyle.setLocked(true);
        titleStyle.setFillBackgroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        // 字体
        Font titleFont = workbook.createFont();
        titleFont.setFontHeightInPoints((short) 16);
        titleFont.setBold(true);
        titleFont.setFontName("微软雅黑");
        titleStyle.setFont(titleFont);
        styles.put("title", titleStyle);
        // 文件头样式
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 前景色
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        // 颜色填充方式
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setWrapText(true);
        // 设置边界
        headerStyle.setBorderRight(BorderStyle.THIN);
        headerStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        Font headerFont = workbook.createFont();
        headerFont.setFontHeightInPoints((short) 12);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        headerFont.setFontName("微软雅黑");
        headerStyle.setFont(headerFont);
        styles.put("header", headerStyle);

        Font cellStyleFont = workbook.createFont();
        cellStyleFont.setFontHeightInPoints((short) 12);
        cellStyleFont.setColor(IndexedColors.BLUE_GREY.getIndex());
        cellStyleFont.setFontName("微软雅黑");
        // 正文样式A
        CellStyle cellStyleA = workbook.createCellStyle();
        cellStyleA.setAlignment(HorizontalAlignment.CENTER);
        cellStyleA.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyleA.setWrapText(true);
        cellStyleA.setBorderRight(BorderStyle.THIN);
        cellStyleA.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleA.setBorderLeft(BorderStyle.THIN);
        cellStyleA.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleA.setBorderTop(BorderStyle.THIN);
        cellStyleA.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleA.setBorderBottom(BorderStyle.THIN);
        cellStyleA.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleA.setFont(cellStyleFont);
        styles.put("cellA", cellStyleA);
        // 正文样式B，添加前景色为浅黄色
        CellStyle cellStyleB = workbook.createCellStyle();
        cellStyleB.setAlignment(HorizontalAlignment.CENTER);
        cellStyleB.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyleB.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        cellStyleB.setWrapText(true);
        cellStyleB.setBorderRight(BorderStyle.THIN);
        cellStyleB.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleB.setBorderLeft(BorderStyle.THIN);
        cellStyleB.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleB.setBorderTop(BorderStyle.THIN);
        cellStyleB.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleB.setBorderBottom(BorderStyle.THIN);
        cellStyleB.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleB.setFont(cellStyleFont);
        styles.put("cellB", cellStyleB);
        return styles;
    }
}
