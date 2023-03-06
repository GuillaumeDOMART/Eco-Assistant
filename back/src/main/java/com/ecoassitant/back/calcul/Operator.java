package com.ecoassitant.back.calcul;

/**
 * Object for represent addition, subtraction...  of a operation
 */
public interface Operator extends OperationElem {
    /**
     * execute the operation with x and y
     * @param x first value
     * @param y second value
     * @return the operation between x and y (x OP y)
     */
    Double execute(Double x, Double y);

    /**
     * indicate the type of the Operate
     * @return  TypeOp.OPERATEUR
     */
    default TypeOp type() {
        return TypeOp.OPERATEUR;
    }
}
