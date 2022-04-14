package com.ezenjpa.ezenjpaver.controller;

import com.ezenjpa.ezenjpaver.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("admin")
@Slf4j
public class AdminController {

    @Autowired
    AdminService adminService;

    @RequestMapping("")
    public String adminRoot(){
        return "admin/main";
    }

    @RequestMapping("main")
    public String main(){
        return "admin/main";
    }

    @RequestMapping("memberList")
    public String memberList(Model model){
        model = adminService.memberListForAdmin(model);
        return "admin/memberList";
    }
    // memberList 상단 필터
    @GetMapping("userSearchAction")
    public String memberListFilter(@RequestParam(name = "cat")String cat,
                                   @RequestParam(name = "searchText",required = false)String searchText,
                                   Model model) {
        model = adminService.memberListForAdminByFilter(cat, searchText, model);
        return "admin/memberList";
    }
    // 상세조회
    @GetMapping("memberListpopup")
    public String memberListPopup(@RequestParam("user_idx")Long userIdx, Model model){
        model = adminService.userInfo(userIdx, model);
        return "admin/memberListpopup";
    }
}
