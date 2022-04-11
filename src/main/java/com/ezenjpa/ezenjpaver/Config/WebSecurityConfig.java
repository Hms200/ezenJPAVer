package com.ezenjpa.ezenjpaver.Config;

import com.ezenjpa.ezenjpaver.security.CustomAuthFailureHandler;
import com.ezenjpa.ezenjpaver.security.CustomFormAuthenticationProvider;
import com.ezenjpa.ezenjpaver.security.CustomLoginSuccessHandler;
import com.ezenjpa.ezenjpaver.security.JwtAuthenticationFilter;
import com.ezenjpa.ezenjpaver.security.oauth.CustomOAuth2UserService;
import com.ezenjpa.ezenjpaver.security.oauth.OauthLoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    // Jwt토큰 검증필터
    @Autowired
    JwtAuthenticationFilter jwtAuthenticationFilter;
    // 로그인 성공시 수행할 작업(토큰발급)
    @Autowired
    CustomLoginSuccessHandler loginSuccessHandler;
    // 로그인 실패시 수행할 작업(실패원인 분석)
    @Autowired
    CustomAuthFailureHandler AuthFailureHandler;
    // OAuth2.0 로그인 성공시 수행할 작업
    @Autowired
    OauthLoginSuccessHandler oauthLoginSuccessHandler;
    // id, pw check 클래스
    @Autowired
    CustomFormAuthenticationProvider formAuthenticationProvider;
    // OAuth2.0 userSerivce
    @Autowired
    CustomOAuth2UserService customOAuth2UserService;
    // fromAhuthenticationProvider 등록
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.authenticationProvider(formAuthenticationProvider);
    }

    @Override  // 보안설정
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
                .and()
                .csrf().disable()
                .httpBasic().disable()
                .authorizeHttpRequests()
                .antMatchers("/", "/main", "/aboutUs", "/siteMap", "/goodsList/**", "/oauth2/**",
                        "/login/**", "/error/**", "/resources/**", "/img/**", "/js/**", "/css/**").permitAll()
                .antMatchers("/amdin/**").hasRole("ADMIN")
                .antMatchers("/login/quit").hasRole("MEMBER")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login/login")
                .loginProcessingUrl("/login/loginAction")
                .usernameParameter("user_id")
                .passwordParameter("user_pw")
                .defaultSuccessUrl("/main")
                .successHandler(loginSuccessHandler)
                .failureHandler(AuthFailureHandler)
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/login/logoutAction"))
                .deleteCookies("Authorization", "JSESSIONID")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutSuccessUrl("/main")
                .and()
                .oauth2Login()
                .loginPage("/login/login")
                .defaultSuccessUrl("/main")
                .successHandler(oauthLoginSuccessHandler)
                .failureHandler(AuthFailureHandler)
                .userInfoEndpoint().userService(customOAuth2UserService);

        http.sessionManagement()	  // session 관리
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
        //	.maximumSessions(1)					// 최대 허용 session 1
        //	.maxSessionsPreventsLogin(true);		// 중복 로그인 허용 안함

        // custom AhuthenticationProvider 등록
        http.authenticationProvider(formAuthenticationProvider);


        // JwtAuthenticationFilter 등록
        http.addFilterAfter(jwtAuthenticationFilter,  SecurityContextHolderAwareRequestFilter.class)
                .exceptionHandling()
                .accessDeniedPage("/login/login");
    }
}
