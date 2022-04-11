package com.ezenjpa.ezenjpaver.controller;

import com.ezenjpa.ezenjpaver.DTO.NoticeDTO;
import com.ezenjpa.ezenjpaver.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class NoticeController {

    @Autowired
    NoticeService noticeService;

    @GetMapping("notice")
    public String notice(Pageable pageable, Model model){
        model = noticeService.noticePaging(pageable, model);
        return "notice";
    }

    // 공지사항 내용 변경
    @PostMapping("noticeUpdateAction")
    @ResponseBody
    public String noticeUpdate(@ModelAttribute NoticeDTO notice){
        return noticeService.updateNotice(notice);
    }

    // 공지사항 작성
    @PostMapping("noticeWriteAction")
    @ResponseBody
    public String writeNotice(@ModelAttribute NoticeDTO notice){
        return noticeService.writeNotice(notice);
    }

    // 공지사항 삭제
    @PostMapping("noticeDeleteAction")
    @ResponseBody
    public String deleteNotice(@RequestParam Long noticeIdx){
        return noticeService.deleteNotice(noticeIdx);
    }

}
