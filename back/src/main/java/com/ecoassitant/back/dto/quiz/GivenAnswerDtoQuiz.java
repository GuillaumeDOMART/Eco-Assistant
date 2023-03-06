package com.ecoassitant.back.dto.quiz;

import com.ecoassitant.back.entity.GivenAnswerEntity;

/**
 * Response of the quiz in progress in the quiz
 */
public class GivenAnswerDtoQuiz {
    private int entry;
    private ResponseUniqueDto response;


    /**
     * create response with a GivenAnswerEntity
     * @param response GivenAnswerEntity
     */
    public GivenAnswerDtoQuiz(GivenAnswerEntity response) {
        entry = response.getEntry();
        this.response = new ResponseUniqueDto(response.getReponsePos());
    }

    public int getEntry() {
        return entry;
    }

    public ResponseUniqueDto getResponse() {
        return response;
    }
}
