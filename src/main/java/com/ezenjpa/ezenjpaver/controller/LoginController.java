package com.ezenjpa.ezenjpaver.controller;

import com.ezenjpa.ezenjpaver.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("login")
public class LoginController {

    @Autowired
    LoginService loginService;
    @Autowired
    HttpSession session;

    @RequestMapping("login")
    public String login(){
        return "login/login";
    }

    @RequestMapping("join")
    public String join(Model model) {
        Optional<String> userIdx = Optional.ofNullable((String)(session.getAttribute("userIdx")));
        if(userIdx.isPresent()){
            String errorMessage = "이미 가입된 회원은 회원가입을 이용하실 수 없습니다.";
            log.info("이미 가입된 회원의 회원가입 시도 차단.");
            log.info("user idx : {}",userIdx.get());
            model.addAttribute("errorMessage", errorMessage);
            return "login/login";
        }
        return "login/join";
    }

    @RequestMapping("quit")
    public String quit(Model model){
        Optional<String> userIdx = Optional.ofNullable((String)(session.getAttribute("userIdx")));
        if(userIdx.isEmpty()){
            String errorMessage = "로그인 하셔야 탈퇴기능을 이용하실 수 있습니다.";
            log.info("로그인 하지 않은 사용자의 탈퇴시도 차단");
            model.addAttribute("errorMessage", errorMessage);
            return "login/login";
        }
        return "login/quit";
    }

}
