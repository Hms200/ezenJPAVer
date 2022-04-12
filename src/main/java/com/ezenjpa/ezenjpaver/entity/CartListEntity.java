package com.ezenjpa.ezenjpaver.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "MALL_CART_LIST")
@SequenceGenerator(name = "cartList_seq_generator",
                    sequenceName = "MALL_CART_LIST_SEQ",
                    allocationSize = 1)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CartListEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator = "cartList_seq_generator")
    @Column(name = "CART_LIST_IDX")
    private Long cartListIdx;

    // 연관관계 mapping - cart list
    @OneToMany(mappedBy = "cartListEntity")
    @ToString.Exclude
    private List<CartEntity> cartEntities;
    // 연관관계 mapping - purchase list
    @OneToOne(mappedBy = "cartListEntity", fetch = FetchType.EAGER)
    @ToString.Exclude
    private PurchaseEntity purchaseEntity;

}
