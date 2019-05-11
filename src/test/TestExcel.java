import com.springmvc.utils.DownDataSourceToExcel;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.SimpleDateFormat;
import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})
public class TestExcel {

    @Test
    public void test1(){
        String path = "E:/testExcel/Excel1.xlsx";
        String fileName = "测试文件";
        List<String> titles = new ArrayList<>();
        titles.add("name");
        titles.add("age");
        titles.add("gander");
        titles.add("job");
        List<String> titles1 = new ArrayList<>();
        titles1.add("姓名");
        titles1.add("年龄");
        titles1.add("性别");
        titles1.add("工作");
        List<Map<String, Object>> lists = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("name", "熊海军");
        map.put("age", "25");
        map.put("gander", "男");
        map.put("job", "开发");
        lists.add(map);
        boolean b = DownDataSourceToExcel.writerExcel(path, fileName, titles, lists, titles1);
        System.out.println(b);
    }

    @Test
    public void test2(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String format = simpleDateFormat.format(new Date());
        System.out.println("format = " + format);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        Date date = calendar.getTime();

        System.out.println("format = " + simpleDateFormat.format(date));

    }
}
