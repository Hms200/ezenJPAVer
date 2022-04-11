package com.ezenjpa.ezenjpaver.repository;

import com.ezenjpa.ezenjpaver.entity.NoticeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<NoticeEntity, Long> {

    // main 용 중요공지사항 리스트
    List<NoticeEntity> getNoticeEntitiesByNoticeShow(int noticeShow);
    // 전체 공지사항 페이징
    Page<NoticeEntity> findAll(Pageable pageable);

}