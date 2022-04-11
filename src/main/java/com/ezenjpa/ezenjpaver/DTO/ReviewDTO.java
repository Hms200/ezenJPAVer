package com.ezenjpa.ezenjpaver.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@NoArgsConstructor
@Getter
@ToString
public class ReviewDTO {

    private Long reviewIdx;
    private Long userIdx;
    private Long goodsIdx;
    private String reviewContents;
    private int reviewStar;
    private int reviewIsReplied;
    private String reviewReply;
    private Date reviewDate;
    private Date reviewReplyDate;

    private String goodsName;
    private String reviewImg;

    @Builder
    public ReviewDTO(Long reviewIdx, Long userIdx, Long goodsIdx, String reviewContents,
                     int reviewStar, int reviewIsReplied, String reviewReply, Date reviewDate,
                     Date reviewReplyDate, String goodsName, String reviewImg) {
        this.reviewIdx = reviewIdx;
        this.userIdx = userIdx;
        this.goodsIdx = goodsIdx;
        this.reviewContents = reviewContents;
        this.reviewStar = reviewStar;
        this.reviewIsReplied = reviewIsReplied;
        this.reviewReply = reviewReply;
        this.reviewDate = reviewDate;
        this.reviewReplyDate = reviewReplyDate;
        this.goodsName = goodsName;
        this.reviewImg = reviewImg;
    }
}
