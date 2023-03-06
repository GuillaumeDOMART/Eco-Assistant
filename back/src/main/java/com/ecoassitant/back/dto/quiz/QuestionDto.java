package com.ecoassitant.back.dto.quiz;

import com.ecoassitant.back.entity.GivenAnswerEntity;
import com.ecoassitant.back.entity.QuestionEntity;
import com.ecoassitant.back.entity.tools.Phase;
import com.ecoassitant.back.entity.tools.TypeQ;

import java.util.ArrayList;
import java.util.List;

/**
 * Question build with an QuestionEntity without id for use in app
 */
public class QuestionDto {
    private Long questionId;
    private String entitled;
    private TypeQ type;
    private Phase phase;
    private  List<ResponsePossibleDto> responses;
    private Long dependency;
    private GivenAnswerDtoQuiz response;


    /**
     * Constructor of questionDto
     * @param question question Entity change into questionDto
     */
    public QuestionDto(QuestionEntity question) {
        if (question == null)
            return;
        this.questionId = question.getIdQuestion();
        this.entitled = question.getEntitled();
        this.response = null;
        this.type = question.getTypeQ();
        this.phase = question.getPhase();
        this.responses = new ArrayList<>();
        question.getResponses().forEach(response -> responses.add(new ResponsePossibleDto(response)));
        if (question.getDependency() == null)
            this.dependency = -1L;
        else
            this.dependency = question.getDependency().getIdReponsePos();
    }

    public String getEntitled() {
        return entitled;
    }

    public void setEntitled(String entitled) {
        this.entitled = entitled;
    }

    public TypeQ getType() {
        return type;
    }

    public void setType(TypeQ type) {
        this.type = type;
    }

    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    public List<ResponsePossibleDto> getResponses() {
        return responses;
    }

    public void setResponses(List<ResponsePossibleDto> responses) {
        this.responses = List.copyOf(responses);
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Long getDependency() {
        return dependency;
    }

    public void setDependency(Long dependency) {
        this.dependency = dependency;
    }

    /**
     * add all responses in the quiz
     * @param responses list of response of th previous quiz
     */
    public void addResponses(List<GivenAnswerEntity> responses){
        responses.forEach(responseDonneeEntity -> addReponse(this, responseDonneeEntity));
    }

    /**
     * add a response in the quiz
     * @param question a question
     * @param response response of th previous quiz
     */
    private void addReponse(QuestionDto question, GivenAnswerEntity response){
        if (question == null)
            return;
        if (question.questionId == response.getGivenAnswerKey().getQuestion().getIdQuestion()){
            question.response = new GivenAnswerDtoQuiz(response);
            return;
        }
        question.responses.forEach(responsePossibleDto -> addReponse(responsePossibleDto.getNextQuestion(),response));
    }

    /**
     * get the response of the question complete previously else null
     * @return response of the previous quiz
     */
    public GivenAnswerDtoQuiz getResponse() {
        return response;
    }
}
