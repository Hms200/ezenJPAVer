package com.ezenjpa.ezenjpaver.repository;

import com.ezenjpa.ezenjpaver.entity.OneToOneEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OneToOneRepository extends JpaRepository<OneToOneEntity, Long> {

    OneToOneEntity getByOneToOneIdx(Long oneToOneIdx);

    List<OneToOneEntity> getAllByUserEntityUserIdx(Long userIdx);

    List<OneToOneEntity> getAllByUserEntityUserIdxAndOneToOneCat(Long userIdx, String cat);

    // admin 용 1:1 질문 리스트
    Page<OneToOneEntity> findAll(Pageable pageable);
}