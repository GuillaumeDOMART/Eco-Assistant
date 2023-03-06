package com.ecoassitant.back.calcul;
/**
 * Object to represent multiplication of a calculation
 */
public class Mult implements Operateur{
    @Override
    public Double execute(Double x, Double y) {
        return x * y;
    }

    @Override
    public int level() {
        return 2;
    }
    @Override
    public String toString(){
        return "*";
    }
}
