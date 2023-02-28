package com.ecoassitant.back.repository;

import com.ecoassitant.back.entity.QuestionEntity;
import com.ecoassitant.back.entity.tools.Phase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * repository of Question
 */
public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {
    List<QuestionEntity> findQuestionEntityByPhaseOrderByIdQuestion(Phase phase);

}
