package com.ecoassitant.back.service;

import com.ecoassitant.back.dto.PhaseDto;
import com.ecoassitant.back.dto.quiz.QuestionUniqueDto;
import com.ecoassitant.back.entity.tools.Phase;

import java.util.List;
import java.util.Map;

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
