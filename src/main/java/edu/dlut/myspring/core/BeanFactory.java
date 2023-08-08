package edu.dlut.myspring.core;

public interface BeanFactory {
    public Object getBean(String beanName) throws ClassNotFoundException, InstantiationException, IllegalAccessException;
}
