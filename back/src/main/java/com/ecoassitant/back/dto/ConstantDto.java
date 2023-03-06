package com.ecoassitant.back.dto;

import com.ecoassitant.back.entity.ConstanteEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

/**
 * DTO of ConstantEntity
 */
@Data
@AllArgsConstructor
public class ConstantDto {
    private Long idConstant;
    private double constant;
    private String source;

    /**
     * Generate a DTO from a ConstantEntity
     *
     * @throws IllegalArgumentException if entity is null
     */
    public ConstantDto(ConstanteEntity constanteEntity) {
        Objects.requireNonNull(constanteEntity);
        this.idConstant = constanteEntity.getIdConstante();
        this.constant = constanteEntity.getConstante();
        this.source = constanteEntity.getTracabilite();
    }


}
