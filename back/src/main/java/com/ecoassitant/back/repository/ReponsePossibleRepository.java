package com.ecoassitant.back.repository;

import com.ecoassitant.back.entity.QuestionEntity;
import com.ecoassitant.back.entity.ReponsePossibleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * repository of ReponsePossible
 */
public interface ReponsePossibleRepository extends JpaRepository<ReponsePossibleEntity, Long> {

    /**
     * Get List<ResponsePossibleEntity> associated with question associate
     * @param questionEntity the question associate
     * @return List<ResponsePossibleEntity>
     */
    List<ReponsePossibleEntity> findByQuestionAsso(QuestionEntity questionEntity);
}
