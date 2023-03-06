package com.ecoassitant.back.dto;

import com.ecoassitant.back.entity.ConstantEntity;
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
    public ConstantDto(ConstantEntity constantEntity) {
        Objects.requireNonNull(constantEntity);
        this.idConstant = constantEntity.getIdConstante();
        this.constant = constantEntity.getConstant();
        this.source = constantEntity.getSource();
    }


}
