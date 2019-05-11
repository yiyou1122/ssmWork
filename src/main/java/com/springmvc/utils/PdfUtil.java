package com.springmvc.utils;

import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 向pdf中签字
 */
public class PdfUtil {
    public static void main(String[] args) throws Exception {
        PdfReader reader = null;
        PdfStamper stamper = null;
        String filePath = "E:/pdf/1.pdf";
        String newFilePath = "E:/pdf/ceshi/1.pdf";
        Map<String, Object> replaceMap = new HashMap<String, Object>();
        replaceMap.put("custName", "王雷雷");
        replaceMap.put("contractNo", "2341847192384798234");
        replaceMap.put("contractNo1", "111111111112384798234");
        replaceMap.put("certNo", "2323011993111111");
        replaceMap.put("address", "黑龙江");
        replaceMap.put("phone", "13888888888");
        replaceMap.put("busiAmt", "123,456,121,11");
        replaceMap.put("start", "1019-01-01");
        replaceMap.put("end", "1212-12-12");
        replaceMap.put("rate", "8.5");

        replaceMap.put("date1", "2019");
        replaceMap.put("date2", "01");
        replaceMap.put("date3", "22");

        replaceMap.put("up1", "11111111111111");
        replaceMap.put("up2", "11111111111111");
        replaceMap.put("down1", "2222222222222222");
        replaceMap.put("down2", "2222222222222222");

        //STFANGSO.TTF
        reader = new PdfReader(filePath);
        stamper = new PdfStamper(reader, new FileOutputStream(newFilePath));
        PdfContentByte over = stamper.getOverContent(8);
        over.beginText();
        BaseFont bf = BaseFont.createFont("E:/pdf/STFANGSO.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        over.setFontAndSize(bf, 15);
        over.setTextMatrix(30,30);
        // 狮桥额度借款合同

        over.showTextAligned(Element.ALIGN_LEFT,  (String)replaceMap.get("custName"), 125, 758, 0);
        over.showTextAligned(Element.ALIGN_LEFT,  (String)replaceMap.get("certNo"), 135, 738, 0);
        over.showTextAligned(Element.ALIGN_LEFT,  (String)replaceMap.get("busiAmt"), 146, 660, 0);
        over.showTextAligned(Element.ALIGN_LEFT,  (String)replaceMap.get("address"), 295, 660, 0);
        over.showTextAligned(Element.ALIGN_LEFT,  (String)replaceMap.get("contractNo"), 340, 642, 0);
        over.showTextAligned(Element.ALIGN_LEFT,  "12", 216, 575, 0);
        over.showTextAligned(Element.ALIGN_LEFT,  "8.4", 143, 555, 0);
        over.showTextAligned(Element.ALIGN_LEFT,  "熊海军", 126, 520, 0);
        over.showTextAligned(Element.ALIGN_LEFT,  "111111111111111", 320, 520, 0);
        over.showTextAligned(Element.ALIGN_LEFT,  "北京工商", 126, 503, 0);

        over.showTextAligned(Element.ALIGN_LEFT,  (String)replaceMap.get("date1"), 160, 447, 0);
        over.showTextAligned(Element.ALIGN_LEFT,  (String)replaceMap.get("date2"), 207, 447, 0);
        over.showTextAligned(Element.ALIGN_LEFT,  (String)replaceMap.get("date3"), 240, 447, 0);

//        over.showTextAligned(Element.ALIGN_LEFT,  (String)replaceMap.get("custName"), 175, 208, 0);
//        over.showTextAligned(Element.ALIGN_LEFT,  (String)replaceMap.get("up1"), 180, 350, 0);
//        over.showTextAligned(Element.ALIGN_LEFT,  (String)replaceMap.get("up1"), 180, 230, 0);
//        over.showTextAligned(Element.ALIGN_LEFT,  (String)replaceMap.get("date1"), 127, 564, 0);
//        over.showTextAligned(Element.ALIGN_LEFT,  (String)replaceMap.get("date2"), 176, 564, 0);
//        over.showTextAligned(Element.ALIGN_LEFT,  (String)replaceMap.get("date3"), 210, 564, 0);

        over.endText();
        stamper.close();
        reader.close();
    }

    /**
     * 向pdf签字
     * @param filePath 未签署的文件路径
     * @param newFilePath 签署完的文件路径
     * @param replaceList 签署参数
     * @throws Exception
     */
    public static void signFileContext(String filePath, String newFilePath, List<Map<String, Object>> replaceList) throws Exception{
        PdfReader reader = null;
        PdfStamper stamper = null;
        try {
            reader = new PdfReader(filePath);
            stamper = new PdfStamper(reader, new FileOutputStream(newFilePath));
            PdfContentByte over = null;
            if(null != replaceList && replaceList.size() > 0){
                for (Map replaceMap:replaceList) {
                    over = stamper.getOverContent((int)replaceMap.get("pageNum"));
                    over.beginText();
                    BaseFont bf = BaseFont.createFont("E:/pdf/STFANGSO.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
                    over.setFontAndSize(bf, 15);
                    over.setTextMatrix(30,30);
                    over.showTextAligned(0,  (String)replaceMap.get("param"),
                            Float.valueOf(replaceMap.get("x").toString()), Float.valueOf(replaceMap.get("y").toString()), 0);
                }
            }
            over.endText();
        } finally {
            if(null != stamper){
                stamper.close();
            }
            if(null != reader){
                reader.close();
            }
        }
    }
}
