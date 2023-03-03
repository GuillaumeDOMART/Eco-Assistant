package com.ecoassitant.back.dto;

import com.ecoassitant.back.entity.ConstanteEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

/**
 * DTO of ConstanteEntity
 */
@Data
@AllArgsConstructor
public class ConstanteDto {
    private Long idConstante;
    private double constante;
    private String tracabilite;

    /**
     * Generate a DTO from a ConstanteEntity
     *
     * @throws IllegalArgumentException if entity is null
     */
    public ConstanteDto(ConstanteEntity constanteEntity) {
        Objects.requireNonNull(constanteEntity);
        this.idConstante = constanteEntity.getIdConstante();
        this.constante = constanteEntity.getConstante();
        this.tracabilite = constanteEntity.getTracabilite();
    }


}
