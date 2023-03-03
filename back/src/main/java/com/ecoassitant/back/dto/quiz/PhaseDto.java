package com.ecoassitant.back.dto.quiz;

import com.ecoassitant.back.entity.tools.Phase;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Phase with id of project, demand for obtain a Quiz by phase
 */
@Data
public class PhaseDto {
    @NotBlank
    private String phase;
    @NotBlank
    private Integer id;

    public Phase getPhase() {
        return Phase.valueOf(phase);
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
