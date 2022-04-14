package com.ezenjpa.ezenjpaver.VO;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserListForAdmin {

    private Long userIdx;
    private String userId;
    private String userName;
    private Long totalAmount;
    private Long totalPrice;
    private Date joinDate;

}
