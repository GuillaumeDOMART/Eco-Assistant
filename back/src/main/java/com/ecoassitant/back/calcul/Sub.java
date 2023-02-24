package com.ecoassitant.back.calcul;
/**
 * Object for represente substraction of a calcul
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
