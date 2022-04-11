package com.ezenjpa.ezenjpaver.security;

import com.ezenjpa.ezenjpaver.entity.UserEntity;
import com.ezenjpa.ezenjpaver.enums.Role;
import com.ezenjpa.ezenjpaver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUserId(userId);
        if(user == null){
            log.info("해당 ID를 찾을 수 없습니다.");
            throw new UsernameNotFoundException(userId);
        }
        log.info("로그인 인증을 시작합니다.");
        log.info("로그인 시도 ID : {}", user.getUserId());

        CustomUserDetails customUserDetails = new CustomUserDetails();
        customUserDetails.setUsername(user.getUserId());
        customUserDetails.setPassword(user.getUserPw());

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        if(userId.equals("admin")){
            grantedAuthorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
            log.info("사용자 권한 : ADMIN");
        }else {
            grantedAuthorities.add(new SimpleGrantedAuthority(Role.MEMBER.getValue()));
            log.info("사용자 권한 : MEMBER");
        }

        customUserDetails.setAuthorities(grantedAuthorities);
        customUserDetails.setEnabled(true);
        customUserDetails.setAccountNonExpired(true);
        customUserDetails.setAccountNonLocked(true);
        customUserDetails.setCredentialsNonExpried(true);

        return customUserDetails;
    }
}
