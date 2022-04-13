package com.ezenjpa.ezenjpaver.repository;

import com.ezenjpa.ezenjpaver.entity.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {

    QuestionEntity getByQuestionIdx(Long questionIdx);

}