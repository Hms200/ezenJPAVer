package com.ezenjpa.ezenjpaver.repository;

import com.ezenjpa.ezenjpaver.entity.CartEntity;
import com.ezenjpa.ezenjpaver.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartRepository extends JpaRepository<CartEntity, Long> {

    @Query("select c, p from CartEntity c inner join PurchaseEntity p on c.cartListEntity.cartListIdx = p.cartListEntity.cartListIdx where c.userEntity.userIdx = :userIdx and c.cartIsDone = 1")
    List<CartEntity> makingPurchaseListForMyPage(@Param("userIdx") Long userIdx);

}