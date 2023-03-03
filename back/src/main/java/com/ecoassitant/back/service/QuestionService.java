package com.ecoassitant.back.service;

import com.ecoassitant.back.dto.quiz.QuestionUniqueDto;
import com.ecoassitant.back.entity.tools.Phase;

import java.util.List;
import java.util.Map;

/**
 * Service for QuestionController
 */
public interface QuestionService {
    /**
     * set quiz
     *
     * @return first question of quiz
     */
    public Map<Phase, List<QuestionUniqueDto>> getQuestionnaire();
}
