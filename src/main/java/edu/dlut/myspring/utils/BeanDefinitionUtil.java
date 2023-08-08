package edu.dlut.myspring.utils;

import edu.dlut.myspring.annotation.Component;
import edu.dlut.myspring.bean.BeanDefinition;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class BeanDefinitionUtil {
    public static Set<BeanDefinition> getBeanDefinition(Set<Class<?>> classes) {
        Set<BeanDefinition> beanDefinitions = new HashSet<>();

        for (Class<?> clazz : classes) {
            //找到类中所有的bean，有注解的才是bean，没有注解不用放BeanDefinition里
            if(clazz.getAnnotation(Component.class) == null) {
                continue;
            }

            // bean的名字:Component注解里的值 或 类名首字母小写
            String beanName = clazz.getAnnotation(Component.class).value();
            if("".equals(beanName)) {
                beanName = clazz.getSimpleName().substring(0,1).toLowerCase()+clazz.getSimpleName().substring(1);
            }
            String className = clazz.getSimpleName().substring(0,1).toLowerCase()+clazz.getSimpleName().substring(1);

            BeanDefinition beanDefinition = new BeanDefinition();
            beanDefinition.setBeanName(beanName);
            beanDefinition.setClassName(className);
            beanDefinition.setBeanClass(clazz);

            beanDefinitions.add(beanDefinition);
        }
        return beanDefinitions;
    }
}
