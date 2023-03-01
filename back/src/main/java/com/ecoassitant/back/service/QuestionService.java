package com.ecoassitant.back.service;

import com.ecoassitant.back.dto.quiz.PhaseDto;
import com.ecoassitant.back.dto.quiz.QuestionUniqueDto;

import java.util.List;

/**
 * Service for QuestionController
 */
public interface QuestionService {

    /**
     * Return list of QUiz for a Phase
     * @param phaseDto phase of the quiz
     * @return quiz of this phase
     */
    List<QuestionUniqueDto> completPhase(PhaseDto phaseDto);
}
