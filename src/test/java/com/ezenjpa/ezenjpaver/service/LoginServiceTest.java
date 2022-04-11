package com.ezenjpa.ezenjpaver.service;

import com.ezenjpa.ezenjpaver.entity.UserEntity;
import com.ezenjpa.ezenjpaver.repository.UserRepository;
import org.hibernate.annotations.Comment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class LoginServiceTest {
    Logger log = LoggerFactory.getLogger(LoginServiceTest.class);


    @Autowired
    UserRepository userRepository;
    @Autowired
    DisposablePasswordGenerator passwordGenerator;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Test
    @Comment("아이디 찾기 테스트")
    void findId() {
        String user_name = "test";
        String user_email = "test@test";
        String result;

        Optional<UserEntity> userId = Optional.ofNullable(userRepository.findByUserNameAndUserEmail(user_name, user_email));

        result = userId.map(UserEntity::getUserId)
                .orElse("not found");

        Assertions.assertEquals("authtest", result);
    }

    @Test
    @Comment("비밀번호 찾기 테스트")
    void findPw() {
        String user_id = "authtest";
        String user_name = "test";
        String user_email = "test@test";
        String result;

        Optional<UserEntity> isUserExist = Optional.ofNullable(userRepository.findByUserIdAndUserNameAndUserEmail(user_id, user_name, user_email));
        if (isUserExist.isPresent()) {
            UserEntity user = isUserExist.get();
            log.info("임시비밀번호를 발급합니다.");
            String newPW = passwordGenerator.generateDisposablePassword();
            String encoded = passwordEncoder.encode(newPW);
            log.info("발급된 임시 비밀번호 = {} 비밀번호를 변경합니다.", newPW);
            user.setUserPw(encoded);
            user = userRepository.save(user);
            result = user.getUserPw();
            Assertions.assertEquals(encoded,result);
        }
    }

    @Test
    @Comment("회원탈퇴 테스트")
    void quit(){
        String user_idx = "222";
        userRepository.deleteById(Long.valueOf(user_idx));
        Optional<UserEntity> user = userRepository.findById(Long.valueOf(user_idx));
        Assertions.assertTrue(user.isEmpty());
    }


}
