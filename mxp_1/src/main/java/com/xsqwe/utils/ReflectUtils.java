package com.xsqwe.utils;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ReflectUtils {

    public static Object getGetMethodValue(Object object, String getMethod) {
        List<Method> methods = new ArrayList<>();
        getAllMethods(methods, object.getClass());
        for(Method method : methods) {
            if(method.getName().equals(getMethod)) {
                try {
                    return method.invoke(object);
                } catch (Exception e) {
                    log.error("invoke method " + method.getName() + " from " + JSON.toJSONString(object) + " error", e);
                }
            }
        }
        return null;
    }

    public static Object getFieldValue(Object object, Field field) {
        try {
            field.setAccessible(true);
            return field.get(object);
        } catch (IllegalAccessException e) {
            log.error("get field " + field.getName() + " from " + JSON.toJSONString(object) + " error", e);
            return null;
        }
    }

    public static void getAllMethods(List<Method> methods, Class<?> clazz) {
        Method[] currentMethods = clazz.getDeclaredMethods();
        if(methods == null) methods = new ArrayList<>();
        for(Method method : currentMethods) {
            methods.add(method);
        }
        if(clazz.getSuperclass() != null && !clazz.getSuperclass().getName().equals(Object.class.getName())) {
            getAllMethods(methods, clazz.getSuperclass());
            return;
        }
        return;
    }

    public static void getAllFields(List<Field> fields, Class<?> clazz) {
        Field[] currentFields = clazz.getDeclaredFields();
        if(fields == null) fields = new ArrayList<>();
        for(Field field : currentFields) {
            fields.add(field);
        }
        if(clazz.getSuperclass() != null && !clazz.getSuperclass().getName().equals(Object.class.getName())) {
            getAllFields(fields, clazz.getSuperclass());
            return;
        }

        return;
    }

}
