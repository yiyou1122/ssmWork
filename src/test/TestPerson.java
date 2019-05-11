import java.util.Map;
import com.springmvc.utils.PersonInfoUtil;

public class TestPerson {

    public static void main(String[] args){
        try {
            Map<String, Object> infoIdentityCardNum = PersonInfoUtil.getInfoIdentityCardNum("232301199312142218");
            System.out.print(infoIdentityCardNum);
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
