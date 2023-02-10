package com.ecoassitant.back.calcul;
/**
 * Object for represente multiplication of a calcul
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
}
