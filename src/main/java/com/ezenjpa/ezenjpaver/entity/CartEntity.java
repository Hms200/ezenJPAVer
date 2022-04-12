package com.ezenjpa.ezenjpaver.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "MALL_CART")
@SequenceGenerator(name = "cart_seq_generator",
                    sequenceName = "MALL_CART_SEQ",
                    allocationSize = 1)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator = "cart_seq_generator")
    @Column(name = "CART_IDX")
    private Long cartIdx;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CART_LIST_IDX")
    private CartListEntity cartListEntity;

    @ManyToOne
    @JoinColumn(name = "USER_IDX")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "GOODS_IDX")
    private GoodsEntity goodsEntity;

    @ManyToOne
    @JoinColumn(name = "OPTION_IDX")
    private OptionEntity optionEntity;

    @Column(name = "CART_AMOUNT")
    private Integer cartAmount;

    @Column(name = "CART_TOTAL_PRICE")
    private Integer cartTotalPrice;

    @Column(name = "CART_ISDONE")
    private Integer cartIsDone;

    @Transient
    
    private PurchaseEntity purchaseEntity;

}
