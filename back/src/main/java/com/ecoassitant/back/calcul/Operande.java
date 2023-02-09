package com.ecoassitant.back.calcul;

public class Operande implements OperationElem {
    private Double value;

    /**
     * constructor of Operande
     * @param value Double of the value of the operande
     */
    public Operande(Double value) {
        this.value = value;
    }

    public Double val(){
        return value;
    }

    @Override
    public TypeOp type() {
        return TypeOp.OPERANDE;
    }

    @Override
    public int level() {
        return 0;
    }
}
