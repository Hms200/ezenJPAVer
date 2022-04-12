package com.ezenjpa.ezenjpaver.VO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class PurchaseListVO {

    private Long cartIdx;
    private Long CartListIdx;
    private Long userIdx;
    private Integer cartAmount;
    private Integer optionIdx;
    private Integer cartTotalPrice;
    private int cartIsDone;
    private Long purchaseIdx;
    private String purchaseStatement;
    private Date purchaseDate;
    private Long goodsIdx;
    private String goodsThumb;
    private String goodsName;
    private Integer goodsPrice;
}
