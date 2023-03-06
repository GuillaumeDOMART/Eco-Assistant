package com.ecoassitant.back.service.impl;

import com.ecoassitant.back.dto.quiz.PhaseDto;
import com.ecoassitant.back.dto.quiz.QuestionUniqueDto;
import com.ecoassitant.back.repository.GivenAnswerRepository;
import com.ecoassitant.back.repository.ProjectRepository;
import com.ecoassitant.back.repository.QuestionRepository;
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
    private final ProjectRepository projectRepository;
    private final GivenAnswerRepository givenAnswerRepository;

    /**
     * Function to create QuestionServiceImpl with QuestionRepository
     *
     * @param questionRepository      the QuestionRepository
     * @param projectRepository        the ProjectRepository
     * @param givenAnswerRepository the GivenAnswerRepository
     */
    public QuestionServiceImpl(QuestionRepository questionRepository, ProjectRepository projectRepository, GivenAnswerRepository givenAnswerRepository) {
        this.questionRepository = questionRepository;
        this.projectRepository = projectRepository;
        this.givenAnswerRepository = givenAnswerRepository;
    }

    /**
     * add response of previous quiz to the currently quiz
     * @param questions list of question
     * @param idProject idProject of the previous quiz
     * @return questions with the response of the previous quiz
     */
    private List<QuestionUniqueDto> completQuiz(List<QuestionUniqueDto> questions, Integer idProject) {
        var project = projectRepository.findByIdProject(idProject);
        if (project.isEmpty())
            return new ArrayList<QuestionUniqueDto>();
        var responses = givenAnswerRepository.findByGivenAnswerKey_Project(project.get());
        responses.forEach(response ->{
            questions.forEach(question -> question.remplir(response));
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
