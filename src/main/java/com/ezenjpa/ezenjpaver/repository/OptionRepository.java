package com.ezenjpa.ezenjpaver.repository;

import com.ezenjpa.ezenjpaver.entity.OptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepository extends JpaRepository<OptionEntity, Long> {
}