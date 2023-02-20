package com.ecoassitant.back.dto;

import com.ecoassitant.back.entity.tools.Phase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionProposeDto {
    private Long idQuestion;
    private Phase phase;
    private int vote;
    private boolean isApprove;
}
