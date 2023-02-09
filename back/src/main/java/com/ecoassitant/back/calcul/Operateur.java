package com.ecoassitant.back.calcul;

/**
 * Object for represente addition, substraction...  of a calcul
 */
public interface Operateur extends OperationElem {
    /**
     * execute the operation with x and y
     * @param x first value
     * @param y second value
     * @return the operation between x and y (x OP y)
     */
    public Double execute(Double x, Double y);

    /**
     * indicate the type of the Operateur
     * @return  TypeOp.OPERATEUR
     */
    default public TypeOp type() {
        return TypeOp.OPERATEUR;
    }
}
