package com.ecoassitant.back.dto.result;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO of a GivenAnswerEntity
 */
@Data
@AllArgsConstructor
public class GivenAnswerDto {
    private Long responsePosId;
    private String entry;

}
