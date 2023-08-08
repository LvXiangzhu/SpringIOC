package edu.dlut.myspring.bean;

import edu.dlut.myspring.annotation.Component;
import lombok.Data;

public class BeanDefinition {
    String beanName; // bean的名字:Component注解里的值 或 类名首字母小写
    String className; //类的名字
    Class beanClass; // bean的类型

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public Class getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }
}
