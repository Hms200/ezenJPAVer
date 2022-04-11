package com.ezenjpa.ezenjpaver.repository;

import com.ezenjpa.ezenjpaver.entity.FaqEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FaqRepository extends JpaRepository<FaqEntity, Long> {
}