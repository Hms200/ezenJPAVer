package com.ezenjpa.ezenjpaver.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@NoArgsConstructor
@Getter
@ToString
public class UserDTO {

    private Long userIdx;
    private String userId;
    private String userPw;
    private String userName;
    private String userPhone;
    private String userAddress;
    private String userEmail;
    private Date joinDate;
    private String userProvider;  // enum

    @Builder
    public UserDTO(Long userIdx, String userId, String userPw, String userName, String userPhone, String userAddress, String userEmail, Date joinDate, String userProvider) {
        this.userIdx = userIdx;
        this.userId = userId;
        this.userPw = userPw;
        this.userName = userName;
        this.userPhone = userPhone;
        this.userAddress = userAddress;
        this.userEmail = userEmail;
        this.joinDate = joinDate;
        this.userProvider = userProvider;
    }
}
