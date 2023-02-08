package com.ecoassitant.back.calcul;

import org.springframework.beans.factory.annotation.Autowired;

public interface Operateur extends OperationElem {
    public Double execute(Double x, Double y);

    default public TypeOp type() {
        return TypeOp.OPERATEUR;
    }
}
