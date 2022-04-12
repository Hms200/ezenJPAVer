package com.ezenjpa.ezenjpaver.repository;

import com.ezenjpa.ezenjpaver.entity.GoodsEntity;
import com.ezenjpa.ezenjpaver.enums.Events;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoodsRepository extends JpaRepository<GoodsEntity, Long> {

    GoodsEntity getByGoodsIdx(Long goodsIdx);
    // 판매중인 상품 중 판매량 상위 10개
    List<GoodsEntity> getTop10ByGoodsOnSaleOrderByGoodsPurchasedDesc(int goodsOnSale);
    // 해당 이벤트가 걸려있고 판매중인 상품
    List<GoodsEntity> getGoodsEntitiesByGoodsOnEventAndGoodsOnSale(Events event, int GoodsOnSale);
    // 판매중인 모든 상품
    List<GoodsEntity> getGoodsEntitiesByGoodsOnSale(int goodsOnSale);
    // 메인검색용 상품검색
    List<GoodsEntity> getGoodsEntitiesByGoodsNameContaining(String searchText);
}