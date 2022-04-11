package com.ezenjpa.ezenjpaver.controller;

import com.ezenjpa.ezenjpaver.service.MyPageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;



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

}
