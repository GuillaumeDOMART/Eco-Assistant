package com.ecoassitant.back.service;

import com.ecoassitant.back.dto.resultat.ReponseDonneesDto;

/**
 * Service of ReponseDonnees
 */
public interface ReponseDonneesService {
    /**
     * Service for save list of reponseDonnee
     * @param responses payload with list of reponseDonnee and id project
     * @return true if save in bdd is successed
     */
    public boolean saveResponseDonnees(ReponseDonneesDto responses);
}
