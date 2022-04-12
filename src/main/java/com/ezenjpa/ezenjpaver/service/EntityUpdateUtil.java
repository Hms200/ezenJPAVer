package com.ezenjpa.ezenjpaver.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/*
*  DTO에 담긴 정보로 Entity의 내용을 update 해줌.
*
*  연관관계가 mapping 된 field의 entity정보를 가져오기 위해서는 해당 entity를 가져올 repository 에
*  getBy**Idx 가 만들어져 있어야 함. -> 새로운 entity 내용으로 db insert 가능.
*
* */
@Component
@Slf4j
public class EntityUpdateUtil {

    @Autowired
    ApplicationContext context;

    public Object entityUpdateUtil(Object dto, Object entity) throws InvocationTargetException, IllegalAccessException {
        // Reflection 으로 DTO filed, getter 가져와서 HashMap에 mapping
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
                if(name.toUpperCase().equals(method.getName().replace("get", "").toUpperCase())){
                    updateData.put(name, method.invoke(dto));
                }
            }
        }
        // Reflection 으로 entity setter 가져와서 update 할 field 만 가려내 setter 실행
        updateData.forEach((k,v) -> {
            if(v != null){
                String name = k.toUpperCase();
                for(Method method : entityMethods){
                    method.setAccessible(true);
                    if(method.getName().contains("set") && method.getName().toUpperCase().contains(name)){
                        try {
                            method.invoke(entity, v);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
        // update 할 field가 Entity형 인 경우 Repository 호출하여 join된 Entity 가져와서 setter로 update 할 entity에 넣어줌
                    }if(method.getName().contains("set") && method.getName().contains("Entity") && method.getName().toUpperCase(Locale.ROOT).contains(name.replace("IDX",""))){
                        String repo = k.replace("Idx", "");
                        repo = repo.substring(0,1).toUpperCase() + repo.substring(1);
                        String methodname = k.substring(0,1).toUpperCase() + k.substring(1);
                        try {
                            Class<?> cls = Class.forName("com.ezenjpa.ezenjpaver.repository." + repo + "Repository");
                            Class<?> entitycls = Class.forName("com.ezenjpa.ezenjpaver.entity."+ repo + "Entity");
                            Object targetentity = context.getBean(cls).getClass().getDeclaredMethod("getBy"+methodname, Long.class).invoke(context.getBean(cls), v);
                            method.invoke(entity,  entitycls.cast(targetentity));
                        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        return entity;
    }

}
