package com.ecoassitant.back.dto.quiz;

import com.ecoassitant.back.dto.quiz.QuestionDto;
import com.ecoassitant.back.entity.ReponsePossibleEntity;

/**
 * ReponsePossible build with ReponsePossibleEntity for use in app
 */
public class ReponsePossibleDto {
    private Long reponseId;
    private QuestionDto questionSuiv;
    private String intitule;
    private int constante;

    /**
     * Constructor of ReponsePossibleDto with a ReponsePossibleEntity
     * @param reponse ReponsePossible entity change int ReponsePossibleDto
     */
    public ReponsePossibleDto(ReponsePossibleEntity reponse) {
        this.questionSuiv = reponse.getQuestionSuiv() != null? new QuestionDto(reponse.getQuestionSuiv()) : null;
        this.intitule = reponse.getIntitule();
        this.constante = reponse.getConstante().getConstante();
        this.reponseId = reponse.getIdReponsePos();
    }

    public QuestionDto getQuestionSuiv() {
        return questionSuiv;
    }

    public void setQuestionSuiv(QuestionDto questionSuiv) {
        this.questionSuiv = questionSuiv;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public int getConstante() {
        return constante;
    }

    public void setConstante(int constante) {
        this.constante = constante;
    }

    /**
     * get the Id
     * @return id of the response
     */
    public Long getId() {
        return reponseId;
    }

    /**
     * ser id
     * @param id of the response
     */
    public void setId(Long id) {
        this.reponseId = id;
    }
}
