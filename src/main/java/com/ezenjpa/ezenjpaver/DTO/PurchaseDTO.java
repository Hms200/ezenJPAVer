package com.ezenjpa.ezenjpaver.DTO;

import com.ezenjpa.ezenjpaver.enums.Statement;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@NoArgsConstructor
@Getter
@ToString
public class PurchaseDTO {

    private Long purchaseIdx;
    private Long cartListIdx;
    private Long userIdx;
    private int purchaseTotalPrice;
    private String purchaseBuyerName;
    private String purchaseBuyerPhone;
    private String purchaseBuyerAddress;
    private String purchasePayment;
    private String purchaseBuyerRequest;
    private Date purchaseDate;
    private Statement purchaseStatement;

    @Builder
    public PurchaseDTO(Long purchaseIdx, Long cartListIdx, Long userIdx, int purchaseTotalPrice, String purchaseBuyerName, String purchaseBuyerPhone, String purchaseBuyerAddress, String purchasePayment, String purchaseBuyerRequest, Date purchaseDate, Statement purchaseStatement) {
        this.purchaseIdx = purchaseIdx;
        this.cartListIdx = cartListIdx;
        this.userIdx = userIdx;
        this.purchaseTotalPrice = purchaseTotalPrice;
        this.purchaseBuyerName = purchaseBuyerName;
        this.purchaseBuyerPhone = purchaseBuyerPhone;
        this.purchaseBuyerAddress = purchaseBuyerAddress;
        this.purchasePayment = purchasePayment;
        this.purchaseBuyerRequest = purchaseBuyerRequest;
        this.purchaseDate = purchaseDate;
        this.purchaseStatement = purchaseStatement;
    }
}
