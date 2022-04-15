package com.ezenjpa.ezenjpaver.repository;

import com.ezenjpa.ezenjpaver.entity.QuestionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {

    QuestionEntity getByQuestionIdx(Long questionIdx);
    List<QuestionEntity> getAllByUserEntityUserIdx(Long userIdx);
    // admin용 질문목록
    Page<QuestionEntity> findAll(Pageable pageable);


}