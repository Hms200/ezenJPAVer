package com.ezenjpa.ezenjpaver.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "MALL_PURCHASE")
@SequenceGenerator(name = "purchase_seq_generator",
                    sequenceName = "MALL_PURCHASE_SEQ",
                    allocationSize = 1)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PurchaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator = "purchase_seq_generator")
    @Column(name = "PURCHASE_IDX")
    private Long purchaseIdx;

    @OneToOne
    @JoinColumn(name = "CART_LIST_IDX")
    private CartListEntity cartListEntity;

    @ManyToOne
    @JoinColumn(name = "USER_IDX")
    private UserEntity userEntity;

    @Column(name = "PURCHASE_TOTAL_PRICE")
    private Integer totalPrice;

    @Column(name = "PURCHASE_BUYER_NAME")
    private String buyerName;

    @Column(name = "PURCHASE_BUYER_PHONE")
    private String buyerPhone;

    @Column(name = "PURCHASE_BUYER_ADDRESS")
    private String buyerAddress;

    @Column(name = "PURCHASE_PAYMENT")
    private String payment;

    @Column(name = "PURCHASE_BUYER_REQUEST")
    private String buyerRequest;

    @Column(name = "PURCHASE_DATE")
    @Temporal(TemporalType.DATE)
    private Date purchaseDate;

    @Column(name = "PURCHASE_STATEMENT")
    private String purchaseStatement;

}
