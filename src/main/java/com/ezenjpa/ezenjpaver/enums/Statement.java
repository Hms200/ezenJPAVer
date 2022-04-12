package com.ezenjpa.ezenjpaver.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum Statement {
    STATEMENT(0,"주문상태"),
    ACCEPTED(1,"주문접수"),
    PREPARE_GOODS(2,"상품준비중"),
    SHIPPING(3,"배송중"),
    SHIPPED(4,"배송완료"),
    REFUND_ASKED(5,"반품신청"),
    CHANGE_ASKED(6,"교환신청"),
    REFUND_ACCEPTED(7,"반품접수"),
    CHANGE_ACCEPTED(8,"교환접수"),
    CANCEL(9,"주문취소"),
    DONE(10,"완료");

    private int code;
    private String description;

    public static Statement desriptionOf(String description){
        for(Statement stmt : Statement.values()){
            if(stmt.getDescription().equals(description)){
                return stmt;
            }
        }
        return null;
    }

    public static Statement getStatementByCode(int code){
        for(Statement stmt : Statement.values()){
            if(stmt.getCode() == code){
                return stmt;
            }
        }
        return null;
    }
}
