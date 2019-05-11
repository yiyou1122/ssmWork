import com.springmvc.bean.SysUser;
import com.springmvc.bean.Video;
import com.springmvc.dao.SysUserMapper;
import com.springmvc.dao.VideoDao;
import com.springmvc.service.VideoService;
import com.springmvc.utils.PrimaryKeyGeneratot;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})
public class TestVideo {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Resource

    @Test
    public void test1(){
        List<SysUser> sysUsers = sysUserMapper.selectByName("bbbb");
        SysUser user = sysUserMapper.queryByName("bbbb");
        System.out.println(user);
        List<Map<String, Object>> listMap = new ArrayList<>();
        Map<String, Object> resultMap = new HashMap<>();
        String id = null;
        for(int i = 0; i < sysUsers.size(); i++){
            SysUser sysUser = sysUsers.get(i);
            if("bbbb".equals(sysUser.getUsername())){
                Map<String, Object> map = new HashMap<>();
                id = sysUser.getId();
                map.put("name", sysUser.getName());
                map.put("username", sysUser.getUsername());
                map.put("password", sysUser.getPassword());
                listMap.add(map);
            }
        }
        resultMap.put("id", id);
        resultMap.put("list", listMap);
        System.out.println(resultMap);
    }

    @Test
    public void test2(){
        Map<String, Object> map = new HashMap<>();
        map.put("bizNo", "");
        String bizNo = (String) map.get("bizNo");
        System.out.println("bizNo = " + bizNo);
        if(StringUtils.isEmpty(bizNo)){
            bizNo = PrimaryKeyGeneratot.getTwentyOfNextId();
            map.put("bizNo", bizNo);
        }
        String bizNo1 = (String) map.get("bizNo");
        System.out.println("bizNo1 = " + bizNo1);
    }
}
