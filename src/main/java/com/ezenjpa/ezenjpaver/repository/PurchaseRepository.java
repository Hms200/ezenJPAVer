package com.ezenjpa.ezenjpaver.repository;

import com.ezenjpa.ezenjpaver.entity.PurchaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<PurchaseEntity, Long> {
}