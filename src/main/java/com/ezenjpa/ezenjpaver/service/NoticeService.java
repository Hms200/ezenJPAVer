package com.ezenjpa.ezenjpaver.service;

import com.ezenjpa.ezenjpaver.DTO.NoticeDTO;
import com.ezenjpa.ezenjpaver.entity.NoticeEntity;
import com.ezenjpa.ezenjpaver.repository.NoticeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@Slf4j
public class NoticeService {

    @Autowired
    NoticeRepository noticeRepository;
    @Autowired
    PagenationService pagenation;

    public Model noticePaging(Pageable pageable, Model model){
        log.info("{}번째 페이지 공지사항리스트 가져옵니다.", pageable.getPageNumber()+1);
        Page<NoticeEntity> noticeEntityPage = noticeRepository.findAll(pageable);
        List<NoticeDTO> noticeDTO = new ArrayList<>();
         noticeEntityPage.getContent().forEach(page -> {
            NoticeDTO notice = page.convertToNoticeDTO(page);
            noticeDTO.add(notice);
        });
        model.addAttribute("noticeList",noticeDTO);
        pagenation = pagenation.pagenationInfo(noticeEntityPage, 5);
        model.addAttribute("pages", pagenation);
        return model;
    }

    public String updateNotice(NoticeDTO notice){
        log.info("Idx {} 공지사항 변경사항을 업데이트 합니다.",notice.getNoticeIdx());
        NoticeEntity modifying = NoticeEntity.builder()
                .noticeIdx(notice.getNoticeIdx())
                .noticeTitle(notice.getNoticeTitle())
                .noticeContents(notice.getNoticeContents())
                .noticeDate(notice.getNoticeDate())
                .noticeShow(notice.getNoticeShow())
                .build();
        noticeRepository.save(modifying);
        return "<Script>alert('수정되었습니다.'); location.href='notice'</script>";
    }

    public String writeNotice(NoticeDTO notice){
        log.info("새로운 공지사항을 등록합니다.");
        NoticeEntity newNotice = NoticeEntity.builder()
                .noticeTitle(notice.getNoticeTitle())
                .noticeContents(notice.getNoticeContents())
                .noticeDate(Date.from(Instant.now()))
                .noticeShow(notice.getNoticeShow())
                .build();
        noticeRepository.save(newNotice);
        return "<Script>alert('등록되었습니다.'); location.href='notice'</script>";

    }

    public String deleteNotice(Long noticeIdx){
        log.info("Idx : {} 공지사항을 삭제합니다.", noticeIdx);
        noticeRepository.deleteById(noticeIdx);
        return "<Script>alert('삭제되었습니다.'); location.href='notice'</script>";
    }

}
