package com.ezenjpa.ezenjpaver.repository;

import com.ezenjpa.ezenjpaver.entity.CartEntity;
import com.ezenjpa.ezenjpaver.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<CartEntity, Long> {



}