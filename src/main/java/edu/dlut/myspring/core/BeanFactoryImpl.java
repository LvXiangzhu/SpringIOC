package edu.dlut.myspring.core;

import edu.dlut.myspring.annotation.Autowired;
import edu.dlut.myspring.annotation.Component;
import edu.dlut.myspring.annotation.Qualifier;
import edu.dlut.myspring.annotation.Value;
import edu.dlut.myspring.bean.BeanDefinition;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class BeanFactoryImpl implements BeanFactory{

    private static final Map<String, Object> beans = new ConcurrentHashMap<>();

    //二级缓存解决循环依赖
    private static final Map<String, Object> earlyBeans = new ConcurrentHashMap<>();

    private static final Set<String> beanNames = new HashSet<>();
    private static final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    @Override
    public Object getBean(String beanName) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        //如果bean存在，直接返回
        if(beans.containsKey(beanName)) {
            return beans.get(beanName);
        }

        //如果Bean在二级缓存，说明有循环依赖，直接返回
        if(earlyBeans.containsKey(beanName)) {
            System.out.println("循环依赖，提前返回未加载完的bean：" + beanName);
            return earlyBeans.get(beanName);
        }

        //创建Bean（无法解决循环引用）
        createValueObject(beanName);
        Object bean = createAutoWiredObject(beanName);

        return bean;
    }

    public Object createAutoWiredObject(String name) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        BeanDefinition beanDefinition = beanDefinitionMap.get(name);
        if(beanDefinition == null) {
            throw new ClassNotFoundException("找不到对应的beanDefinition");
        }
        Class<?> clazz = beanDefinition.getBeanClass();
        Object obj;
        if(beans.containsKey(name)) {
            obj = beans.get(name);
        }else {
            obj = clazz.newInstance();
        }

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if(field.getAnnotation(Autowired.class) == null) {
                continue;
            }
            if(field.getAnnotation(Qualifier.class) != null) {
                //byName
                String beanName = field.getAnnotation(Qualifier.class).value();

                //先放入二级缓存,防止循环依赖
                earlyBeans.put(name, obj);

                Object bean = getBean(beanName);
                field.set(obj, bean);

            }else {
                //byType
                Object clazzBean = null;
                for (String beanName : beans.keySet()) {
                    if(beans.get(beanName).getClass() == field.getType()) {
                        clazzBean = beans.get(beanName);
                    }
                }
                if(clazzBean == null) {
                    Class<?> clazz2 = Class.forName(field.getType().getName());
                    if(clazz2.getAnnotation(Component.class) == null) {
                        throw new RuntimeException("该属性的Bean不存在");
                    }
                    String beanName = clazz2.getAnnotation(Component.class).value();

                    //先放入二级缓存,防止循环依赖
                    earlyBeans.put(name, obj);

                    Object bean = getBean(beanName);
                    field.set(obj, bean);
                }
            }
        }

        //对象创建完了，从二级缓存移除
        earlyBeans.remove(name, obj);
        beans.put(name, obj);
        return obj;
    }

    public Object createValueObject(String name) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        BeanDefinition beanDefinition = beanDefinitionMap.get(name);
        if(beanDefinition == null) {
            throw new ClassNotFoundException("找不到对应的beanDefinition");
        }
        Class clazz = beanDefinition.getBeanClass();
        Object obj;
        if(beans.containsKey(name)) {
            obj = beans.get(name);
        }else {
            obj = clazz.newInstance();
        }

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            //取消安全检查
            field.setAccessible(true);
            if(field.getAnnotation(Value.class) != null) {
                //注意：这块Value的String类型要转换为该属性对应的类型
                String val = field.getAnnotation(Value.class).value();
                switch (field.getType().getSimpleName()) {
                    case "int":
                    case "Integer":
                        field.set(obj, Integer.parseInt(val));
                        break;
                    case "String":
                        field.set(obj, val);
                        break;
                    case "double":
                    case "Double":
                        field.set(obj, Double.parseDouble(val));
                        break;
                    case "float":
                    case "Float":
                        field.set(obj, Float.parseFloat(val));
                        break;
                    case "char":
                    case "Character":
                        field.set(obj, val.charAt(0));
                        break;
                    case "boolean":
                    case "Boolean":
                        field.set(obj, Boolean.parseBoolean(val));
                        break;
                    case "long":
                    case "Long":
                        field.set(obj, Long.parseLong(val));
                        break;
                    case "byte":
                    case "Byte":
                        field.set(obj, Byte.parseByte(val));
                        break;
                    case "short":
                    case "Short":
                        field.set(obj, Short.parseShort(val));
                        break;
                }
            }
        }
        beans.put(name, obj);
        return obj;
    }

    protected void registerBean(BeanDefinition beanDefinition) {
        beanNames.add(beanDefinition.getBeanName());
        beanDefinitionMap.put(beanDefinition.getBeanName(), beanDefinition);
    }
}
