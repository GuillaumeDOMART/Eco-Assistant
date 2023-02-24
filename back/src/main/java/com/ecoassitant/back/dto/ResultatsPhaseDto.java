package com.ecoassitant.back.dto;

import com.ecoassitant.back.dto.resultat.ResultatDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Dto for result of a phase
 */
@Data
public class ResultatsPhaseDto {
    private ResultatDto mine;
    private final List<ResultatDto> others = new ArrayList<>();

    /**
     * Builder of ResultatsPhaseDto
     *
     * @param mine the result Dto
     */
    public ResultatsPhaseDto(ResultatDto mine) {
        this.mine = mine;
    }

    /**
     * Function to add another Dto
     *
     * @param resultatDto the other Dto
     */
    public void addOther(Optional<ResultatDto> resultatDto) {
        resultatDto.ifPresent(others::add);

    }
}
