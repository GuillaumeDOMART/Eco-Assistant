package com.ecoassitant.back.calcul;

/**
 * Object of an operation
 */
public interface OperationElem {
    /**
     * type of the OperationElem (OPERAND or OPERATOR)
     * @return Enum for type
     */
    TypeOp type();

    /**
     * level of priority for operation
     * @return integer for level. OperationElem has a priority important if the level is  high
     */
    int level();
}
