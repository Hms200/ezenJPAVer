package com.ezenjpa.ezenjpaver.repository;

import com.ezenjpa.ezenjpaver.entity.OneToOneEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OneToOneRepository extends JpaRepository<OneToOneEntity, Long> {
}