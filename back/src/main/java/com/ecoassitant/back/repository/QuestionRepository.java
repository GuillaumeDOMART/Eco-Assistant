package com.ecoassitant.back.repository;

import com.ecoassitant.back.entity.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * repository of Question
 */
public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {

}
