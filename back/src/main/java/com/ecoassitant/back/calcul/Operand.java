package com.ecoassitant.back.calcul;

/**
 * Represent value of operation
 */
public class Operand implements OperationElem {
    private final Double value;

    /**
     * constructor of Operand
     * @param value Double of the value of the operand
     */
    public Operand(Double value) {
        this.value = value;
    }

    /**
     *  Value of the Operand
     * @return the value
     */
    public Double val(){
        return value;
    }

    @Override
    public TypeOp type() {
        return TypeOp.OPERAND;
    }

    @Override
    public int level() {
        return 0;
    }
    @Override
    public String toString(){
        return value.toString();
    }
}
