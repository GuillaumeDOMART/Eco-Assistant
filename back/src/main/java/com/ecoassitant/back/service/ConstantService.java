package com.ecoassitant.back.service;

import com.ecoassitant.back.dto.ConstantDto;

import java.util.List;

/**
 * Service of ConstanteController
 */
public interface ConstantService {
    /**
     * Get ConstantDto associated with constanteId
     * @param constanteId represents id of constante
     * @return ConstantDto
     */
    ConstantDto getConstante(Long constanteId);

    /**
     * Get List<ConstantDto> in the DB
     * @return List<ConstantDto>
     */
    List<ConstantDto> findAll();
}
