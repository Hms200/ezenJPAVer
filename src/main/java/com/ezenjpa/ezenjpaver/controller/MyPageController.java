package com.ezenjpa.ezenjpaver.controller;

import com.ezenjpa.ezenjpaver.DTO.ReviewDTO;
import com.ezenjpa.ezenjpaver.DTO.UserDTO;
import com.ezenjpa.ezenjpaver.service.MyPageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
        if(cat == null){
           model = myPageService.purchaseList(model);
        }else {
            model = myPageService.purchaseListFilter(model, cat);
        }
        return "myPage/purchaseList";
    }

    @GetMapping("changestatement")
    @ResponseBody
    public String changeStatement(@RequestParam("userIdx") Long userIdx,
                                  @RequestParam("ask") String ask){
        log.info("환불,교환,취소 신청 접수");
        myPageService.changeStatement(userIdx, ask);
        return "<script>alert('신청이 완료되었습니다.'); location.href='/myPage/myPage'; </script>";
    }

    @GetMapping("reviewpopup")
    public String reviewPopup(@RequestParam String user_idx,
                              @RequestParam String goods_idx, Model model){
        model.addAttribute("user_idx",user_idx);
        model.addAttribute("goods_idx", goods_idx);
        return "myPage/reviewpopup";
    }

    @PostMapping("reviewWriteAction")
    @ResponseBody
    public Long writeReview(@RequestBody ReviewDTO review) throws InvocationTargetException, IllegalAccessException {
        log.info("다음 내용으로 리뷰를 작성합니다. {}",review.toString());
        return myPageService.insertReview(review);
    }

    @PostMapping("uploadReviewImgAction")
    @ResponseBody
    public String uploadReviewImg(@RequestParam("reviewFile") MultipartFile multipartFile,
                                  @RequestParam("reviewIdx") Long reviewIdx) throws Exception {
        log.info("리뷰 이미지 저장을 시도합니다.");
        myPageService.uploadeImg(multipartFile, reviewIdx);
        return "<script>alert('리뷰등록 성공'); location.href='../myPage/purchaseList'; </script>";
    }

}
