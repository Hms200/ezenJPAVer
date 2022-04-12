package com.ezenjpa.ezenjpaver.service;

import com.ezenjpa.ezenjpaver.DTO.ReviewDTO;
import com.ezenjpa.ezenjpaver.DTO.UserDTO;
import com.ezenjpa.ezenjpaver.VO.PurchaseListVO;
import com.ezenjpa.ezenjpaver.entity.ReviewEntity;
import com.ezenjpa.ezenjpaver.entity.UserEntity;
import com.ezenjpa.ezenjpaver.repository.CartRepository;
import com.ezenjpa.ezenjpaver.repository.ReviewRepository;
import com.ezenjpa.ezenjpaver.repository.UserRepository;
import org.hibernate.annotations.Comment;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.transaction.Transactional;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

@SpringBootTest
@Transactional
class MyPageServiceTest {
    Logger log = LoggerFactory.getLogger(MyPageServiceTest.class);

    @Autowired
    UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    ApplicationContext context;


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
        ReviewDTO review = ReviewDTO.builder()
                .reviewStar(5)
                .reviewContents("ss")
                .userIdx(222L)
                .goodsIdx(95L)
                .build();
        // Reflection 으로 DTO filed, getter 가져와서 HashMap에 mapping
        Class<?> cls = review.getClass();
        Field[] fields = cls.getDeclaredFields();
        Map<String, Object> mapping = new HashMap<>();
        Method[] methods = cls.getDeclaredMethods();
        for(Field field : fields){
            field.setAccessible(true);
            String name = field.getName();

            for(Method method : methods){
                method.setAccessible(true);
                if(name.toUpperCase(Locale.ROOT).equals(method.getName().replace("get", "").toUpperCase(Locale.ROOT))){
                    mapping.put(name, method.invoke(review));
                }
            }
        }

        ReviewEntity reviewEntity = reviewRepository.getById(108L);
        System.out.println(reviewEntity.toString());
        // Reflection 으로 entity setter 가져와서 update 할 field 만 가려내 setter 실행
        Class<?> entityCls = reviewEntity.getClass();
        Method[] setters = entityCls.getDeclaredMethods();


        mapping.forEach((k,v) -> {
            if(v != null){
                String name = k.toUpperCase(Locale.ROOT);
                for(Method setter : setters){
                    setter.setAccessible(true);
                    if(name.equals(setter.getName().replace("set", "").toUpperCase(Locale.ROOT))){
                        try {
                            setter.invoke(reviewEntity, v);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }if(setter.getName().contains("set") && setter.getName().contains("Entity") && setter.getName().toUpperCase(Locale.ROOT).contains(name.replace("IDX",""))){
                        String repo = k.replace("Idx", "");
                        repo = repo.substring(0,1).toUpperCase() + repo.substring(1);
                        String methodname = k.substring(0,1).toUpperCase() + k.substring(1);
                        try {
                            Class<?> clss = Class.forName("com.ezenjpa.ezenjpaver.repository." + repo + "Repository");
                            System.out.println(clss.getName());
                            Class<?> entitycls = Class.forName("com.ezenjpa.ezenjpaver.entity."+ repo + "Entity");
                            Object targetentity = context.getBean(clss).getClass().getDeclaredMethod("getBy"+methodname, Long.class).invoke(context.getBean(clss), v);
                            setter.invoke(reviewEntity,  entitycls.cast(targetentity));
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        System.out.println(reviewEntity);
    }

    @Test
    @Comment("구매목록 작성")
    void purchaseList(){
        Long userIdx = 222L;
        List<PurchaseListVO> list = cartRepository.makingPurchaseListForMyPage(userIdx);
        System.out.println(list.toString());
    }

    @Test
    @Comment("java reflection test")
    void reflection() {

        Long userIdx = 2L;
        UserEntity userEntity = new UserEntity();


        try {
            System.out.println(UserRepository.class.getDeclaredMethod("getByUserIdx", Long.class).getName());
            userEntity = (UserEntity) userRepository.getClass().getDeclaredMethod("getByUserIdx", Long.class).invoke(userRepository, userIdx);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {

        }
        System.out.println(userEntity);
    }
}