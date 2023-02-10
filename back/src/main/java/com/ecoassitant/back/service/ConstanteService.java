package com.ecoassitant.back.service;

import com.ecoassitant.back.dto.ConstanteDto;

import java.util.List;

/**
 * Service of ConstanteController
 */
public interface ConstanteService {
    ConstanteDto getConstante(Long constanteId);

    List<ConstanteDto> findAll();
}
