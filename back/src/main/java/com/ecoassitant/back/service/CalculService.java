package com.ecoassitant.back.service;

import java.util.List;

/**
 * Service of calcul
 */
public interface CalculService {
    /**
     * List of calculs executed for a project
     * @param idProject project of wich we want the result
     * @return list of results
     */
    public List<Double> CalculsForProject(Long idProject);
}
