package com.ecoassitant.back.dto.quiz;

import com.ecoassitant.back.entity.ResponsePossibleEntity;

/**
 * Reponse for QuestionUniqueDto
 */
public class ResponseUniqueDto {
    private Long questionSuivId;
    private Long reponseId;
    private String intitule;

    /**
     * create reponse without questionSuiv
     * @param responsePossibleDto reponse format tree
     */
    public ResponseUniqueDto(ResponsePossibleDto responsePossibleDto) {
        this.intitule = responsePossibleDto.getEntitled();
        this.questionSuivId = responsePossibleDto.getNextQuestion()!= null? responsePossibleDto.getNextQuestion().getQuestionId(): -1L;
        this.reponseId = responsePossibleDto.getId();
    }

    /**
     * create reponse without questionSuiv
     * @param reponsePossible ResponsePossibleEntity
     */
    public ResponseUniqueDto(ResponsePossibleEntity reponsePossible) {
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
