package com.ecoassitant.back.dto.resultat;

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
    private String entry;

    /**
     * Generate a DTO from a ReponseDonneeEntity
     *
     * @throws IllegalArgumentException if entity is null
     */

}
