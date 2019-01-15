package com.busekylin.web.ioc;

import java.util.HashMap;
import java.util.Map;

/**
 * bean对象池
 */
public class BeansPool {
    private static class BeansPoolSingleton {
        private static final BeansPool INSTANCE = new BeansPool();
    }

    private BeansPool() {
        this.beansMap = new HashMap<>();
    }

    public static BeansPool getInstance() {
        return BeansPoolSingleton.INSTANCE;
    }

    private Map<Class, Object> beansMap;

    public Map<Class, Object> allClass(){
        return beansMap;
    }

    public Object getObject(Class clazz){
        return beansMap.get(clazz);
    }

    public void setObject(Class clazz, Object object){
        beansMap.put(clazz, object);
    }
}
