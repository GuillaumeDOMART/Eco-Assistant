package com.ecoassitant.back.dto;

import com.ecoassitant.back.entity.ProposedQuestionEntity;
import com.ecoassitant.back.entity.tools.Phase;
import lombok.Data;

/**
 * DTO for QuestionPropose
 */
@Data
public class ProposedQuestionDto {
    private Long idQuestion;

    private String entitled;
    private Phase phase;
    private int vote;
    private boolean isApprove;

    /**
     * Constructor to create ProposedQuestionDto with a ProposedQuestionEntity
     * @param qp the entity
     */
    public ProposedQuestionDto(ProposedQuestionEntity qp){
        this.idQuestion = qp.getIdQuestion();
        this.entitled = qp.getEntitled();
        this.phase = qp.getPhase();
        this.vote = qp.getVote();
        this.isApprove = qp.isApprove();
    }
}
