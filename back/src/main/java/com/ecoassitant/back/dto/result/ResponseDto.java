package com.ecoassitant.back.dto.result;

/**
 * A response of a question for a project
 */
public class ResponseDto {
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
