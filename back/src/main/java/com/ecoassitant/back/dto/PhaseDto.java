package com.ecoassitant.back.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PhaseDto {
    @NotBlank
    private String phase;
    @NotBlank
    private Integer id;

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public Integer getProjetId() {
        return id;
    }

    public void setProjetId(Integer id) {
        this.id = id;
    }
}
