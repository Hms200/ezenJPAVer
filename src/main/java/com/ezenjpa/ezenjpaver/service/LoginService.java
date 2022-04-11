package com.ezenjpa.ezenjpaver.service;

import com.ezenjpa.ezenjpaver.DTO.UserDTO;
import com.ezenjpa.ezenjpaver.entity.UserEntity;
import com.ezenjpa.ezenjpaver.enums.UserProvider;
import com.ezenjpa.ezenjpaver.repository.UserRepository;
import com.ezenjpa.ezenjpaver.security.TokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.swing.text.html.Option;
import java.time.Instant;
import java.util.Date;
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

    // id 찾기
    public String findId(String user_name, String user_email){

        Optional<UserEntity> userId = Optional.ofNullable(userRepository.findByUserNameAndUserEmail(user_name, user_email));

        return userId.map(userEntity -> "<script>alert('고객님의 ID는 " + userEntity.getUserId() + " 입니다.');location.href='login';</script>")
                .orElse("<script>alert('일치하는 정보를 찾을 수 없습니다. 다시 확인해 주세요');</script>");
    }

    // 임시비밀번호 발급
    public String findPW(String user_id, String user_name, String user_email){
        Optional<UserEntity> isUserExist = Optional.ofNullable(userRepository.findByUserIdAndUserNameAndUserEmail(user_id, user_name, user_email));
        if(isUserExist.isPresent()){
            UserEntity user = isUserExist.get();
            log.info("임시비밀번호를 발급합니다.");
            String newPW = passwordGenerator.generateDisposablePassword();
            log.info("발급된 임시 비밀번호 = {} 비밀번호를 변경합니다.", newPW);
            user.setUserPw(passwordEncoder.encode(newPW));
            userRepository.save(user);
            return "<script>alert('임시비밀번호는 "+ newPW + "입니다. 로그인하시어 비밀번호를 변경해 주세요');location.href='login';</script>";
        }
        return "<script>alert('일치하는 정보가 없습니다. 다시 확인해주세요');</script>";
    }

    // id중복확인
    public int checkDuplicationOfId(String user_id){
        Optional<UserEntity> user = Optional.ofNullable(userRepository.findByUserId(user_id));
        if(user.isPresent()){
            return 1;
        }else {
            return 0;
        }
    }

    // 회원가입
    public String join(UserDTO userDTO){
        log.info("입력받은 암호를 암호화합니다.");
        String encodedPw = passwordEncoder.encode(userDTO.getUserPw());
        log.info("암호화 완료. 신규 회원 정보를 DB에 등록합니다.");
        UserEntity newUser = UserEntity.builder()
                .userId(userDTO.getUserId())
                .userPw(encodedPw)
                .userAddress(userDTO.getUserAddress())
                .userEmail(userDTO.getUserEmail())
                .userPhone(userDTO.getUserPhone())
                .userName(userDTO.getUserName())
                .userProvider(UserProvider.LOCAL)
                .joinDate(Date.from(Instant.now()))
                .build();
        userRepository.save(newUser);

        return "<script>alert('등록되었습니다.');location.href='login';</script>";
    }

    // 회원탈퇴
    public void quit(String user_idx){
        userRepository.deleteById(Long.valueOf((user_idx)));
    }

}
