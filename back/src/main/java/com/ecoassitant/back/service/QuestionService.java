package com.ecoassitant.back.service;

import com.ecoassitant.back.dto.QuestionUniqueDto;
import com.ecoassitant.back.entity.tools.Phase;

import java.util.HashMap;
import java.util.List;

/**
 * Service for QuestionController
 */
public interface QuestionService {
    /**
     * set quiz
     *
     * @return first question of quiz
     */
    public HashMap<Phase, List<QuestionUniqueDto>> getQuestionnaire();
}
