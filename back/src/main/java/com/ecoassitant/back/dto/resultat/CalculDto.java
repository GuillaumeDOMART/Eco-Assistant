package com.ecoassitant.back.dto.resultat;

public class CalculDto {
    private final Double result;
    private final String intitule;

    public CalculDto(String intitule, Double result) {
        this.result = result;
        this.intitule = intitule;
    }

    public Double getResult() {
        return result;
    }

    public String getIntitule() {
        return intitule;
    }
}
