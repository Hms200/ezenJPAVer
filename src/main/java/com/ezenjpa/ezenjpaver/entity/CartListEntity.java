package com.ezenjpa.ezenjpaver.entity;

import lombok.*;

import javax.persistence.*;

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

}
