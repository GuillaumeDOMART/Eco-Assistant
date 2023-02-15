package com.ecoassitant.back.dto;

public class ReponseUniqueDto {
    private Long questionSuivId;
    private String intitule;
    public ReponseUniqueDto(ReponsePossibleDto reponsePossibleDto) {
        this.intitule = reponsePossibleDto.getIntitule();
        this.questionSuivId = reponsePossibleDto.getQuestionSuiv()!= null?reponsePossibleDto.getQuestionSuiv().getQuestionId(): -1;
    }

    public Long getQuestionSuivId() {
        return questionSuivId;
    }

    public String getIntitule() {
        return intitule;
    }
}
