package com.ezenjpa.ezenjpaver.repository;

import com.ezenjpa.ezenjpaver.DTO.NoticeDTO;
import com.ezenjpa.ezenjpaver.entity.NoticeEntity;
import org.hibernate.annotations.Comment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.swing.text.html.parser.Entity;
import javax.transaction.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class NoticeRepositoryTest {

    @Autowired
    NoticeRepository noticeRepository;

    @Test
    @Comment("공지사항 리스트 페이징 테스트")
    void getAllNoticeEntities() {
        PageRequest pageRequest = PageRequest.of(0, 5);
        Page<NoticeEntity> noticeEntities = noticeRepository.findAll(pageRequest);
        noticeEntities.forEach(entity -> {
            NoticeDTO notice = entity.convertToNoticeDTO(entity);
            System.out.println(notice.toString());
        });
    }

    @Test
    @Comment("공지사항 업데이트 테스트")
    void modifyNotice(){
        NoticeDTO notice = NoticeDTO.builder()
                .noticeIdx(41L)
                .noticeTitle("3월 배송 휴무 안내")
                .noticeContents("\n" +
                        "\n" +
                        "3월 공휴일로 인한 배송 휴무일을 안내드립니다.\n" +
                        "\n" +
                        "택배사 휴무일 \n" +
                        "3월 1일 (화) 삼일절\n" +
                        "3월 9일 (수) 대통령 선거일\n" +
                        "해당 택배사 \n" +
                        "CJ대한통운, 롯데택배, 한진택배, 로젠택배, 우체국택배\n" +
                        "\n" +
                        "그럼 소향행과 함께, 향기롭고 편안한 3월 보내시길 바랍니다. \n" +
                        "\n" +
                        "감사합니다.\n")
                .noticeShow(1)
                .noticeDate(Date.from(Instant.now()))
                .build();
        NoticeEntity modifying = NoticeEntity.builder()
                .noticeIdx(notice.getNoticeIdx())
                .noticeTitle(notice.getNoticeTitle())
                .noticeContents(notice.getNoticeContents())
                .noticeShow(notice.getNoticeShow())
                .noticeDate(notice.getNoticeDate())
                .build();
        noticeRepository.save(modifying);
        Optional<NoticeEntity> test = noticeRepository.findById(41L);
        NoticeEntity testEntity = test.get();
        NoticeDTO check = testEntity.convertToNoticeDTO(testEntity);
        System.out.println(check.toString());
    }

    @Test
    @Comment("새로운 공지사항 등록 태스트")
    void insertNotice(){
        NoticeDTO notice = NoticeDTO.builder()
                .noticeTitle("4월 배송 휴무 안내")
                .noticeContents("\n" +
                        "\n" +
                        "4월 공휴일로 인한 배송 휴무일을 안내드립니다.\n" +
                        "\n" +
                        "택배사 휴무일 없습니다.\n" +
                        "해당 택배사 \n" +
                        "CJ대한통운, 롯데택배, 한진택배, 로젠택배, 우체국택배\n" +
                        "\n" +
                        "그럼 소향행과 함께, 향기롭고 편안한 4월 보내시길 바랍니다. \n" +
                        "\n" +
                        "감사합니다.\n")
                .noticeShow(0)
                .noticeDate(Date.from(Instant.now()))
                .build();
        NoticeEntity newNotice = NoticeEntity.builder()
                .noticeTitle(notice.getNoticeTitle())
                .noticeContents(notice.getNoticeContents())
                .noticeShow(notice.getNoticeShow())
                .noticeDate(notice.getNoticeDate())
                .build();

        noticeRepository.save(newNotice);

    }
}