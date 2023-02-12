package com.ecoassitant.back.repository;

import com.ecoassitant.back.entity.QuestionEntity;
import com.ecoassitant.back.entity.ReponseDonneeEntity;
import com.ecoassitant.back.entity.ReponsePossibleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * repository of ReponsePossible
 */
public interface ReponsePossibleRepository extends JpaRepository<ReponsePossibleEntity, Long> {
    List<ReponsePossibleEntity> findByQuestionAsso(QuestionEntity questionEntity);
}
