package com.ezenjpa.ezenjpaver.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
public class FaqDTO {

    private Long faqIdx;
    private String faqTitle;
    private String faqContents;
    private String faqCat;

    @Builder
    public FaqDTO(Long faqIdx, String faqTitle, String faqContents, String faqCat) {
        this.faqIdx = faqIdx;
        this.faqTitle = faqTitle;
        this.faqContents = faqContents;
        this.faqCat = faqCat;
    }
}
