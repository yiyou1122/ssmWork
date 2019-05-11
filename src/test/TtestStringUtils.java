import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class TtestStringUtils {
    public static void main(String[] args) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("custNo","");
//        String custNo = (String)map.get("custNo");
//        System.out.println(custNo);
        System.out.println(!"".equals(map.get("custNo")));
        System.out.println("----------------------------");
        System.out.println(StringUtils.isNotBlank(""));
        System.out.println(StringUtils.isNotBlank(" "));
        System.out.println(StringUtils.isNotBlank(null));
        System.out.println("----------------------------");
        System.out.println(StringUtils.isNotEmpty((String)map.get("custNo")));
        System.out.println(StringUtils.isNotEmpty(" "));
        System.out.println(StringUtils.isNotEmpty(null));
    }
}
