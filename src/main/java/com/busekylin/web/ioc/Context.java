package com.busekylin.web.ioc;

import com.busekylin.web.ioc.annotations.Bean;
import com.busekylin.web.ioc.annotations.Component;
import com.busekylin.web.ioc.annotations.Configuration;
import com.busekylin.web.ioc.annotations.Inject;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class Context {
    public static void init() throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
        List<Class> classes = BeansList.getAllClasses();

//        实例化所有的@Component和@Configuration类
        for (Class clazz : classes) {
            if (clazz.isAnnotationPresent(Component.class) || clazz.isAnnotationPresent(Configuration.class)) {
                BeansPool.getInstance().setObject(clazz, clazz.newInstance());
            }
        }

//        实例化@Bean方法生成的类
        for (Class clazz : classes) {
            if (clazz.isAnnotationPresent(Configuration.class)) {
                Method[] methods = clazz.getDeclaredMethods();

                for (Method method : methods) {
                    if (method.isAnnotationPresent(Bean.class)) {
                        Object object = BeansPool.getInstance().getObject(clazz);
                        Object result = method.invoke(object);

                        BeansPool.getInstance().setObject(result.getClass(), result);
                    }
                }
            }
        }

//        注入@Component类中所有标注有@Inject的字段
        for (Class clazz : classes) {
            if (clazz.isAnnotationPresent(Component.class)) {
                Field[] fields = clazz.getDeclaredFields();

                for(Field field: fields){
                    if(field.isAnnotationPresent(Inject.class)){
                        Object classObject = BeansPool.getInstance().getObject(clazz);
                        Object fieldObject = BeansPool.getInstance().getObject(field.getType());

                        field.setAccessible(true);
                        field.set(classObject, fieldObject);
                    }
                }
            }
        }
    }

    public static Object getBean(Class clazz){
        return BeansPool.getInstance().getObject(clazz);
    }
}
