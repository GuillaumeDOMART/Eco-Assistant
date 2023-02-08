package com.ecoassitant.back.calcul;

public class Add implements Operateur{
    @Override
    public Double execute(Double x, Double y) {
        return x + y;
    }


    @Override
    public int level() {
        return 1;
    }
}
