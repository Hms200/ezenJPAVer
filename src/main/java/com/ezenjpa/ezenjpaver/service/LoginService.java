package com.ezenjpa.ezenjpaver.service;

import com.ezenjpa.ezenjpaver.entity.UserEntity;
import com.ezenjpa.ezenjpaver.repository.UserRepository;
import com.ezenjpa.ezenjpaver.security.TokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
@Slf4j
public class LoginService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    TokenProvider tokenProvider;
    @Autowired
    HttpSession session;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    @Autowired
    DisposablePasswordGenerator passwordGenerator;

    public String findId(String user_name, String user_email){

        Optional<UserEntity> userId = Optional.ofNullable(userRepository.findByUserNameAndUserEmail(user_name, user_email));
        return userId.map(userEntity -> "<script>alert('고객님의 ID는 " + userEntity.getUserId() + " 입니다.');location.href='login';</script>")
                .orElse("<script>alert('일치하는 정보를 찾을 수 없습니다. 다시 확인해 주세요');</script>");
    }

}
