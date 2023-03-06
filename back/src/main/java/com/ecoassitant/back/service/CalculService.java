package com.ecoassitant.back.service;

import com.ecoassitant.back.dto.result.ResultsPhaseDto;

import java.util.Optional;

/**
 * Service of calcul
 */
public interface CalculService {
    /**
     * List of operations executed for a project
     * @param idProject project of wich we want the result
     * @return list of results
     */
    Optional<ResultsPhaseDto> calculsForProject(Integer idProject, String mail);
}
