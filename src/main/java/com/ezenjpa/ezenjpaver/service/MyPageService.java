package com.ezenjpa.ezenjpaver.service;

import com.ezenjpa.ezenjpaver.DTO.UserDTO;
import com.ezenjpa.ezenjpaver.entity.UserEntity;
import com.ezenjpa.ezenjpaver.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class MyPageService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    HttpSession session;

    //member info
    public Model memberInfo(Model model){
        log.info("로그인한 사용자 정보를 가져옵니다.");
        String userIdx = (String) session.getAttribute("userIdx");
        UserEntity userEntity = userRepository.getById(Long.valueOf(userIdx));
        UserDTO userDTO = userEntity.convertToUserDTO(userEntity);
        model.addAttribute("user", userDTO);
        return model;
    }

}
