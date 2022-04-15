package com.ezenjpa.ezenjpaver.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "MALL_GOODS_IMGS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class GoodsImgsEntity {

    @Id
    @Column(name = "GOODS_IMG")
    private String goodsImg;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "GOODS_IDX")
    private GoodsEntity goodsEntity;




}
