package com.ecoassitant.back.calcul;

public interface OperationElem {
    /**
     * type of the OperationElem (OPERANDE or OPERATEUR)
     * @return Enum for type
     */
    public TypeOp type();

    /**
     * level of priority for operation
     * @return integer for level. OperationElem has a priority important if the level is  high
     */
    public int level();
}
