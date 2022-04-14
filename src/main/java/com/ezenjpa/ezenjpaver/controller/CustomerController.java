package com.ezenjpa.ezenjpaver.controller;

import com.ezenjpa.ezenjpaver.DTO.FaqDTO;
import com.ezenjpa.ezenjpaver.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;

@Controller
@RequestMapping("customer")
@Slf4j
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @RequestMapping("")
    public String customerRoot(){
        return "redirect:customer/faq";
    }

    @RequestMapping("faq")
    public String faq(Model model){
        model = customerService.getAllFaq(model);
        return "customer/faq";
    }
    // faq 카테고리 필터
    @GetMapping("faqCatAction")
    public String faqSortedByCat(@RequestParam("faq_cat")String cat, Model model){
        model = customerService.getFaqByCat(cat, model);
        return "customer/faq";
    }
    // faq 등록
    @PostMapping("faqWriteAction")
    @ResponseBody
    public String faqWriteAction(@ModelAttribute FaqDTO Faq) throws InvocationTargetException, IllegalAccessException {
        customerService.insertNewFaq(Faq);
        return "<script>alert('등록되었습니다.'); location.href='/customer/faq'</script>";
    }
    // faq 삭제
    @RequestMapping("faqDeleteAction")
    @ResponseBody
    public String faqDeleteAction(@RequestParam("faq_idx") Long faqIdx) {
        customerService.deleteFaq(faqIdx);
        return "<script>alert('삭제되었습니다.'); location.href='/customer/faq';</script>";
    }
}
