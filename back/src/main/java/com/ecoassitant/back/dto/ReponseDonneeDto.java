package com.ecoassitant.back.dto;

import com.ecoassitant.back.entity.ReponseDonneeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

/**
 * DTO of a ReponseDonneeEntity
 */
@Data
@AllArgsConstructor
public class ReponseDonneeDto {
    private Long reponsePosId;
    private int entry;

    /**
     * Generate a DTO from a ReponseDonneeEntity
     *
     * @throws IllegalArgumentException if entity is null
     */
    public ReponseDonneeDto(ReponseDonneeEntity reponseDonnee) {
        Objects.requireNonNull(reponseDonnee);
        this.reponsePosId = reponseDonnee.getReponseDonneeKey().getReponsePos().getIdReponsePos();
        this.entry = reponseDonnee.getEntry();
    }
}