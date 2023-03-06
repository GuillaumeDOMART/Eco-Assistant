package com.ecoassitant.back.dto.quiz;

import com.ecoassitant.back.entity.ResponsePossibleEntity;

/**
 * ResponsePossible build with ResponsePossibleEntity for use in app
 */
public class ResponsePossibleDto {
    private Long responseId;
    private QuestionDto nextQuestion;
    private String entitled;
    private double constant;

    /**
     * Constructor of ResponsePossibleDto with a ResponsePossibleEntity
     * @param response ResponsePossible entity change int ResponsePossibleDto
     */
    public ResponsePossibleDto(ResponsePossibleEntity response) {
        this.nextQuestion = response.getQuestionSuiv() != null? new QuestionDto(response.getQuestionSuiv()) : null;
        this.entitled = response.getIntitule();
        this.constant = response.getConstante().getConstant();
        this.responseId = response.getIdReponsePos();
    }

    public QuestionDto getNextQuestion() {
        return nextQuestion;
    }

    public void setNextQuestion(QuestionDto nextQuestion) {
        this.nextQuestion = nextQuestion;
    }

    public String getEntitled() {
        return entitled;
    }

    public void setEntitled(String entitled) {
        this.entitled = entitled;
    }

    public double getConstant() {
        return constant;
    }

    public void setConstant(double constant) {
        this.constant = constant;
    }

    /**
     * get the Id
     * @return id of the response
     */
    public Long getId() {
        return responseId;
    }

    /**
     * ser id
     * @param id of the response
     */
    public void setId(Long id) {
        this.responseId = id;
    }
}
