package com.ecoassitant.back.dto.result;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Dto for result of a phase
 */
@Data
public class ResultsPhaseDto {
    private ResultDto mine;
    private final List<ResultDto> others = new ArrayList<>();

    /**
     * Builder of ResultsPhaseDto
     *
     * @param mine the result Dto
     */
    public ResultsPhaseDto(ResultDto mine) {
        this.mine = mine;
    }

    /**
     * Function to add another Dto
     *
     * @param resultatDto the other Dto
     */
    public void addOther(Optional<ResultDto> resultatDto) {
        resultatDto.ifPresent(others::add);

    }
}
