package com.ezenjpa.ezenjpaver.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@NoArgsConstructor
@Getter
@ToString
public class QuestionDTO {

    private Long questionIdx;
    private Long userIdx;
    private Long goodsIdx;
    private String questionTitle;
    private String questionContents;
    private Date questionDate;
    private int questionIsreplied;
    private String questionReply;
    private Date questionReplyDate;

    @Builder
    public QuestionDTO(Long questionIdx, Long userIdx, Long goodsIdx, String questionTitle, String questionContents, Date questionDate, int questionIsreplied, String questionReply, Date questionReplyDate) {
        this.questionIdx = questionIdx;
        this.userIdx = userIdx;
        this.goodsIdx = goodsIdx;
        this.questionTitle = questionTitle;
        this.questionContents = questionContents;
        this.questionDate = questionDate;
        this.questionIsreplied = questionIsreplied;
        this.questionReply = questionReply;
        this.questionReplyDate = questionReplyDate;
    }
}
