package com.ecoassitant.back.dto.quiz;

import com.ecoassitant.back.entity.ReponseDonneeEntity;

public class ReponseDonneeDtoQuiz {
    private int entry;
    private ReponseUniqueDto reponse;

    public int getEntry() {
        return entry;
    }

    public ReponseUniqueDto getReponse() {
        return reponse;
    }

    public ReponseDonneeDtoQuiz(ReponseDonneeEntity reponse) {
        entry = reponse.getEntry();
        this.reponse = new ReponseUniqueDto(reponse.getReponseDonneeKey().getReponsePos());
    }
}
