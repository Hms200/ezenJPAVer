package com.ezenjpa.ezenjpaver.DTO;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class CartDTO {

    private Long cartIdx;
    private Long cartListIdx;
    private Long userIdx;
    private Integer optionIdx;
    private Integer cartAmount;
    private Integer cartTotalPrice;
    private int cartIsDone;

    private Long purchaseIdx;
    private String purchaseStatement;
    private Date purchaseDate;

    private Long goodsIdx;
    private String goodsThumb;
    private String goodsName;
    private Integer goodsPrice;

    @Builder
    public CartDTO(Long cartIdx, Long cartListIdx, Long userIdx, Integer optionIdx, Integer cartAmount, Integer cartTotalPrice, int cartIsDone, Long purchaseIdx, String purchaseStatement, Date purchaseDate, Long goodsIdx, String goodsThumb, String goodsName, Integer goodsPrice) {
        this.cartIdx = cartIdx;
        this.cartListIdx = cartListIdx;
        this.userIdx = userIdx;
        this.optionIdx = optionIdx;
        this.cartAmount = cartAmount;
        this.cartTotalPrice = cartTotalPrice;
        this.cartIsDone = cartIsDone;
        this.purchaseIdx = purchaseIdx;
        this.purchaseStatement = purchaseStatement;
        this.purchaseDate = purchaseDate;
        this.goodsIdx = goodsIdx;
        this.goodsThumb = goodsThumb;
        this.goodsName = goodsName;
        this.goodsPrice = goodsPrice;
    }
}

