package com.ezenjpa.ezenjpaver.repository;

import com.ezenjpa.ezenjpaver.entity.PurchaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<PurchaseEntity, Long> {

    PurchaseEntity getByUserEntityUserIdx(Long userIdx);
}