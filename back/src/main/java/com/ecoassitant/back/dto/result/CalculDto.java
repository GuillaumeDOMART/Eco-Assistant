package com.ecoassitant.back.dto.result;

/**
 * Calculation with his result and his entitled
 */
public class CalculDto {
    private final Double result;
    private final String entitled;

    /**
     * Calculation format for the front
     * @param entitled  entitled of the calculation
     * @param result result of the calculation
     */
    public CalculDto(String entitled, Double result) {
        this.result = result;
        this.entitled = entitled;
    }

    public Double getResult() {
        return result;
    }

    public String getEntitled() {
        return entitled;
    }
}
