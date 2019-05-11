import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})
public class TestThread {
    @Test
    public void test1(){
        Object obj1 = new Object();
        Object obj2 = new Object();

            new Thread(){
                @Override
                public void run() {
                    synchronized (obj1){
                        try {
                            System.out.println("Thread1 obj1");
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        synchronized (obj2){
                            System.out.println("Thread1 obj2");
                        }
                    }


                }
            }.start();

            new Thread(){
                @Override
                public void run() {
                    synchronized (obj2){
                        System.out.println("Thread2 obj2");
                        synchronized (obj1){
                            System.out.println("Thread2 obj1");
                        }
                    }
                }
            }.start();
        }




}
