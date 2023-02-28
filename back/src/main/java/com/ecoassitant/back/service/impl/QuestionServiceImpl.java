package com.ecoassitant.back.service.impl;

import com.ecoassitant.back.dto.PhaseDto;
import com.ecoassitant.back.dto.quiz.QuestionDto;
import com.ecoassitant.back.dto.quiz.QuestionUniqueDto;
import com.ecoassitant.back.entity.tools.Phase;
import com.ecoassitant.back.repository.ProjetRepository;
import com.ecoassitant.back.repository.QuestionRepository;
import com.ecoassitant.back.repository.ReponseDonneeRepository;
import com.ecoassitant.back.service.QuestionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

    @Override
    public List<QuestionUniqueDto> completQuiz(List<QuestionUniqueDto> questions, Integer idProject) {
        var projet = projetRepository.findById(idProject);
        if (projet.isEmpty())
            return null;
        var reponses = reponseDonneeRepository.findByReponseDonneeKey_Projet(projet.get());
        reponses.forEach(reponse ->{
            questions.forEach(question -> question.remplir(reponse));
        });
        return questions;
    }

    public List<QuestionUniqueDto> completPhase(PhaseDto phaseDto) {
        var questionsEntity = questionRepository.findQuestionEntityByPhaseOrderByIdQuestion(phaseDto.getPhase());
        var questionsUniqueDto = questionsEntity.stream().map(QuestionUniqueDto::new).toList();
        return completQuiz(questionsUniqueDto, phaseDto.getId());
    }
}
