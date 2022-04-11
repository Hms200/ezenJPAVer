package com.ezenjpa.ezenjpaver.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
public class GoodsDTO {

    private Long goodsIdx;
    private String goodsName;
    private Integer goodsPrice;
    private String goodsThumb;
    private String goodsCat;
    private Integer goodsStock;
    private Integer goodsOnEvent;     // enum
    private Integer goodsPurchased;
    private String goodsDetail;
    private Integer goodsOnSale;

    @Builder
    public GoodsDTO(Long goodsIdx, String goodsName, Integer goodsPrice, String goodsThumb, String goodsCat, Integer goodsStock, Integer goodsOnEvent, Integer goodsPurchased, String goodsDetail, Integer goodsOnSale) {
        this.goodsIdx = goodsIdx;
        this.goodsName = goodsName;
        this.goodsPrice = goodsPrice;
        this.goodsThumb = goodsThumb;
        this.goodsCat = goodsCat;
        this.goodsStock = goodsStock;
        this.goodsOnEvent = goodsOnEvent;
        this.goodsPurchased = goodsPurchased;
        this.goodsDetail = goodsDetail;
        this.goodsOnSale = goodsOnSale;
    }
}
