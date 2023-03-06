package com.ecoassitant.back.dto.resultat;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO of a GivenAnswerEntity
 */
@Data
@AllArgsConstructor
public class ReponseDonneeDto {
    private Long reponsePosId;
    private String entry;

    /**
     * Generate a DTO from a GivenAnswerEntity
     *
     * @throws IllegalArgumentException if entity is null
     */

}
