package com.ecoassitant.back.repository;

import com.ecoassitant.back.entity.QuestionProposeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository of QuestionPropose
 */
public interface QuestionProposeRepository extends JpaRepository<QuestionProposeEntity, Long> {
}
