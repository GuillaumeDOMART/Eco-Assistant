package com.ecoassitant.back.dto.quiz;

import com.ecoassitant.back.dto.quiz.ReponsePossibleDto;

/**
 * Reponse for QuestionUniqueDto
 */
public class ReponseUniqueDto {
    private Long questionSuivId;
    private Long reponseId;
    private String intitule;

    /**
     * create reponse without questionSuiv
     * @param reponsePossibleDto reponse format tree
     */
    public ReponseUniqueDto(ReponsePossibleDto reponsePossibleDto) {
        this.intitule = reponsePossibleDto.getIntitule();
        this.questionSuivId = reponsePossibleDto.getQuestionSuiv()!= null?reponsePossibleDto.getQuestionSuiv().getQuestionId(): -1L;
        this.reponseId = reponsePossibleDto.getId();
    }

    /**
     * create reponse without questionSuiv
     * @param reponsePossible ReponsePossibleEntity
     */
    public ReponseUniqueDto(ReponsePossibleEntity reponsePossible) {
        this.intitule = reponsePossible.getIntitule();
        this.questionSuivId = reponsePossible.getQuestionSuiv()!= null?reponsePossible.getQuestionSuiv().getIdQuestion(): -1L;
        this.reponseId = reponsePossible.getIdReponsePos();
    }

    public Long getQuestionSuivId() {
        return questionSuivId;
    }

    public String getIntitule() {
        return intitule;
    }

    public Long getReponseId() {return reponseId;}
}
