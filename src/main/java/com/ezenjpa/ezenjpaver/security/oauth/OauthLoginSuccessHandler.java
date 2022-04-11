package com.ezenjpa.ezenjpaver.security.oauth;

import com.ezenjpa.ezenjpaver.entity.UserEntity;
import com.ezenjpa.ezenjpaver.repository.UserRepository;
import com.ezenjpa.ezenjpaver.security.TokenProvider;
import com.ezenjpa.ezenjpaver.service.MainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OauthLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    TokenProvider tokenProvider;
    @Autowired
    HttpSession session;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MainService mainService;
    @Bean
    public SecurityContextHolder OauthSecurityContextHolder(){
        return new SecurityContextHolder();
    }

    @SuppressWarnings("static-access")
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("로그인 인증 성공. 인증토큰 발급 시작");
        UserPrincipal userPrincipal = (UserPrincipal) OauthSecurityContextHolder().getContext().getAuthentication().getPrincipal();

        String userId = userPrincipal.getUserId();
        UserEntity user = userRepository.findByUserId(userId);
        String userIdx = String.valueOf(user.getUserIdx());
        String userName = user.getUserName();
        log.info("user idx : {}", userIdx);

        final String token = tokenProvider.creat(userIdx);
        log.info("인증토큰 발급 완료");

        Cookie cookie = new Cookie("Authorization", "Bearer"+token);
        // 만료기간은 1시간
        cookie.setMaxAge(60*60);
        // 옵션 설정
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        // add cookie to response
        response.addCookie(cookie);
        log.info("JWT Token을 cookie에 저장합니다.");

        // user id 대신 user name 저장
        session.setAttribute("userId", userName);
        session.setAttribute("userIdx", userIdx);
        log.info("소셜로그인. id 대신 name을 id로 세션에 저장합니다. user ID : {}, user Idx : {}", userName, userIdx);

        mainService.setCartBedgeNumber();

        log.info("myPage로 redirect 합니다.");
        response.sendRedirect("../myPage/myPage");

    }
}
