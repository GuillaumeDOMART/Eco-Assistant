package com.ecoassitant.back.dto.quiz;

import com.ecoassitant.back.dto.quiz.ReponsePossibleDto;

/**
 * Reponse for QuestionUniqueDto
 */
public class ReponseUniqueDto {
    private Long questionSuivId;
    private String intitule;

    /**
     * create reponse without questionSuiv
     * @param reponsePossibleDto reponse format tree
     */
    public ReponseUniqueDto(ReponsePossibleDto reponsePossibleDto) {
        this.intitule = reponsePossibleDto.getIntitule();
        this.questionSuivId = reponsePossibleDto.getQuestionSuiv()!= null?reponsePossibleDto.getQuestionSuiv().getQuestionId(): -1L;
    }

    public Long getQuestionSuivId() {
        return questionSuivId;
    }

    public String getIntitule() {
        return intitule;
    }
}
