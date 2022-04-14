package com.ezenjpa.ezenjpaver.repository;

import com.ezenjpa.ezenjpaver.entity.OptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OptionRepository extends JpaRepository<OptionEntity, Long> {
    OptionEntity getByOptionIdx(Long optionIdx);
}