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

    ConstantEntity c1 = new ConstantEntity(1L, 1, "oui");
    ConstantEntity c2 = new ConstantEntity(2L, 20, "jentends");
    ProjectEntity projet = new ProjectEntity();

    QuestionEntity q1 = new QuestionEntity(1L, "q1", TypeQ.NUMERIC, Phase.HORS_PHASE, null);
    QuestionEntity q2 = new QuestionEntity(2L, "q2", TypeQ.QCM, Phase.HORS_PHASE, null);
    QuestionEntity q3 = new QuestionEntity(3L, "q3", TypeQ.NUMERIC, Phase.HORS_PHASE, null);



    ResponsePossibleEntity r1 = new ResponsePossibleEntity(1L, q1, q2, "rentre une valeur", c1);
    ResponsePossibleEntity r2 = new ResponsePossibleEntity(2L, q2, q3, "oui", c1);
    ResponsePossibleEntity r3 = new ResponsePossibleEntity(3L, q2, q3, "non", c1);
    ResponsePossibleEntity r4 = new ResponsePossibleEntity(4L, q3, null, "rentre une valeur", c2);

    CalculOperatorEntity add = new CalculOperatorEntity(1L, Operator.ADD);
    CalculOperatorEntity sub = new CalculOperatorEntity(2L, Operator.SUB);
    CalculOperatorEntity mult = new CalculOperatorEntity(3L, Operator.MULT);
    CalculOperatorEntity div = new CalculOperatorEntity(4L, Operator.DIV);
    CalculOperatorEntity stop = new CalculOperatorEntity(5L, Operator.NOTHING);
    List<GivenAnswerEntity> listRd = new ArrayList<>();
    GivenAnswerKey rdk1 = new GivenAnswerKey();
    GivenAnswerEntity rd1 = new GivenAnswerEntity(rdk1,r1, 10);
    GivenAnswerKey rdk2 = new GivenAnswerKey();
    GivenAnswerEntity rd2 = new GivenAnswerEntity(rdk2, r3, 5);
    GivenAnswerKey rdk3 = new GivenAnswerKey();
    List<CalculEntity> listCalcul = new ArrayList<>();
    @Test
    void creationCalculEntier() {
        rdk1.setProject(projet);
        rdk1.setQuestion(q1);

        rdk2.setProject(projet);
        rdk2.setQuestion(q2);

        rdk3.setProject(projet);
        rdk3.setQuestion(q3);
        GivenAnswerEntity rd3 = new GivenAnswerEntity(rdk3, r4, 1);

        listRd.add(rd1);
        listRd.add(rd2);
        listRd.add(rd3);

        //10 + 5 * 20
        var cl1 = new CalculEntity();
        cl1.setIdCalcul(1L);
        cl1.setCalculOp(add);
        cl1.setResponsePossible(r1);
        cl1.setPhase(Phase.DEVELOPPEMENT);
        cl1.setNbCalcul(1);

        var cl2 = new CalculEntity();
        cl2.setIdCalcul(2L);
        cl2.setCalculOp(mult);
        cl2.setResponsePossible(r3);
        cl2.setPhase(Phase.DEVELOPPEMENT);
        cl2.setNbCalcul(1);

        var cl3 = new CalculEntity();
        cl3.setIdCalcul(3L);
        cl3.setCalculOp(stop);
        cl3.setResponsePossible(r4);
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