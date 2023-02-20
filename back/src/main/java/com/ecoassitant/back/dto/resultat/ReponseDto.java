package com.ecoassitant.back.dto.resultat;

/**
 * A reponse of a question for a project
 */
public class ReponseDto {
    private String questionId;
    private String entry;

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    public Long getQuestionId() {
        return Long.valueOf(questionId);
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId.toString();
    }
}
