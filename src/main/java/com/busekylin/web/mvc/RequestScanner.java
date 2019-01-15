package com.busekylin.web.mvc;

import com.busekylin.web.ioc.BeansList;
import com.busekylin.web.mvc.annotations.Controller;
import com.busekylin.web.mvc.annotations.RequestMapping;
import com.busekylin.web.mvc.enums.RequestMethod;

import java.lang.reflect.Method;
import java.util.List;

public class RequestScanner {
    public static void init() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        List<Class> classes = BeansList.getAllClasses();

        for (Class clazz : classes) {
            if (clazz.isAnnotationPresent(Controller.class)) {
                Method[] methods = clazz.getDeclaredMethods();

                for (Method method : methods) {
                    if (method.isAnnotationPresent(RequestMapping.class)) {
                        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                        String url = requestMapping.value();
                        RequestMethod requestMethod = requestMapping.method();

                        HttpUrlMappingPool.getInstance().setMapping(
                                HttpUrlMapping.builder()
                                        .url(url)
                                        .requestMethod(requestMethod)
                                        .clazz(clazz)
                                        .method(method)
                                        .build());

                    }
                }
            }
        }
    }
}
