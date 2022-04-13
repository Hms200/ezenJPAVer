package com.ezenjpa.ezenjpaver.repository;

import com.ezenjpa.ezenjpaver.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity getByUserIdx(Long userIdx);

    // id로 찾기
    UserEntity findByUserId(String userID);
    // id와 email로 찾기
    UserEntity findByUserNameAndUserEmail(String userName, String userEmail);
    // id 이름 email로 찾기
    UserEntity findByUserIdAndUserNameAndUserEmail(String userId, String userName, String userEmail);
}