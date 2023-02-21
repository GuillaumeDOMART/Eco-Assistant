package com.ecoassitant.back.dao;

import com.ecoassitant.back.entity.tools.Phase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionProposeDao {
    private String intitule;
    private Phase phase;
}

