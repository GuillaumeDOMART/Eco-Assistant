package com.ecoassitant.back.calcul;
/**
 * Object for represente division of a calcul
 */
public class Div implements Operateur{
    @Override
    public Double execute(Double x, Double y) {
        if (y == 0)
            throw  new IllegalArgumentException();
        return x / y;
    }

    @Override
    public int level() {
        return 2;
    }
}
