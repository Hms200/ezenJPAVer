package com.ezenjpa.ezenjpaver.security;

import com.ezenjpa.ezenjpaver.enums.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = parseBearerToken(request);
            // 토큰 검사하기
            if(token != null && !token.equalsIgnoreCase("null")) {
                // user_idx 가져오고 위조된경우 예외처리함.
                String userIdx = tokenProvider.validateAndGetUserIdx(token);
                // 사용자 권한 설정. 관리자의 user_idx = 2임.
                Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
                if(userIdx.equals("2")) {
                    grantedAuthorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
                }else {
                    grantedAuthorities.add(new SimpleGrantedAuthority(Role.MEMBER.getValue()));
                }
                // SecurityContextHolder에 등록
                AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken
                        (userIdx, null, grantedAuthorities);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                securityContext.setAuthentication(authentication);
                SecurityContextHolder.setContext(securityContext);
            }
        } catch (Exception e) {
            log.error("jwt 인증 실패");
        }

        filterChain.doFilter(request, response);

    }

    private String parseBearerToken(HttpServletRequest request) {
        // cookie에서 토큰정보를 읽어 파싱해 토큰 리턴
        Cookie[] cookies = request.getCookies();
        String token = "";
        for(Cookie c : cookies) {
            if(c.getName().equals("Authorization")) {
                token = c.getValue();
            }
        }
        if(StringUtils.hasText(token) && token.startsWith("Bearer")) {
            return token.substring(6);
        }
        return null;

    }
}
