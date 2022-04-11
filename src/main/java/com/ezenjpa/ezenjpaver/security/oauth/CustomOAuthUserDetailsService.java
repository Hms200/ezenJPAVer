package com.ezenjpa.ezenjpaver.security.oauth;

import com.ezenjpa.ezenjpaver.entity.UserEntity;
import com.ezenjpa.ezenjpaver.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
public class CustomOAuthUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Id를 검색합니다.");
        UserEntity user = userRepository.findByUserId(username);
        if(user == null){
            log.info("해당 id를 찾을 수 없습니다.");
            throw new UsernameNotFoundException("등록되지 않은 사용자");
        }
        return UserPrincipal.create(user);
    }
}
