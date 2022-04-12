package com.ezenjpa.ezenjpaver.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@NoArgsConstructor
@Getter
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
    public CartDTO(Long cartIdx, Long cartListIdx, Long userIdx, Integer optionIdx, Integer cartAmount, Integer cartTotalPrice, int cartIsDone) {
        this.cartIdx = cartIdx;
        this.cartListIdx = cartListIdx;
        this.userIdx = userIdx;
        this.optionIdx = optionIdx;
        this.cartAmount = cartAmount;
        this.cartTotalPrice = cartTotalPrice;
        this.cartIsDone = cartIsDone;
    }
}
