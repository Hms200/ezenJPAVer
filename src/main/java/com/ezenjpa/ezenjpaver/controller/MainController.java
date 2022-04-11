package com.ezenjpa.ezenjpaver.controller;

import com.ezenjpa.ezenjpaver.service.MainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class MainController {

    @Autowired
    MainService mainService;

    @RequestMapping("")
    public String root(){
        return "redirect:main";
    }

    @RequestMapping("main")
    public String main(Model model){
        // cart bedge 숫자 설정
        mainService.setCartBedgeNumber();
        // main용 중요공지사항 목록 설정
        model = mainService.setNoticeForMain(model);
        // card data
        model = mainService.setCardData(model);
        return "main";
    }

    @RequestMapping("aboutUs")
    public String aboutUs(){
        return "aboutUs";
    }

    @RequestMapping("siteMap")
    public String siteMap(){
        return "siteMap";
    }

    @GetMapping("mainsearchAction")
    public String mainsSearch(@RequestParam String searchtext, Model model){
        model = mainService.mainSearch(searchtext, model);
        return main(model);
    }

}
