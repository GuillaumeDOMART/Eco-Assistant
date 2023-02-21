package com.ecoassitant.back.dto;

import com.ecoassitant.back.entity.QuestionProposeEntity;
import com.ecoassitant.back.entity.tools.Phase;
import lombok.Data;

@Data
public class QuestionProposeDto {
    private Long idQuestion;

    private String intitule;
    private Phase phase;
    private int vote;
    private boolean isApprove;


    public QuestionProposeDto(QuestionProposeEntity qp){
        this.idQuestion = qp.getIdQuestion();
        this.intitule = qp.getIntitule();
        this.phase = qp.getPhase();
        this.vote = qp.getVote();
        this.isApprove = qp.isApprove();
    }
}
