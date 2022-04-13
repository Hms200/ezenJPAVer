package com.ezenjpa.ezenjpaver.VO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class GoodsListVO {

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
}
