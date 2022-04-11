package com.ezenjpa.ezenjpaver.service;

import com.ezenjpa.ezenjpaver.DTO.UserDTO;
import com.ezenjpa.ezenjpaver.entity.UserEntity;
import com.ezenjpa.ezenjpaver.repository.UserRepository;
import org.hibernate.annotations.Comment;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MyPageServiceTest {
    Logger log = LoggerFactory.getLogger(MyPageServiceTest.class);

    @Autowired
    UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;


    @Test
    void updateMemberInfo() {
        UserDTO user = UserDTO.builder()
                .userPw("1234")
                .userName("가나다")
                .userEmail("test@test")
                .userAddress("서울시")
                .userPhone("000-0000-0000")
                .build();
        UserEntity userEntity = userRepository.getById(222L);
       userEntity.setUserPw(passwordEncoder.encode(user.getUserPw()));
       userEntity.setUserName(user.getUserName());
       userEntity.setUserEmail(user.getUserEmail());
       userEntity.setUserAddress(user.getUserAddress());
       userEntity.setUserPhone(user.getUserPhone());



        userEntity = userRepository.save(userEntity);

        log.info("{}", userEntity.toString());
    }

    @Test
    void info(){
        UserDTO user = UserDTO.builder()
                .userPw("1234")
                .userName("가나다")
                .userEmail("test@test")
                .userAddress("서울시")
                .userPhone("000-0000-0000")
                .build();
        Class<?> userClass = user.getClass();

        Field[] fields = userClass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            System.out.println(field.getName());
        }
        Method[] getters = userClass.getDeclaredMethods();
        for (Method method : getters) {
            method.setAccessible(true);
            System.out.println(method.getName());
        }
    }

    @Test
    @Comment("reflection 이용해서 update util 만들기")
    void updater() throws InvocationTargetException, IllegalAccessException {
        UserDTO user = UserDTO.builder()  // param obj
                .userPw("1234")
                .userName("가나다")
                .userEmail("test@test")
                .userAddress("서울시")
                .userPhone("000-0000-0000")
                .build();
        // Reflection 으로 DTO filed, getter 가져와서 HashMap에 mapping
        Class<?> cls = user.getClass();
        Field[] fields = cls.getDeclaredFields();
        Map<String, Object> mapping = new HashMap<>();
        Method[] methods = cls.getDeclaredMethods();
        for(Field field : fields){
            field.setAccessible(true);
            String name = field.getName();

            for(Method method : methods){
                method.setAccessible(true);
                if(name.toUpperCase(Locale.ROOT).equals(method.getName().replace("get", "").toUpperCase(Locale.ROOT))){
                    mapping.put(name, method.invoke(user));
                }
            }
        }

        UserEntity userEntity = userRepository.getById(222L);
        System.out.println(userEntity.toString());
        // Reflection 으로 entity setter 가져와서 update 할 field 만 가려내 setter 실행
        Class<?> entityCls = userEntity.getClass();
        Method[] setters = entityCls.getDeclaredMethods();


        mapping.forEach((k,v) -> {
            if(v != null){
                String name = k.toUpperCase(Locale.ROOT);
                for(Method setter : setters){
                    setter.setAccessible(true);
                    if(name.equals(setter.getName().replace("set", "").toUpperCase(Locale.ROOT))){
                        try {
                            setter.invoke(userEntity, v);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        System.out.println(userEntity.toString());
    }
}