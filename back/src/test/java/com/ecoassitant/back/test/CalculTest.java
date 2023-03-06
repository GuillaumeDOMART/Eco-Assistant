package com.ecoassitant.back.test;

import com.ecoassitant.back.calcul.ReformedOperation;
import com.ecoassitant.back.entity.*;
import com.ecoassitant.back.entity.tools.Operator;
import com.ecoassitant.back.entity.tools.Phase;
import com.ecoassitant.back.entity.tools.TypeQ;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class CalculTest {

    ConstanteEntity c1 = new ConstanteEntity(1L, 1, "oui");
    ConstanteEntity c2 = new ConstanteEntity(2L, 20, "jentends");
    ProjectEntity projet = new ProjectEntity();

    QuestionEntity q1 = new QuestionEntity(1L, "q1", TypeQ.NUMERIC, Phase.HORS_PHASE, null);
    QuestionEntity q2 = new QuestionEntity(2L, "q2", TypeQ.QCM, Phase.HORS_PHASE, null);
    QuestionEntity q3 = new QuestionEntity(3L, "q3", TypeQ.NUMERIC, Phase.HORS_PHASE, null);



    ReponsePossibleEntity r1 = new ReponsePossibleEntity(1L, q1, q2, "rentre une valeur", c1);
    ReponsePossibleEntity r2 = new ReponsePossibleEntity(2L, q2, q3, "oui", c1);
    ReponsePossibleEntity r3 = new ReponsePossibleEntity(3L, q2, q3, "non", c1);
    ReponsePossibleEntity r4 = new ReponsePossibleEntity(4L, q3, null, "rentre une valeur", c2);

    CalculOperateurEntity add = new CalculOperateurEntity(1L, Operator.ADD);
    CalculOperateurEntity sub = new CalculOperateurEntity(2L, Operator.SUB);
    CalculOperateurEntity mult = new CalculOperateurEntity(3L, Operator.MULT);
    CalculOperateurEntity div = new CalculOperateurEntity(4L, Operator.DIV);
    CalculOperateurEntity stop = new CalculOperateurEntity(5L, Operator.NOTHING);
    List<GivenAnswerEntity> listRd = new ArrayList<>();
    ReponseDonneeKey rdk1 = new ReponseDonneeKey();
    GivenAnswerEntity rd1 = new GivenAnswerEntity(rdk1,r1, 10);
    ReponseDonneeKey rdk2 = new ReponseDonneeKey();
    GivenAnswerEntity rd2 = new GivenAnswerEntity(rdk2, r3, 5);
    ReponseDonneeKey rdk3 = new ReponseDonneeKey();
    List<CalculEntity> listCalcul = new ArrayList<>();
    @Test
    void creationCalculEntier() {
        rdk1.setProjet(projet);
        rdk1.setQuestion(q1);

        rdk2.setProjet(projet);
        rdk2.setQuestion(q2);

        rdk3.setProjet(projet);
        rdk3.setQuestion(q3);
        GivenAnswerEntity rd3 = new GivenAnswerEntity(rdk3, r4, 1);

        listRd.add(rd1);
        listRd.add(rd2);
        listRd.add(rd3);

        //10 + 5 * 20
        var cl1 = new CalculEntity();
        cl1.setIdCalcul(1L);
        cl1.setCalculOp(add);
        cl1.setReponsePossible(r1);
        cl1.setPhase(Phase.DEVELOPPEMENT);
        cl1.setNbCalcul(1);

        var cl2 = new CalculEntity();
        cl2.setIdCalcul(2L);
        cl2.setCalculOp(mult);
        cl2.setReponsePossible(r3);
        cl2.setPhase(Phase.DEVELOPPEMENT);
        cl2.setNbCalcul(1);

        var cl3 = new CalculEntity();
        cl3.setIdCalcul(3L);
        cl3.setCalculOp(stop);
        cl3.setReponsePossible(r4);
        cl3.setPhase(Phase.DEVELOPPEMENT);
        cl3.setNbCalcul(1);

        listCalcul.add(cl1);
        listCalcul.add(cl2);
        listCalcul.add(cl3);

        var calcul = new ReformedOperation(listCalcul, listRd);
        Double executer = calcul.execute().get();
        Double test =  110D;
        Assert.assertEquals(test, executer);
    }
    @Test
    void creationCalculNull() {
        Assert.assertThrows(NullPointerException.class, () -> new ReformedOperation(null, listRd));
    }
}