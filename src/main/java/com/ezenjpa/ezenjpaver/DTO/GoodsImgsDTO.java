package com.ezenjpa.ezenjpaver.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
public class GoodsImgsDTO {

    private Long goodsIdx;
    private String goodsImg;

    @Builder
    public GoodsImgsDTO(Long goodsIdx, String goodsImg) {
        this.goodsIdx = goodsIdx;
        this.goodsImg = goodsImg;
    }
}
