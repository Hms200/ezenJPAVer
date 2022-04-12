package com.ezenjpa.ezenjpaver.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Component
@Slf4j
public class EntityUpdateUtil {

    public Object entityUpdateUtil(Object dto, Object entity) throws InvocationTargetException, IllegalAccessException {

        Class<?> dtoCls = dto.getClass();
        Class<?> entityCls = entity.getClass();

        Field[] dtoFields = dtoCls.getDeclaredFields();
        Method[] dtoMethods = dtoCls.getDeclaredMethods();
        Method[] entityMethods = entityCls.getDeclaredMethods();

        Map<String, Object> updateData = new HashMap<>();

        for(Field field : dtoFields){
            field.setAccessible(true);
            String name = field.getName();
            for(Method method : dtoMethods){
                method.setAccessible(true);
                if(name.toUpperCase(Locale.ROOT).equals(method.getName().replace("get", "").toUpperCase(Locale.ROOT))){
                    updateData.put(name, method.invoke(dto));
                }
            }
        }

        updateData.forEach((k,v) -> {
            if(v != null){
                String name = k.toUpperCase(Locale.ROOT);
                for(Method method : entityMethods){
                    method.setAccessible(true);
                    if(method.getName().contains("set") && method.getName().toUpperCase(Locale.ROOT).contains(name)){
                        try {
                            method.invoke(entity, v);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        });
        return entity;
    }
}
