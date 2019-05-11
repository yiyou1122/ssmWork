import com.springmvc.utils.PdfUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})
public class TestPdf {

    @Test
    public void test1(){
        String filePath = "E:/pdf/4.pdf";
        String newFilePath = "E:/pdf/ceshi/4.pdf";
        List<Map<String, Object>> replaceList = new ArrayList<>();
        Map<String, Object> replaceMap = new HashMap<String, Object>();
        replaceMap.put("param", "11111111111111");
        replaceMap.put("x", 100f);
        replaceMap.put("y", 100f);
        replaceMap.put("pageNum", 1);
        replaceList.add(replaceMap);
        try {
            PdfUtil.signFileContext(filePath, newFilePath, replaceList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2(){
        Integer it=new Integer(1);
        Integer it2=new Integer(1);
        Integer is1=3;
        Integer is2=3;
        if(it==it2){
            System.out.println("相等");
        }else{
            System.out.println("不相等");
        }
        if(is1==is2){
            System.out.println("不new创建的Integer对象相等");
        }else{
            System.out.println("不new创建的Integer对象不相等");
        }
    }
}
