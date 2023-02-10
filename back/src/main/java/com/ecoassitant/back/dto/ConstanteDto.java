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
    private int constante;
    private String tracabilite;


    public ConstanteDto(ConstanteEntity constanteEntity) {
        Objects.requireNonNull(constanteEntity);
        this.idConstante = constanteEntity.getIdConstante();
        this.constante = constanteEntity.getConstante();
        this.tracabilite = constanteEntity.getTracabilite();
    }


}
