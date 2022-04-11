package com.ezenjpa.ezenjpaver.entity;

import com.ezenjpa.ezenjpaver.DTO.NoticeDTO;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "MALL_NOTICE")
@SequenceGenerator(name = "notice_seq_generator",
                    sequenceName = "MALL_NOTICE_SEQ",
                    allocationSize = 1)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class NoticeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator = "notice_seq_generator")
    @Column(name = "NOTICE_IDX")
    private Long noticeIdx;

    @Column(name = "NOTICE_TITLE")
    private String noticeTitle;

    @Column(name = "NOTICE_CONTENTS")
    private String noticeContents;

    @Column(name = "NOTICE_SHOW", nullable = false)
    private int noticeShow;

    @Column(name = "NOTICE_DATE")
    @Temporal(TemporalType.DATE)
    private Date noticeDate;

    // noticeDTO로 변환
    public NoticeDTO convertToNoticeDTO(NoticeEntity entity){
        return NoticeDTO.builder()
                .noticeIdx(entity.getNoticeIdx())
                .noticeTitle(entity.getNoticeTitle())
                .noticeContents(entity.getNoticeContents())
                .noticeDate(entity.getNoticeDate())
                .noticeShow(entity.getNoticeShow())
                .build();
    }

}
