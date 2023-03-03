package com.ecoassitant.back.service.impl;

import com.ecoassitant.back.dto.quiz.PhaseDto;
import com.ecoassitant.back.dto.quiz.QuestionUniqueDto;
import com.ecoassitant.back.repository.ProjetRepository;
import com.ecoassitant.back.repository.QuestionRepository;
import com.ecoassitant.back.repository.ReponseDonneeRepository;
import com.ecoassitant.back.service.QuestionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * implementation of questionService
 */
@Service
public class
QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;
    private final ProjetRepository projetRepository;
    private final ReponseDonneeRepository reponseDonneeRepository;

    /**
     * Function to create QuestionServiceImpl with QuestionRepository
     *
     * @param questionRepository      the QuestionRepository
     * @param projetRepository        the ProjetRepository
     * @param reponseDonneeRepository the ReponseDonneeRepository
     */
    public QuestionServiceImpl(QuestionRepository questionRepository, ProjetRepository projetRepository, ReponseDonneeRepository reponseDonneeRepository) {
        this.questionRepository = questionRepository;
        this.projetRepository = projetRepository;
        this.reponseDonneeRepository = reponseDonneeRepository;
    }

    /**
     * add reponse of previous quiz to the currently quiz
     * @param questions list of question
     * @param idProject idProject of the previous quiz
     * @return questions with the response of the previous quiz
     */
    private List<QuestionUniqueDto> completQuiz(List<QuestionUniqueDto> questions, Integer idProject) {
        var projet = projetRepository.findById(idProject);
        if (projet.isEmpty())
            return new ArrayList<QuestionUniqueDto>();
        var reponses = reponseDonneeRepository.findByReponseDonneeKey_Projet(projet.get());
        reponses.forEach(reponse ->{
            questions.forEach(question -> question.remplir(reponse));
        });
        return questions;
    }

    /**
     * Return list of QUiz for a Phase
     * @param phaseDto phase of the quiz
     * @return quiz of this phase
     */
    public List<QuestionUniqueDto> completPhase(PhaseDto phaseDto) {
        var questionsEntity = questionRepository.findQuestionEntityByPhaseOrderByIdQuestion(phaseDto.getPhase());
        var questionsUniqueDto = questionsEntity.stream().map(QuestionUniqueDto::new).toList();
        return completQuiz(questionsUniqueDto, phaseDto.getId());
    }
}
