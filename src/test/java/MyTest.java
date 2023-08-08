import com.sun.org.apache.xpath.internal.operations.Or;
import edu.dlut.myspring.core.AnnotationApplicationContext;
import edu.dlut.myspring.entity.Order;
import edu.dlut.myspring.entity.User;
import edu.dlut.myspring.utils.BeanDefinitionUtil;
import edu.dlut.myspring.utils.MyTools;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Arrays;

public class MyTest {
    public static void main(String[] args) {

    }

    @Test
    public  void test1() {
        BeanDefinitionUtil.getBeanDefinition(MyTools.getClasses("edu.dlut.myspring.entity"));
    }

    @Test
    public void test2() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        AnnotationApplicationContext context = new AnnotationApplicationContext("edu.dlut.myspring.entity");
    }

    @Test
    public void testBeanFactory() {

    }

    @Test
    public void test3() {
        User user = new User();
        Class clazz = user.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
//            System.out.println(field.getType().getName());
            try {
                Class clazz2 = Class.forName("edu.dlut.myspring.entity.Order");
                System.out.println(clazz2);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

}
