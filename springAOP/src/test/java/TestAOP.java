import com.yc.AppConfig;
import com.yc.biz.StudentBiz;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class) //使用junit4进行测试
@ContextConfiguration(classes = AppConfig.class)
public class TestAOP {
    @Autowired
    private StudentBiz studentBiz;

    @Test
    public void testAdd(){
        studentBiz.add("张三");
    }
}
