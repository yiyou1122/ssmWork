import com.springmvc.utils.NumberToCn;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})
public class TestNum {
    @Test
    public void test1(){
        String s = NumberToCn.numberToCnUnit(new BigDecimal("12345678.1899"));
        System.out.println("结果是：" + s);
    }

    @Test
    public void test2(){
        BigDecimal bg = new BigDecimal("0");
        System.out.println(bg);
    }
}
