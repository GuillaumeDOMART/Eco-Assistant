package com.ecoassitant.back.dto;

import com.ecoassitant.back.entity.ReponsePossibleEntity;

/**
 * ReponsePossible build with ReponsePossibleEntity for use in app
 */
public class ReponsePossibleDto {
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
}
