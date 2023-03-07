package com.ecoassitant.back.calcul;

/**
 * Object to represent addition, substraction...  of a operation
 */
public interface Operateur extends OperationElem {
    /**
     * execute the operation with x and y
     * @param x first value
     * @param y second value
     * @return the operation between x and y (x OP y)
     */
    Double execute(Double x, Double y);

    /**
     * indicate the type of the Operateur
     * @return  TypeOp.OPERATEUR
     */
    default TypeOp type() {
        return TypeOp.OPERATEUR;
    }
}
