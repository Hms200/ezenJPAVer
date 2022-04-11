package com.ezenjpa.ezenjpaver.service;

import com.ezenjpa.ezenjpaver.entity.UserEntity;
import com.ezenjpa.ezenjpaver.repository.UserRepository;
import org.hibernate.annotations.Comment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class LoginServiceTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @Comment("아이디 찾기 테스트")
    void findId() {
        String user_name = "test";
        String user_email = "test@test";
        String result;

        Optional<UserEntity> userId = Optional.ofNullable(userRepository.findByUserNameAndUserEmail(user_name, user_email));

        result = userId.map(userEntity ->  userEntity.getUserId())
                .orElse("not found");

        Assertions.assertEquals("authtest",result);
    }
}