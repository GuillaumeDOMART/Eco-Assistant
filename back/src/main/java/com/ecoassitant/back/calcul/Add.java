package com.ecoassitant.back.calcul;
/**
 * Object to represent addition of a calculation
 */
public class Add implements Operateur{
    @Override
    public Double execute(Double x, Double y) {
        return x + y;
    }


    @Override
    public int level() {
        return 1;
    }
    @Override
    public String toString(){
        return "+";
    }
}
