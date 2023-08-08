package edu.dlut.myspring.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD) //作用在属性上
@Retention(RetentionPolicy.RUNTIME) //作用时间：运行
public @interface Autowired {
}
