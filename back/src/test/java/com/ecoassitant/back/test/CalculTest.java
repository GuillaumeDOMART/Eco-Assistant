package com.ecoassitant.back.test;

import com.ecoassitant.back.calcul.CalculEntier;
import com.ecoassitant.back.entity.*;
import com.ecoassitant.back.entity.tools.Categorie;
import com.ecoassitant.back.entity.tools.Operator;
import com.ecoassitant.back.entity.tools.Phase;
import com.ecoassitant.back.entity.tools.TypeQ;
import net.bytebuddy.pool.TypePool;
import org.checkerframework.checker.units.qual.C;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class CalculTest {
    ProjetEntity projet = new ProjetEntity();

    QuestionEntity q1 = new QuestionEntity(1L, "q1", null, TypeQ.NUMERIC, Phase.HORS_PHASE, Categorie.FIRST, true);
    QuestionEntity q2 = new QuestionEntity(2L, "q2", q1, TypeQ.QCM, Phase.HORS_PHASE, Categorie.FIRST, true);
    QuestionEntity q3 = new QuestionEntity(3L, "q3", q2, TypeQ.NUMERIC, Phase.HORS_PHASE, Categorie.FIRST, true);

    ConstanteEntity c1 = new ConstanteEntity(1L, 1, "oui");
    ConstanteEntity c2 = new ConstanteEntity(2L, 20, "jentends");

    ReponsePossibleEntity r1 = new ReponsePossibleEntity(1L, q1, q2, "rentre une valeur", c1);
    ReponsePossibleEntity r2 = new ReponsePossibleEntity(2L, q2, q3, "oui", c1);
    ReponsePossibleEntity r3 = new ReponsePossibleEntity(3L, q2, q3, "non", c1);
    ReponsePossibleEntity r4 = new ReponsePossibleEntity(4L, q3, null, "rentre une valeur", c2);

    CalculOperateurEntity add = new CalculOperateurEntity(1L, Operator.ADD);
    CalculOperateurEntity sub = new CalculOperateurEntity(2L, Operator.SUB);
    CalculOperateurEntity mult = new CalculOperateurEntity(3L, Operator.MULT);
    CalculOperateurEntity div = new CalculOperateurEntity(4L, Operator.DIV);
    CalculOperateurEntity stop = new CalculOperateurEntity(5L, Operator.NOTHING);
    List listRd = new ArrayList<ReponseDonneeEntity>();
    ReponseDonneeKey rdk1 = new ReponseDonneeKey();
    ReponseDonneeEntity rd1 = new ReponseDonneeEntity(rdk1, 10);
    ReponseDonneeKey rdk2 = new ReponseDonneeKey();
    ReponseDonneeEntity rd2 = new ReponseDonneeEntity(rdk2, 5);
    ReponseDonneeKey rdk3 = new ReponseDonneeKey();
    List listCalcul = new ArrayList<CalculEntity>();
    @Test
    void creationCalculEntier() {



        rdk1.setProjet(projet);
        rdk1.setReponsePos(r1);

        rdk2.setProjet(projet);
        rdk2.setReponsePos(r3);

        rdk3.setProjet(projet);
        rdk3.setReponsePos(r4);
        ReponseDonneeEntity rd3 = new ReponseDonneeEntity(rdk3, 1);

        listRd.add(rd1);
        listRd.add(rd2);
        listRd.add(rd3);


        //10 + 5 * 20
        listCalcul.add(new CalculEntity(1L, add, r1, 1));
        listCalcul.add(new CalculEntity(2L, mult, r3, 1));
        listCalcul.add(new CalculEntity(3L, stop, r4, 1));

        var calcul = new CalculEntier(listCalcul, listRd);
        double executer = calcul.execute().get();
        Assert.assertEquals(10 + 5 * 20, executer);
    }
    @Test
    void creationCalculNull() {
        Assert.assertThrows(IllegalArgumentException.class, () -> new CalculEntier(null, listRd));
    }
}