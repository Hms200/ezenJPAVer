package com.ezenjpa.ezenjpaver.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
public class GoodsOptionDTO {

    private Long optionIdx;
    private String optionName;
    private int optionPrice;

    @Builder
    public GoodsOptionDTO(Long optionIdx, String optionName, int optionPrice) {
        this.optionIdx = optionIdx;
        this.optionName = optionName;
        this.optionPrice = optionPrice;
    }
}
