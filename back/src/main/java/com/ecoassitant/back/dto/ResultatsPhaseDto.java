package com.ecoassitant.back.dto;

import com.ecoassitant.back.dto.resultat.ResultatDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ResultatsPhaseDto {
    private ResultatDto mine;
    private final List<ResultatDto> others = new ArrayList<>();

}
