package com.ecoassitant.back.service;

import com.ecoassitant.back.dto.ConstanteDto;

import java.util.Collection;
import java.util.List;

public interface ConstanteService {
    ConstanteDto getConstante(Long constanteId);

    List<ConstanteDto> findAll();
}
