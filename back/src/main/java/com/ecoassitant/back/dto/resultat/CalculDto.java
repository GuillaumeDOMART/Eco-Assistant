package com.ecoassitant.back.dto.resultat;

/**
 * Calcul with his result and his entitled
 */
public class CalculDto {
    private final Double result;
    private final String intitule;

    /**
     * Calcul format for the front
     * @param intitule  entitled of the calcul
     * @param result result of the calcul
     */
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
