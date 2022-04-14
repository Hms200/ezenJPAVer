package com.ezenjpa.ezenjpaver.repository;

import com.ezenjpa.ezenjpaver.entity.CartListEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartListRepository extends JpaRepository<CartListEntity, Long> {
    CartListEntity getByCartListIdx(Long cartListIdx);
}