package com.ecoassitant.back.service;

import com.ecoassitant.back.dto.QuestionDto;

/**
 * Service for QuestionController
 */
public interface QuestionService {
    /**
     * set quiz
     * @return first question of quiz
     */
    public QuestionDto getQuestionnaire();
}
