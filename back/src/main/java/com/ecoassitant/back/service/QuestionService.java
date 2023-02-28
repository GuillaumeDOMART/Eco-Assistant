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
     * set quiz with response of the previous quiz
     * @param id id of project
     * @return map of question by sort by phase
     */
    public List<QuestionUniqueDto> completQuiz(List<QuestionUniqueDto> questions, Integer idProject);

    List<QuestionUniqueDto> completPhase(PhaseDto phaseDto);
}
