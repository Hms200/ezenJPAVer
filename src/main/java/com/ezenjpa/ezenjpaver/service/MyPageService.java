package com.ezenjpa.ezenjpaver.service;

import com.ezenjpa.ezenjpaver.DTO.UserDTO;
import com.ezenjpa.ezenjpaver.entity.UserEntity;
import com.ezenjpa.ezenjpaver.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class MyPageService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    HttpSession session;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    @Autowired
    EntityUpdateUtil updateUtil;

    //member info
    public Model memberInfo(Model model){
        log.info("로그인한 사용자 정보를 가져옵니다.");
        String userIdx = (String) session.getAttribute("userIdx");
        UserEntity userEntity = userRepository.getById(Long.valueOf(userIdx));
        UserDTO userDTO = userEntity.convertToUserDTO(userEntity);
        model.addAttribute("user", userDTO);
        return model;
    }

    //update member info
    public void updateMemberInfo(UserDTO user) throws InvocationTargetException, IllegalAccessException {
        Long userIdx = Long.valueOf((String) session.getAttribute("userIdx"));
        String encodedPw = passwordEncoder.encode(user.getUserPw());
        UserEntity userEntity = userRepository.getById(userIdx);
        userEntity = (UserEntity) updateUtil.entityUpdateUtil(user, userEntity);
        userEntity.setUserPw(encodedPw);
        userRepository.save(userEntity);
        log.info("user 정보 업데이트 완료.");
    }

}
