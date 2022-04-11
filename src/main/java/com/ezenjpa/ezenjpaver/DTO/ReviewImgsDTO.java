package com.ezenjpa.ezenjpaver.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
public class ReviewImgsDTO {

    private Long reviewIdx;
    private String reviewImg;

    @Builder
    public ReviewImgsDTO(Long reviewIdx, String reviewImg) {
        this.reviewIdx = reviewIdx;
        this.reviewImg = reviewImg;
    }
}
