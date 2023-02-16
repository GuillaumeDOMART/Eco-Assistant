package com.ecoassitant.back.test;

import com.ecoassitant.back.calcul.CalculEntier;
import com.ecoassitant.back.entity.CalculEntity;
import com.ecoassitant.back.entity.CalculOperateurEntity;
import com.ecoassitant.back.entity.QuestionEntity;
import com.ecoassitant.back.entity.ReponsePossibleEntity;
import com.ecoassitant.back.entity.tools.Categorie;
import com.ecoassitant.back.entity.tools.Operator;
import com.ecoassitant.back.entity.tools.Phase;
import com.ecoassitant.back.entity.tools.TypeQ;

import java.util.ArrayList;

public class TestCalcul {

    public void creationCalculEntier(){
        var q1 = new QuestionEntity(1L, "q1", null, TypeQ.NUMERIC, Phase.HORS_PHASE, Categorie.FIRST, true);
        var q2 = new QuestionEntity(2L, "q2", q1, TypeQ.QCM, Phase.HORS_PHASE, Categorie.FIRST, true);

        var r1 = new ReponsePossibleEntity(1L,)

        var add = new CalculOperateurEntity(1L, Operator.ADD);
        var sub = new CalculOperateurEntity(2L, Operator.SUB);
        var mult = new CalculOperateurEntity(3L, Operator.MULT);
        var div = new CalculOperateurEntity(4L, Operator.DIV);
        var stop = new CalculOperateurEntity(5L, Operator.NOTHING);

        var listCalcul = new ArrayList<CalculEntity>();
        listCalcul.add(new CalculEntity(1L, add, ));

        var calcul = new CalculEntier();
    }

}
