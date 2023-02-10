package com.ecoassitant.back.service;

import com.ecoassitant.back.dto.ProjetDto;

import java.util.List;

/**
 * Service of ProjetService
 */
public interface ProjetService {
    /**
     * Get ProjetDto associated with the id
     * @param id
     * @return ProjetDto
     */
    ProjetDto getProject(Long id);

    /**
     * Get List<ProjetDto> associated with the Profil id
     * @param id
     * @return List<ProjetDto>
     */
    List<ProjetDto> findProjectByProfilId(Long id);

    /**
     * Get all ProjetDTO in the DB
     * @return List<ProjetDto>
     */
    List<ProjetDto> findAll();
}
