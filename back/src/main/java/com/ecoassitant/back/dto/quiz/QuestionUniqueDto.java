package com.ecoassitant.back.dto.quiz;

import com.ecoassitant.back.entity.QuestionEntity;
import com.ecoassitant.back.entity.GivenAnswerEntity;
import com.ecoassitant.back.entity.tools.Phase;
import com.ecoassitant.back.entity.tools.TypeQ;

import java.util.ArrayList;
import java.util.List;

/**
 * Question with Id for questionSuiv
 */
public class QuestionUniqueDto {
    private final Long questionId;
    private final String entitled;
    private final Phase phase;
    private final TypeQ type;
    private final Long dependency;
    private final List<ResponseUniqueDto> responses;
    private GivenAnswerDtoQuiz response;

    public Long getDependency() {
        return dependency;
    }




    /**
     * create a question with a quiz
     * @param quiz format Entity
     */
    public QuestionUniqueDto(QuestionEntity quiz) {
        this.entitled = quiz.getEntitled();
        this.phase = quiz.getPhase();
        this.questionId = quiz.getIdQuestion();
        this.dependency = quiz.getDependency()!= null? quiz.getDependency().getIdReponsePos(): -1;
        this.type = quiz.getTypeQ();
        this.responses = new ArrayList<>();
        quiz.getResponses().forEach(reponsePossibleEntity -> responses.add(new ResponseUniqueDto(reponsePossibleEntity)));
        this.response = null;
    }

    /**
     * set the response of the previous quiz
     * @param response response of the previous quiz
     */
    public void remplir(GivenAnswerEntity response) {
        if (questionId == response.getGivenAnswerKey().getQuestion().getIdQuestion())
            this.response = new GivenAnswerDtoQuiz(response);
    }

    public GivenAnswerDtoQuiz getResponse() {
        return response;
    }
    public Long getQuestionId() {
        return questionId;
    }

    public String getEntitled() {
        return entitled;
    }

    public Phase getPhase() {
        return phase;
    }

    public List<ResponseUniqueDto> getResponses() {
        return responses;
    }

    public TypeQ getType() {
        return type;
    }

}
