package com.ecoassitant.back.service.impl;

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

    /**
     * Set 1st question of questionary, all the questionnary is accessible with responses.questionSuiv
     *
     * @return 1st question of questionary
     */
    @Override
    public Map<Phase, List<QuestionUniqueDto>> getQuestionnaire() {
        var questionEntity = questionRepository.findAll().stream().findFirst();
        var questionDto = questionEntity.map(QuestionDto::new).orElse(null);
        return QuestionUniqueDto.Mapper(questionDto);
    }

    @Override
    public Map<Phase, List<QuestionUniqueDto>> completQuiz(Integer id) {
        var questionEntity = questionRepository.findAll().stream().findFirst();
        var questionDto = questionEntity.map(QuestionDto::new).orElse(null);
        var projet = projetRepository.findById(id);
        if (projet.isEmpty())
            return null;
        var reponses = reponseDonneeRepository.findByReponseDonneeKey_Projet(projet.get());
        questionDto.addReponses(reponses);
        var questionUniqueDto = QuestionUniqueDto.Mapper(questionDto);
        return questionUniqueDto;
    }

}
