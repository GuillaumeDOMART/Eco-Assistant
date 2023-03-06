package com.ecoassitant.back.repository;

import com.ecoassitant.back.entity.ProposedQuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository of QuestionPropose
 */
public interface ProposedQuestionRepository extends JpaRepository<ProposedQuestionEntity, Long> {
}
