package com.ecoassitant.back.calcul;
/**
 * Object to represent the substraction of a operation
 */
public class Sub implements Operateur{
    @Override
    public Double execute(Double x, Double y) {
        return x - y;
    }

    @Override
    public int level() {
        return 1;
    }
    @Override
    public String toString(){
        return "-";
    }
}
