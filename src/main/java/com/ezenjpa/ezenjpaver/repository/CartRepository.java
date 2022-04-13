package com.ezenjpa.ezenjpaver.repository;

import com.ezenjpa.ezenjpaver.VO.PurchaseListVO;
import com.ezenjpa.ezenjpaver.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartRepository extends JpaRepository<CartEntity, Long> {

    CartEntity getByCartIdx(Long cartIdx);

    @Query(name = "purchaseListForMember", nativeQuery = true)
    List<PurchaseListVO> makingPurchaseListForMyPage(@Param("userIdx") Long userIdx);

    @Query(name = "purchaseListForMemberByCat", nativeQuery = true)
    List<PurchaseListVO> makingPurchaseListForMyPageByCat(@Param("userIdx") Long userIdx,
                                                          @Param("statement") String statement);

    List<CartEntity> getAllByCartIsDoneAndUserEntityUserIdx(int cartIsDone, Long userIdx);

    Long countCartEntitiesByCartIsDoneAndUserEntityUserIdx(int cartIsDone, Long userIdx);
}