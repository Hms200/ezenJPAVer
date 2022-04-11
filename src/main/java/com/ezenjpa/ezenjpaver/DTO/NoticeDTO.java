package com.ezenjpa.ezenjpaver.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@NoArgsConstructor
@Getter
@ToString
public class NoticeDTO {

    private Long noticeIdx;
    private String noticeTitle;
    private String noticeContents;
    private int noticeShow;
    private Date noticeDate;

    @Builder
    public NoticeDTO(Long noticeIdx, String noticeTitle, String noticeContents, int noticeShow, Date noticeDate) {
        this.noticeIdx = noticeIdx;
        this.noticeTitle = noticeTitle;
        this.noticeContents = noticeContents;
        this.noticeShow = noticeShow;
        this.noticeDate = noticeDate;
    }
}
