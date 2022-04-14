package com.ezenjpa.ezenjpaver.repository;

import com.ezenjpa.ezenjpaver.entity.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {

    QuestionEntity getByQuestionIdx(Long questionIdx);

    List<QuestionEntity> getAllByUserEntityUserIdx(Long userIdx);


}