package com.ezenjpa.ezenjpaver.repository;

import com.ezenjpa.ezenjpaver.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    // 카트숫자 가져오기
    Long countUserEntitiesByUserIdxAndCartEntitiesNotNull(long userIdx);
    // id로 찾기
    UserEntity findByUserId(String userID);
}