package com.ezenjpa.ezenjpaver.controller;

import com.ezenjpa.ezenjpaver.DTO.UserDTO;
import com.ezenjpa.ezenjpaver.service.MyPageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.InvocationTargetException;


@Slf4j
@Controller
@RequestMapping("myPage")
public class MyPageController {

    @Autowired
    MyPageService myPageService;

    @RequestMapping("myPage")
    public String myPage(){
        return "myPage/myPage";
    }

    @RequestMapping("memberInfo")
    public String memberInfo(Model model){
        model = myPageService.memberInfo(model);
        return "myPage/memberInfo";
    }

    @PostMapping("userUpdateAction")
    @ResponseBody
    public String updateUserInfo(UserDTO user) throws InvocationTargetException, IllegalAccessException {
        log.info("다음 정보로 업데이트 합니다. {}",user.toString());
        myPageService.updateMemberInfo(user);
        return "<script>alert('회원정보가 변경되었습니다.');location.href='/myPage/myPage';</script>";
    }

    @RequestMapping("purchaseList")
    public String purchaseList(Model model, @RequestParam(name = "cat", required = false) Integer cat){
        return "";
    }

}
