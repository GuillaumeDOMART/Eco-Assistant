package com.ecoassitant.back.service;

import com.ecoassitant.back.dto.ResultatDto;

/**
 * Service of calcul
 */
public interface CalculService {
    /**
     * List of calculs executed for a project
     * @param idProject project of wich we want the result
     * @return list of results
     */
    public ResultatDto CalculsForProject(Long idProject);
}
