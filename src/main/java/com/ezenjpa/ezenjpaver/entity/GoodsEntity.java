package com.ezenjpa.ezenjpaver.entity;

import com.ezenjpa.ezenjpaver.DTO.GoodsDTO;
import com.ezenjpa.ezenjpaver.enums.Events;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "MALL_GOODS")
@SequenceGenerator(name = "goods_seq_generator",
                    sequenceName = "MALL_GOODS_SEQ",
                    allocationSize = 1)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class GoodsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE
                    ,generator = "goods_seq_generator")
    @Column(name = "GOODS_IDX")
    private Long goodsIdx;

    @Column(name = "GOODS_NAME")
    private String goodsName;

    @Column(name = "GOODS_PRICE")
    private Integer goodsPrice;

    @Column(name = "GOODS_THUMB")
    private String goodsThumb;

    @Column(name = "GOODS_CAT")
    private String goodsCat;

    @Column(name = "GOODS_STOCK")
    private Integer goodsStock;

    @Column(name = "GOODS_ONEVENT")
    @Enumerated(EnumType.ORDINAL)
    private Events goodsOnEvent;

    @Column(name = "GOODS_PURCHASED")
    private Integer goodsPurchased;

    @Column(name = "GOODS_DETAIL")
    private String goodsDetail;

    @Column(name = "GOODS_ONSALE")
    private Integer goodsOnSale;

    //연관관계 mapping - review
    @OneToMany(mappedBy = "goodsEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<ReviewEntity> reviewEntities;

    //연관관계 mapping - cart
    @OneToMany(mappedBy = "goodsEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<CartEntity> cartEntities;


    //상품dto로 변환
    public GoodsDTO convertToGoodsDTO(GoodsEntity entity){
        return GoodsDTO.builder()
                    .goodsIdx(entity.getGoodsIdx())
                    .goodsThumb(entity.getGoodsThumb())
                    .goodsName(entity.getGoodsName())
                    .goodsPrice(entity.getGoodsPrice())
                    .goodsDetail(entity.getGoodsDetail())
                    .goodsStock(entity.getGoodsStock())
                    .goodsCat(entity.getGoodsCat())
                    .goodsOnEvent(entity.getGoodsOnEvent().ordinal())
                    .goodsOnSale(entity.getGoodsOnSale())
                    .goodsPurchased(entity.getGoodsPurchased())
                    .build();
    }


}

