package com.ecoassitant.back.dto;

import java.util.ArrayList;
import java.util.List;

public class ResultatsPhaseDto {
    private ResultatDto mine;
    private final List<ResultatDto> others = new ArrayList<>();

    public ResultatsPhaseDto(ResultatDto mine) {
        this.mine = mine;
    }
    
    public void addOther(ResultatDto resultatDto){
        others.add(resultatDto);
    }

    public ResultatDto getMine() {
        return mine;
    }

    public List<ResultatDto> getOthers() {
        return others;
    }
}
