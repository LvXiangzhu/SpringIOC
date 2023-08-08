package edu.dlut.myspring.core;

import edu.dlut.myspring.bean.BeanDefinition;
import edu.dlut.myspring.utils.BeanDefinitionUtil;
import edu.dlut.myspring.utils.MyTools;

import java.util.Set;

public class AnnotationApplicationContext extends BeanFactoryImpl{

    public AnnotationApplicationContext(String packages) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        //获取包名下的所有的类
        Set<Class<?>> classes = MyTools.getClasses(packages);
        //获取类中所有的Bean
        Set<BeanDefinition> beanDefinitions = BeanDefinitionUtil.getBeanDefinition(classes);

        if(beanDefinitions != null && !beanDefinitions.isEmpty()) {
            for (BeanDefinition definition : beanDefinitions) {
                registerBean(definition);
            }
        }

//        createObject(beanDefinitions);
    }
}
