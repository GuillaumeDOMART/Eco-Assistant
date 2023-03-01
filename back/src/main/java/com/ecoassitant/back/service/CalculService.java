package com.ecoassitant.back.service;

import com.ecoassitant.back.dto.resultat.ResultatsPhaseDto;

import java.util.Optional;

/**
 * Service of calcul
 */
public interface CalculService {
    /**
     * List of calculs executed for a project
     * @param idProject project of wich we want the result
     * @return list of results
     */
    Optional<ResultatsPhaseDto> calculsForProject(Integer idProject, String mail);
}
