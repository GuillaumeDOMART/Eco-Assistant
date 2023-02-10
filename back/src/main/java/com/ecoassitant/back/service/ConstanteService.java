package com.ecoassitant.back.service;

import com.ecoassitant.back.dto.ConstanteDto;

import java.util.List;

/**
 * Service of ConstanteController
 */
public interface ConstanteService {
    /**
     * Get ConstanteDto associated with constanteId
     * @param constanteId represents id of constante
     * @return ConstanteDto
     */
    ConstanteDto getConstante(Long constanteId);

    /**
     * Get List<ConstanteDto> in the DB
     * @return List<ConstanteDto>
     */
    List<ConstanteDto> findAll();
}
