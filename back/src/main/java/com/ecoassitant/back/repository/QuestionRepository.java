package com.ecoassitant.back.repository;

import com.ecoassitant.back.entity.QuestionEntity;
import com.ecoassitant.back.entity.tools.Phase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * repository of Question
 */
public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {
    /**
     * list of question for a phase sort by ID
     * @param phase phase of the quiz
     * @return list of question
     */
    List<QuestionEntity> findQuestionEntityByPhaseOrderByIdQuestion(Phase phase);

}
