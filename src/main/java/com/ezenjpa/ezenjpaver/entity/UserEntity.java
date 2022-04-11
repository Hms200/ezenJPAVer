package com.ezenjpa.ezenjpaver.entity;

import com.ezenjpa.ezenjpaver.DTO.UserDTO;
import com.ezenjpa.ezenjpaver.enums.UserProvider;
import com.ezenjpa.ezenjpaver.repository.UserRepository;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "MALL_USER")
@SequenceGenerator(name = "user_seq_generator",
                    sequenceName = "MALL_USER_SEQ",
                    allocationSize = 1)
@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator = "user_seq_generator")
    @Column(name = "USER_IDX")
    private Long userIdx;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "USER_PW")
    private String userPw;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "USER_PHONE")
    private String userPhone;

    @Column(name = "USER_ADDRESS")
    private String userAddress;

    @Column(name = "USER_EMAIL")
    private String userEmail;

    @Column(name = "JOIN_DATE")
    @Temporal(TemporalType.DATE)
    private Date joinDate;

    @Column(name = "USER_PROVIDER")
    @Enumerated(EnumType.STRING)
    private UserProvider userProvider;

    @Builder
    public UserEntity(Long userIdx, String userId, String userPw, String userName, String userPhone, String userAddress, String userEmail, Date joinDate, UserProvider userProvider) {
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

    // 연관관계 mapping - cart
    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<CartEntity> cartEntities;

    // converter
    public UserDTO convertToUserDTO(UserEntity userEntity){
        return UserDTO.builder()
                .userIdx(userEntity.getUserIdx())
                .userId(userEntity.getUserId())
                .userName(userEntity.getUserName())
                .userEmail(userEntity.getUserEmail())
                .userPhone(userEntity.getUserPhone())
                .userAddress(userEntity.getUserAddress())
                .userProvider(userEntity.getUserProvider().name())
                .joinDate(userEntity.getJoinDate())
                .userPw(userEntity.getUserPw())
                .build();
    }


}
