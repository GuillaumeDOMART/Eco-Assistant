package com.ecoassitant.back.repository;

import com.ecoassitant.back.entity.QuestionEntity;
import com.ecoassitant.back.entity.ResponsePossibleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * repository of ResponsePossible
 */
public interface ResponsePossibleRepository extends JpaRepository<ResponsePossibleEntity, Long> {

    /**
     * Get List<ResponsePossibleEntity> associated with question associate
     * @param questionEntity the question associate
     * @return List<ResponsePossibleEntity>
     */
    List<ResponsePossibleEntity> findByQuestionAsso(QuestionEntity questionEntity);
}
