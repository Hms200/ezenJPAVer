package com.ezenjpa.ezenjpaver.repository;

import com.ezenjpa.ezenjpaver.VO.PurchaseListVO;
import com.ezenjpa.ezenjpaver.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartRepository extends JpaRepository<CartEntity, Long> {
    // entity update util 용
    CartEntity getByCartIdx(Long cartIdx);
    // 사용자용 구매목록
    @Query(name = "purchaseListForMember", nativeQuery = true)
    List<PurchaseListVO> makingPurchaseListForMyPage(@Param("userIdx") Long userIdx);
    // 사용자용 구매목록 카테고리 필터적용
    @Query(name = "purchaseListForMemberByCat", nativeQuery = true)
    List<PurchaseListVO> makingPurchaseListForMyPageByCat(@Param("userIdx") Long userIdx,
                                                          @Param("statement") String statement);
    //장바구니 항목 불러오기
    List<CartEntity> getAllByCartIsDoneAndUserEntityUserIdx(int cartIsDone, Long userIdx);
    //장바구니에 담긴 항목 갯수 가져오기
    Long countCartEntitiesByCartIsDoneAndUserEntityUserIdx(int cartIsDone, Long userIdx);

}