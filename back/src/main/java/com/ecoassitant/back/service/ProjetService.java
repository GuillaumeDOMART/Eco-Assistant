package com.ecoassitant.back.service;

import com.ecoassitant.back.dto.ProjetDto;

import java.util.List;

/**
 * Service of ProjetService
 */
public interface ProjetService {
    ProjetDto getProject(Long id);
    List<ProjetDto> findProjectByProfilId(Long id);
    List<ProjetDto> findAll();
}
