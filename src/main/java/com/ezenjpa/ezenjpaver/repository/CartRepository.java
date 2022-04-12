package com.ezenjpa.ezenjpaver.repository;

import com.ezenjpa.ezenjpaver.VO.PurchaseListVO;
import com.ezenjpa.ezenjpaver.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartRepository extends JpaRepository<CartEntity, Long> {

    @Query(name = "purchaseListForMember", nativeQuery = true)
    List<PurchaseListVO> makingPurchaseListForMyPage(@Param("userIdx") Long userIdx);

}