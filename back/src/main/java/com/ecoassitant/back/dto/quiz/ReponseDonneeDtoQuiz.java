package com.ecoassitant.back.dto.quiz;

import com.ecoassitant.back.entity.ReponseDonneeEntity;

/**
 * Reponse of the quiz in progress in the quiz
 */
public class ReponseDonneeDtoQuiz {
    private int entry;
    private ReponseUniqueDto reponse;


    /**
     * create response with a ReponseDonneeEntity
     * @param reponse ReponseDonneeEntity
     */
    public ReponseDonneeDtoQuiz(ReponseDonneeEntity reponse) {
        entry = reponse.getEntry();
        this.reponse = new ReponseUniqueDto(reponse.getReponsePos());
    }

    public int getEntry() {
        return entry;
    }

    public ReponseUniqueDto getReponse() {
        return reponse;
    }
}
