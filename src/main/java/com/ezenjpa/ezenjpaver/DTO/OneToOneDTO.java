package com.ezenjpa.ezenjpaver.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@NoArgsConstructor
@Getter
@ToString
public class OneToOneDTO {

    private Long onetooneIdx;
    private Long userIdx;
    private String onetooneCat;
    private String onetooneTitle;
    private String onetooneContents;
    private Date onetooneDate;
    private int onetooneIsreplied;
    private String onetooneReply;
    private Date onetooneReplyDate;

    @Builder
    public OneToOneDTO(Long onetooneIdx, Long userIdx, String onetooneCat, String onetooneTitle, String onetooneContents, Date onetooneDate, int onetooneIsreplied, String onetooneReply, Date onetooneReplyDate) {
        this.onetooneIdx = onetooneIdx;
        this.userIdx = userIdx;
        this.onetooneCat = onetooneCat;
        this.onetooneTitle = onetooneTitle;
        this.onetooneContents = onetooneContents;
        this.onetooneDate = onetooneDate;
        this.onetooneIsreplied = onetooneIsreplied;
        this.onetooneReply = onetooneReply;
        this.onetooneReplyDate = onetooneReplyDate;
    }
}
