package com.ezenjpa.ezenjpaver.repository;

import com.ezenjpa.ezenjpaver.entity.FaqEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FaqRepository extends JpaRepository<FaqEntity, Long> {

    FaqEntity getByFaqIdx(Long faqIdx);

    @Query("select f from FaqEntity f")
    List<FaqEntity> getAll();

    List<FaqEntity> getFaqEntitiesByFaqCat(String cat);

}