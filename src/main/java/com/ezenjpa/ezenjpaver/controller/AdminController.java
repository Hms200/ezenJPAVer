package com.ezenjpa.ezenjpaver.controller;

import com.ezenjpa.ezenjpaver.DTO.OneToOneDTO;
import com.ezenjpa.ezenjpaver.DTO.QuestionDTO;
import com.ezenjpa.ezenjpaver.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import oracle.ucp.proxy.annotation.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("qnaList")
    public String qnaList(@RequestParam("Qna")String cat,Pageable pageable, Model model){
        if(cat.equals("Qna")){
            model = adminService.getQuestionList(pageable, model);
        }else{
            model = adminService.getOneToOneList(pageable, model);
        }
        return "admin/qnaList";
    }

    // qna 답글달기
    @PostMapping("registerQuestionReplyAction")
    @ResponseBody
    public String registerQnaReply(@RequestBody QuestionDTO question){
        adminService.registerQnaReply(question);
        return "등록되었습니다.";
    }
    // 1:1질문 답글달기
    @PostMapping("registerOneToOneReplyAction")
    @ResponseBody
    public String registerOneToOneReply(@RequestBody OneToOneDTO oneToOne){
        adminService.registerOneToOneReply(oneToOne);
        return "등록되었습니다.";
    }


}
